package org.app.data.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.app.Load;
import org.app.data.models.Category;
import org.app.data.models.Product;
import java.util.ArrayList;
import java.util.Arrays;

public class CategoryCollection {

    private static final ObservableList<Category> CATEGORIES = FXCollections.observableArrayList();
    private static final ObservableList<String> mainCategories = FXCollections.observableArrayList();
    private static final ObservableList<String> subCategories = FXCollections.observableArrayList();

    /** legger nye kategorier */
    public static void addCategory(Category toAdd){
        if(!CATEGORIES.contains(toAdd)) {
            mainCategories.add(toAdd.getName());
            CATEGORIES.add(toAdd);
        }
    }

    /** Når listen av kategorier ble endret, må alle combobokser oppdateres */
    public static void updateCategoriesOnChange(ComboBox<String> categoryOptions, ComboBox<String> subCategoryOptions){
        CATEGORIES.addListener((ListChangeListener<Category>) change -> setComboBoxes(categoryOptions, subCategoryOptions));
    }

    /** Når hoved kategori ble endret, må sub kategoriene oppdateres */
    public static void updateSubCategoriesOnChange(ComboBox<String> categoryOptions){
        categoryOptions.valueProperty().addListener((observableValue, oldValue, newValue) -> loadSubCategories(newValue));
    }

    /** Laster opp definerte kategorier */
    public static void loadDefinedCategories(){
        Category korn = new Category("Korn");
        Category klaer = new Category("Arbeidsklær");
        Category landbruk = new Category("Landbruk");
        Category gjodsel = new Category("Gjødsel");

        String[] kornTyper = {"Såkorn", "Økologisk såkorn", "Underkultur og fangvekster", "Plantevern"};
        String[] klaerTyper = {"Jakker", "Hansker", "Bukser", "Regntøy", "Varselklær"};
        String[] landbrukTyper = {"Traktor", "Tresker", "Hjullaster", "Nyttekjøretøy"};
        String[] gjodselTyper = {"Bladgjødsel", "Nitrogengjødsel", "NPK-gjødsel"};

        korn.addAll(new ArrayList<>(Arrays.asList(kornTyper)));
        klaer.addAll(new ArrayList<>(Arrays.asList(klaerTyper)));
        landbruk.addAll(new ArrayList<>(Arrays.asList(landbrukTyper)));
        gjodsel.addAll(new ArrayList<>(Arrays.asList(gjodselTyper)));

        addCategory(korn);
        addCategory(klaer);
        addCategory(landbruk);
        addCategory(gjodsel);
    }

    /** Setter verdier til kategori combobokser */
    public static void setComboBoxes(ComboBox<String> categoryOptions, ComboBox<String> subCategoryOptions){
        categoryOptions.setItems(mainCategories);
        subCategoryOptions.setItems(subCategories);
    }


    /** Åpner en ny vindu for kategori oppretting */
    public static void openCategoryPopup(){
        try {
            Load.window("category.fxml","Endre Kategori",new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Fyller opp sub kategori obslist med verdier basert på hoved kategorien */
    public static void loadSubCategories(String value){
        subCategories.clear();
        for(Category category: CATEGORIES) {
            if(category.getName().equals(value)) {
                subCategories.addAll(category.getSubCategories());
            }
        }
    }

    /** Fyller opp sub-kategorier combobox med riktige verdier på tableview */
    public static void updateSubCategoriesOnTableView(TableView<Product> tableView) {
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

    public static ObservableList<String> getCategories() {
        return mainCategories;
    }

    public static ObservableList<String> getSubCategories() {
        return subCategories;
    }
}
