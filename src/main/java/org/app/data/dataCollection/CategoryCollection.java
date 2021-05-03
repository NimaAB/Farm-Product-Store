package org.app.data.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.app.Load;
import org.app.data.models.Category;
import java.util.ArrayList;
import java.util.Arrays;

public class CategoryCollection {

    private static final ObservableList<Category> CATEGORIES = FXCollections.observableArrayList();
    private static final ObservableList<String> parentCategories = FXCollections.observableArrayList();
    private static final ObservableList<String> childCategories = FXCollections.observableArrayList();
    private static final TableViewCollection productCollection = TableViewCollection.getINSTANCE();

    public static void addCategory(Category toAdd){
        parentCategories.add(toAdd.getName());
        CATEGORIES.add(toAdd);
    }

    public static void collectionOnChange(ComboBox<String> parentOptions, ComboBox<String> childOptions){
        CATEGORIES.addListener((ListChangeListener<Category>) change -> {
            setComboBoxes(parentOptions, childOptions);
        });
    }

    public static void parentCategoriesOnChange(ComboBox<String> parentCategoriesOptions){
        parentCategoriesOptions.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            childCategories.clear();
            for(Category category: CATEGORIES) {
                if(category.getName().equals(newValue)) {
                    childCategories.addAll(category.getSubCategories());
                }
            }
        });
    }

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

        parentCategories.add(korn.getName());
        parentCategories.add(klaer.getName());
        parentCategories.add(landbruk.getName());
        parentCategories.add(gjodsel.getName());

        CATEGORIES.add(korn);
        CATEGORIES.add(klaer);
        CATEGORIES.add(landbruk);
        CATEGORIES.add(gjodsel);
    }

    public static void setComboBoxes(ComboBox<String> parentOptions, ComboBox<String> childOptions){
        parentOptions.setItems(parentCategories);
        childOptions.setItems(childCategories);
    }

    public static void openCategoryPopup(){
        try {
            Load.window("popupEditCategory.fxml","Endre Kategori",new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
