package org.app.controllers;


import javafx.event.ActionEvent;
import org.app.data.dataCollection.CategoryCollection;
import org.app.data.models.Product;
import org.app.fileHandling.FileInfo;
import org.app.fileHandling.IOClient;
import javafx.collections.ObservableList;
import org.app.Load;
import org.app.PathDialogBox;
import org.app.data.dataCollection.TableViewCollection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.app.validation.Alerts;
import org.app.validation.NumberConversion;
import org.app.validation.customExceptions.InvalidNumberFormat;
import org.app.validation.ioExceptions.*;
import org.app.validation.Validator;
import org.app.validation.customExceptions.EmptyFieldException;
import org.app.validation.customExceptions.InvalidTextInputException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private BorderPane adminPane;
    @FXML
    private TextField name, price, txtFilter;
    @FXML
    private TextArea specifications;
    @FXML
    private ComboBox<String> categoriesCombobox;
    @FXML
    private ComboBox<String> subcategoryCombobox;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private TableView<Product> tableview;
    @FXML
    private TableColumn<Product, String> categoryCol;
    @FXML
    private TableColumn<Product, String> subcategoryCol;
    @FXML
    private TableColumn<Product, Double> priceCol;
    @FXML
    private Label filenameLabel;
    public static Label filenameLabelStatic;

    private TableSelectionModel<Product> tableSelectionModel;
    private final TableViewCollection COLLECTION = TableViewCollection.getINSTANCE();
    private String openedFile;
    private final PathDialogBox PATH_DIALOG_BOX = new PathDialogBox();

    private final NumberConversion.StringToDouble STR_2_DOUBLE = new NumberConversion.StringToDouble();


    private void setOpenedFile(String openedFile) {
        this.openedFile = openedFile;
    }

    private String getOpenedFile() {
        return openedFile;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String file = "DataFraApp/Database/products.bin";
        COLLECTION.loadComponents(file);
        COLLECTION.setTableView(tableview);
        COLLECTION.fillFilterComboBox(filterComboBox);
        COLLECTION.filterTableView(tableview, txtFilter);

        CategoryCollection.loadDefinedCategories();
        CategoryCollection.setComboBoxes(categoriesCombobox, subcategoryCombobox);
        CategoryCollection.updateCategoriesOnChange(categoriesCombobox, subcategoryCombobox);
        CategoryCollection.updateSubCategoriesOnChange(categoriesCombobox);
        CategoryCollection.updateSubCategoriesOnTableView(tableview);

        tableSelectionModel = tableview.getSelectionModel();
        tableSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        filenameLabelStatic = filenameLabel;

        categoryCol.setCellFactory(ComboBoxTableCell.forTableColumn(CategoryCollection.getCategories()));
        subcategoryCol.setCellFactory(ComboBoxTableCell.forTableColumn(CategoryCollection.getSubCategories()));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(STR_2_DOUBLE));

        price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                price.setText(newValue.replaceAll("[^\\d*(\\.\\d*)?]", ""));
            }
        });
    }

    @FXML
    void registerProduct() {

        try {

            String product_name = Validator.validateName(name.getText());
            String category = Validator.validateCategory(categoriesCombobox.getValue());
            String subcategory = Validator.validateCategory(subcategoryCombobox.getValue());
            String specs = Validator.validateSpecs(specifications.getText());
            Double product_price = Validator.validatePrice(price.getText());

            Product product = new Product(product_name, category, subcategory, specs, product_price);
            COLLECTION.addComponent(product);
            reset();

            Alerts.success("Ny Produkt Opprettet");
        } catch (InvalidTextInputException | EmptyFieldException | InvalidNumberFormat e) {
            Alerts.warning(e.getMessage());
        }
    }

    private void reset() {
        // Resetter feltene
        name.setText("");
        specifications.setText("");
        price.setText("");
    }

    @FXML
    void open() {
        boolean doOpen = Alerts.confirm("Vil du erstatte dataene du har i tabellen " +
                                        "med dataene som ligger i filen som du skal laste opp?");
        if (doOpen) {
            String path = "DataFraApp/" + PATH_DIALOG_BOX.getPathToOpen();
            try {
                PATH_DIALOG_BOX.nullPathHandling(path);
                PATH_DIALOG_BOX.extensionCheck(path);
                PATH_DIALOG_BOX.fileNotFound(path);
                FileInfo file = new FileInfo(path);
                IOClient<Product> io = new IOClient<>(file);
                io.runOpenThread();
                setOpenedFile(file.getFullPath());
            } catch (FileDontExistsException | NullPointerException | InvalidExtensionException e) {
                Alerts.warning(e.getMessage());
            }
        } else {
            Alerts.success("Dataene er ikke erstattet.");
        }
    }

    private String getPath() {
        if (getOpenedFile() == null) {
            return PATH_DIALOG_BOX.getPathToSave();
        }

        boolean newFile = Alerts.confirm("Vil du lagre filen som en ny fil?");
        if (newFile) {
            return PATH_DIALOG_BOX.getPathToSave();
        } else {
            return getOpenedFile();
        }
    }

    @FXML
    void save() {
        ArrayList<Product> components = new ArrayList<>(COLLECTION.getComponents());
        if (!components.isEmpty()) {
            String path = "DataFraApp/" + getPath();
            try {
                PATH_DIALOG_BOX.nullPathHandling(path);
                PATH_DIALOG_BOX.extensionCheck(path);
            } catch (NullPointerException | InvalidExtensionException e) {
                Alerts.warning(e.getMessage());
                return;
            }
            FileInfo file = new FileInfo(path);
            IOClient<Product> io = new IOClient<>(file, components);
            io.runSaveThread();
        } else {
            Alerts.warning("Ingenting er lagret.");
        }
    }

    @FXML
    void editName(TableColumn.CellEditEvent<Product, String> event) {
        try {
            event.getRowValue().setProductName(event.getNewValue());
            COLLECTION.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML
    void editSpecs(TableColumn.CellEditEvent<Product, String> event) {
        try {
            event.getRowValue().setSpecification(event.getNewValue());
            COLLECTION.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML
    void editCategory(TableColumn.CellEditEvent<Product, String> event) {
        try {
            event.getRowValue().setCategory(event.getNewValue());
            event.getRowValue().setSubCategory("Velg på nytt");
            Alerts.success("Du har endret foreldre kategori.\nVelg en ny subkategori.");
            COLLECTION.setModified(true);
            tableview.refresh();
        } catch (Exception e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML
    void editSubCategory(TableColumn.CellEditEvent<Product, String> event) {
        try {
            event.getRowValue().setSubCategory(event.getNewValue());
            COLLECTION.setModified(true);
            tableview.refresh();
        } catch (Exception e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML
    void editPrice(TableColumn.CellEditEvent<Product, Double> event) {
        try {
            event.getRowValue().setPrice(event.getNewValue());
            COLLECTION.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        } catch (NullPointerException ignored) {
            tableview.refresh();
        }
    }

    @FXML
    void delete() {
        ObservableList<Product> selectedRows = tableSelectionModel.getSelectedItems();
        boolean doRemove = Alerts.confirm("Er du sikker på at du vil slette varen/varene du har valgt?");
        if (doRemove) {
            COLLECTION.deleteSelectedComponents(selectedRows);
            tableview.refresh();
        } else {
            tableSelectionModel.clearSelection();
        }
    }

    @FXML
    void logOut() {
        if (COLLECTION.isModified()) {
            boolean response = Alerts.confirm("Vil du lagre alle endringer?");
            if (response) {
                COLLECTION.saveData();
            } else {
                Alerts.success("Endringer er ikke lagret");
                COLLECTION.setReloadComponents(true);
                COLLECTION.getComponents().clear();
                COLLECTION.setModified(false);
            }
            Stage stage = (Stage) adminPane.getScene().getWindow();
            Load.window("loginView.fxml", "Login", stage);
        } else {
            Stage stage = (Stage) adminPane.getScene().getWindow();
            Load.window("loginView.fxml", "Login", stage);
        }
    }

    @FXML
    void showCategoryRegister(ActionEvent actionEvent) {
       CategoryCollection.openCategoryPopup();
    }
}
