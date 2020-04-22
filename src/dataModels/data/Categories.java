package dataModels.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Categories {
    public static final String[] categories = { "All","RAM","Processor","Screen","Keyboard","Mouse","New Category..." };
    private static final ObservableList<String> categories_obsList = FXCollections.observableArrayList(categories);

    public static void categoryOnChange(ComboBox<String> categoryOptions, TextField txtNewCategory){
        categoryOptions.setItems(categories_obsList);
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
