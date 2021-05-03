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
import java.util.Arrays;

public class CategoryCollection {
    private final FileInfo categoryFile = new FileInfo("DataFraApp/Database/categories.bin");
    private static final ObservableList<Category> CATEGORIES = FXCollections.observableArrayList();
    private static final ObservableList<String> mainCategories = FXCollections.observableArrayList();
    private static final ObservableList<String> subCategories = FXCollections.observableArrayList();

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

    private void loadCategories(){
        IOClient<Category> openFile = new IOClient<>(categoryFile);
        openFile.runOpenThread();

    }


    /** Laster opp definerte kategorier */
    public void loadDefinedCategories(){
        Category korn = new Category("Korn");
        Category klaer = new Category("Arbeidsklær");
        Category landbruk = new Category("Landbruk");
        Category gjodsel = new Category("Gjødsel");

        String[] kornTyper = {"Såkorn", "Økologisk såkorn", "Underkultur og fangvekster", "Plantevern"};
        String[] klaerTyper = {"Jakker", "Hansker", "Bukser", "Regntøy", "Varselklær"};
        String[] landbrukTyper = {"Traktor", "Tresker", "Hjullaster", "Nyttekjøretøy"};
        String[] gjodselTyper = {"Bladgjødsel", "Nitrogengjødsel", "NPK-gjødsel"};

        korn.setSubCategories(new ArrayList<>(Arrays.asList(kornTyper)));
        klaer.setSubCategories(new ArrayList<>(Arrays.asList(klaerTyper)));
        landbruk.setSubCategories(new ArrayList<>(Arrays.asList(landbrukTyper)));
        gjodsel.setSubCategories(new ArrayList<>(Arrays.asList(gjodselTyper)));

        addCategory(korn);
        addCategory(klaer);
        addCategory(landbruk);
        addCategory(gjodsel);
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

    /** Get metoder */

    public ObservableList<String> getCategories() {
        return mainCategories;
    }

    public ObservableList<String> getSubCategories() {
        return subCategories;
    }

    public void setCategories(ArrayList<Category> list) {
        CATEGORIES.addAll(list);
    }
}
