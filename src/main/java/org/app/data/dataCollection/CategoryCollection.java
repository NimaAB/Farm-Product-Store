package org.app.data.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import org.app.data.models.Category;
import org.app.data.models.Product;
import org.app.fileHandling.FileInfo;
import org.app.fileHandling.IOClient;
import java.util.ArrayList;

public class CategoryCollection {

    public static final ObservableList<Category> CATEGORIES = FXCollections.observableArrayList();
    private final ObservableList<String> mainCategories = FXCollections.observableArrayList();
    private final ObservableList<String> subCategories = FXCollections.observableArrayList();
    private final FileInfo categoryFile = new FileInfo("DataFraApp/Database/categories.bin");
    private boolean modified = false;

    public static CategoryCollection instance;
    private CategoryCollection(){}

    public static CategoryCollection getInstance(){
        if(instance == null){
            instance = new CategoryCollection();
        }
        return instance;
    }

    /** legger nye kategorier */
    public void addCategory(Category toAdd){
        if(!CATEGORIES.contains(toAdd)) {
            if(!mainCategories.contains(toAdd.getName())) {
                mainCategories.add(toAdd.getName());
                CATEGORIES.add(toAdd);
            } else {
                throw new IllegalArgumentException("En kategori med navn " +toAdd.getName()+ " finnes allerede!");
            }
        }
    }

    /** Når listen av kategorier ble endret, må alle combobokser oppdateres */
    public void updateCategoriesOnChange(ComboBox<String> categoryOptions, ComboBox<String> subCategoryOptions){
        CATEGORIES.addListener((ListChangeListener<Category>) change -> setComboBoxes(categoryOptions, subCategoryOptions));
    }

    /** Når hoved kategori ble endret, må sub kategoriene oppdateres */
    public void updateSubCategoriesOnChange(ComboBox<String> categoryOptions){
        categoryOptions.valueProperty().addListener((observableValue, oldValue, newValue) -> loadSubCategories(newValue));
    }

    /** Åpner kategorier filen */
    public void loadCategories(){
        IOClient<Category> openFile = new IOClient<>(categoryFile);
        openFile.runOpenThread("Laster opp kategorier...");
    }

    /** Lagrer kategorier filen */
    public void saveCategories(){
        IOClient<Category> saveFile = new IOClient<>(categoryFile, new ArrayList<>(CATEGORIES));
        saveFile.runSaveThread("Lagrer nye kategorier...");
    }

    /** Setter verdier til kategori combobokser */
    public void setComboBoxes(ComboBox<String> categoryOptions, ComboBox<String> subCategoryOptions){
        categoryOptions.setItems(mainCategories);
        subCategoryOptions.setItems(subCategories);
    }

    /** Fyller opp sub kategori obslist med verdier basert på hoved kategorien */
    public void loadSubCategories(String value){
        subCategories.clear();
        for(Category category: CATEGORIES) {
            if(category.getName().equals(value)) {
                subCategories.addAll(category.getSubCategories());
            }
        }
    }

    /** Fyller opp sub-kategorier combobox med riktige verdier på tableview */
    public void updateSubCategoriesOnTableView(TableView<Product> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    if (row.getItem() != null) {
                        Product product = row.getItem();
                        loadSubCategories(product.getCategory());
                    }
                }
            });
            return row;
        });
    }

    /** Getter - Setter  metoder */

    public ObservableList<String> getCategories() {
        return mainCategories;
    }

    public ObservableList<String> getSubCategories() {
        return subCategories;
    }

    public ObservableList<Category> getCategoryObjects(){
        return CATEGORIES;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
