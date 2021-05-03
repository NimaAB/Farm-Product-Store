package org.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.app.data.dataCollection.CategoryCollection;
import org.app.data.models.Category;
import org.app.validation.Alerts;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class popEditCategoryController implements Initializable {

    @FXML private ComboBox<String> parentCategory;
    @FXML private ComboBox<String> childCategory;
    @FXML private Button updateCategory;
    @FXML private Button cancelUpdate;
    @FXML private TextField parentCategoryTextField;
    @FXML private TextField childCategoryTextField;
    @FXML private Button addChildCategory;
    @FXML private ListView<String> childCategoryListview;
    @FXML private Button addCategory;
    @FXML private Button cancelAddCategory;
    private final ObservableList<String> subCategories = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryCollection.loadDefinedCategories();
        CategoryCollection.collectionOnChange(parentCategory, childCategory);
        CategoryCollection.setComboBoxes(parentCategory, childCategory);
        CategoryCollection.parentCategoriesOnChange(parentCategory);
        listViewOnChange();
    }

    @FXML
    void addCategory(ActionEvent event) {
        try {
            String categoryName = parentCategoryTextField.getText();
            Category newCategory = new Category(categoryName);
            newCategory.addAll(new ArrayList<>(subCategories));
            CategoryCollection.addCategory(newCategory);

            parentCategoryTextField.setText("");
            childCategoryTextField.setText("");
            subCategories.clear();
            Alerts.success("Kategori opprettet!");
        } catch (Exception e) {
            Alerts.warning(e.getMessage());
        }
    }

    @FXML
    void addChildCategory(ActionEvent event) {
        try {
            String subCategoryName = childCategoryTextField.getText();
            subCategories.add(subCategoryName);
            childCategoryTextField.setText("");
        } catch (Exception e) {
            Alerts.warning(e.getMessage());
        }
    }

    @FXML
    void cancelCategoryAdd(ActionEvent event) {

    }

    @FXML
    void cancelUpdate(ActionEvent event) {

    }

    @FXML
    void updateCategory(ActionEvent event) {

    }

    void listViewOnChange(){
        subCategories.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                childCategoryListview.setItems(subCategories);
            }
        });
    }
}
