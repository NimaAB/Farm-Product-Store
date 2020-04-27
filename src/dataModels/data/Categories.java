package dataModels.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Categories {
    private static final String[] definedCategories = { "New Category...","Minne","Prosessor","Grafikkort","Vifte" };
    private static final ObservableList<String> categories = FXCollections.observableArrayList(definedCategories);

    public static void fillCategoryCombobox(ComboBox<String> categoryOptions, TextField txtNewCategory){
        categoryOptions.setItems(categories);
        categoryOptions.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            if(newValue.equals("New Category...")){
                txtNewCategory.setText("");
                txtNewCategory.setDisable(false);
                txtNewCategory.setText(txtNewCategory.getText());
            } else {
                txtNewCategory.setDisable(true);
                txtNewCategory.setText(newValue);
            }
        });
    }
}
