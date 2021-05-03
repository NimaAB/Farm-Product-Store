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
    private static final ObservableList<String> mainCategories = FXCollections.observableArrayList();
    private static final ObservableList<String> subCategories = FXCollections.observableArrayList();

    public static void addCategory(Category toAdd){
        if(!CATEGORIES.contains(toAdd)) {
            mainCategories.add(toAdd.getName());
            CATEGORIES.add(toAdd);
        }
    }

    public static void updateCategoriesOnChange(ComboBox<String> categoryOptions, ComboBox<String> subCategoryOptions){
        CATEGORIES.addListener((ListChangeListener<Category>) change -> {
            setComboBoxes(categoryOptions, subCategoryOptions);
        });
    }

    public static void updateSubCategoriesOnChange(ComboBox<String> parentCategoriesOptions){
        parentCategoriesOptions.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            subCategories.clear();
            for(Category category: CATEGORIES) {
                if(category.getName().equals(newValue)) {
                    subCategories.addAll(category.getSubCategories());
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

        addCategory(korn);
        addCategory(klaer);
        addCategory(landbruk);
        addCategory(gjodsel);
    }

    public static void setComboBoxes(ComboBox<String> categoryOptions, ComboBox<String> subCategoryOptions){
        categoryOptions.setItems(mainCategories);
        subCategoryOptions.setItems(subCategories);
    }

    public static void openCategoryPopup(){
        try {
            Load.window("category.fxml","Endre Kategori",new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<String> getCategories() {
        return mainCategories;
    }

    public static ObservableList<String> getSubCategories() {
        return subCategories;
    }
}
