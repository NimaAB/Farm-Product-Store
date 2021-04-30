package org.data.dataCollection;

import org.data.models.Product;
import org.fileHandling.FileInfo;
import org.fileHandling.IOClient;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import java.util.ArrayList;

/**
 * klassen samler data til TableView og metodene gir muligheten for å behandle data.
 */
public class TableViewCollection {

    private final ObservableList<Product> PRODUCTS = FXCollections.observableArrayList();
    private ObservableList<String> categories = FXCollections.observableArrayList();
    private ObservableList<String> subcategories = FXCollections.observableArrayList();
    private boolean reloadComponents = true;
    private boolean modified = false;
    private String filterChoice = "Navn";
    private String loadedFile;
    private static TableViewCollection INSTANCE;

    /**
     * Når et objekt av denne klasse blir opprettet, skal den alltid passe på om dataene i tableview er endret.
     */
    private TableViewCollection() {
        PRODUCTS.addListener(new ListChangeListener<Product>() {
            @Override
            public void onChanged(Change<? extends Product> change) {
                modified = true;
            }
        });
    }

    /**
     * Bruker en singel instans av denne klassen slik at dataene i tableview synkroniserer.
     */
    public static TableViewCollection getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new TableViewCollection();
        return INSTANCE;
    }

    /**
     * Laster opp alle komponenter fra en fil og legger den til obsListen: <b>components</b>
     */
    public void loadComponents(String filePath) {
        IOClient<Product> open = new IOClient<>(new FileInfo(filePath));
        loadedFile = filePath;
        if (reloadComponents) {
            open.runOpenThread();
            reloadComponents = false;
        }
        modified = false;
    }

    /**
     * Sletter alle komponenter som er valgt fra tabellen
     */
    public void deleteSelectedComponents(ObservableList<Product> selectedProducts) {
        if (selectedProducts.size() >= 1) {
            PRODUCTS.removeAll(selectedProducts);
            modified = true;
        }
    }

    /**
     * Legger en ny komponent i tabellen
     */
    public void addComponent(Product product) {
        for (Product p : getComponents()) {
            if (product.getProductID() == p.getProductID()) {
                PRODUCTS.remove(p);
                break;
            }
        }
        PRODUCTS.add(product);
        modified = true;
    }

    /**
     * Oppdaterer filen når bruker logger ut eller programmen slutter
     */
    public void saveData() {
        ArrayList<Product> data = new ArrayList<>(getComponents());
        IOClient <Product> save = new IOClient<>(new FileInfo(loadedFile), data);
        save.runSaveThread();
        modified = false;
    }

    /**
     * Viser alle komponenter i tabellen
     */
    public void setTableView(TableView<Product> tableView) {
        tableView.setItems(getComponents());
    }

    /**
     * Viser alle kategorier i en comboBox i skjemaen der admin oppretter nye komponenter
     */
    public void fillCategoryComboBox(ComboBox<String> categoryOptions, ComboBox<String> subcategoryOptions) {
        String[] definedCategories = {"Korn", "Landbruk", "Gjødsel", "Arbeidsklær"};
        ObservableList<String> categories = FXCollections.observableArrayList(definedCategories);

        for (Product p : PRODUCTS) {
            String category = p.getCategory().substring(0, 1).toUpperCase() + p.getCategory().substring(1);
            if (!categories.contains(category)) {
                categories.add(p.getCategory());
            }
        }

        categoryOptions.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            subcategoryChooser(newValue);
            subcategoryOptions.setItems(subcategories);
        });

        subcategoryOptions.setPromptText("Velg Subkategori");
        categoryOptions.setPromptText("Velg Kategori");
        categoryOptions.setItems(categories);
        this.categories = categories;
    }

    /**
     * Fyller opp subkategori combobox i tableview basert på elementenes hovedkategori
     */

    public void fillSubCategoryCombobox(TableView<Product> tableView) {

        tableView.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    if (row.getItem() != null) {
                        Product product = row.getItem();
                        subcategoryChooser(product.getCategory());
                    }
                }
            });
            return row;
        });
    }

    // Hjelper metode for ComboBox verdiene:
    private void subcategoryChooser(String category){
        String[] kornTyper = {"Såkorn", "Økologisk såkorn", "Underkultur og fangvekster", "Plantevern", ""};
        String[] klaerTyper = {"Jakker", "Hansker", "Bukser", "Regntøy", "Varselklær"};
        String[] gjodselTyper = {"Bladgjødsel", "Nitrogengjødsel", "NPK-gjødsel"};
        String[] landbruk = {"Traktor", "Tresker", "Hjullaster", "Nyttekjøretøy"};
        switch (category) {
            case "Korn":
                subcategories.clear();
                subcategories.addAll(kornTyper);
                break;
            case "Arbeidsklær":
                subcategories.clear();
                subcategories.addAll(klaerTyper);
                break;
            case "Gjødsel":
                subcategories.clear();
                subcategories.addAll(gjodselTyper);
                break;
            case "Landbruk":
                subcategories.clear();
                subcategories.addAll(landbruk);
                break;
        }
    }


    /**
     * Gjør det mulig til å filtrere tabellen ved komponent navn, pris, kategori osv.
     */
    public void fillFilterComboBox(ComboBox<String> filterOptions) {
        String[] filterCats = {"Produkt ID", "Navn", "Kategori", "Spesifikasjoner", "Pris"};
        ObservableList<String> filterCategories = FXCollections.observableArrayList(filterCats);
        filterOptions.setItems(filterCategories);
        filterOptions.setValue("Navn");
        filterOptions.setOnAction(e -> filterChoice = filterOptions.getValue());
    }

    /**
     * Filtrerer og søker gjennom tabellen
     */
    public void filterTableView(TableView<Product> tableView, TextField filterTextField) {
        FilteredList<Product> filteredList = new FilteredList<>(PRODUCTS, product -> true);
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((product) -> {
                String nr = Integer.toString(product.getProductID());
                String name = product.getProductName().toLowerCase();
                String category = product.getCategory().toLowerCase();
                String specs = product.getSpecification();
                String price = Double.toString(product.getPrice());
                String filter = newValue.toLowerCase();

                switch (filterChoice) {
                    case "Produkt ID":
                        if (nr.equals(filter)) {
                            return true;
                        }
                        break;
                    case "Navn":
                        if (name.contains(filter)) {
                            return true;
                        }
                        break;
                    case "Kategori":
                        if (category.contains(filter)) {
                            return true;
                        }
                        break;
                    case "Spesifikasjoner":
                        if (specs.contains(filter)) {
                            return true;
                        }
                        break;
                    case "Pris":
                        if (price.contains(filter)) {
                            return true;
                        }
                        break;
                }
                return newValue.isEmpty();
            });
        });

        SortedList<Product> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    /**
     * Getter og Setter methods
     */
    public void setComponents(ArrayList<Product> items) {
        for (Product i : items) {
            for (Product p : PRODUCTS) {
                while (i.getProductID() == p.getProductID()) {
                    i.setProductID(p.getProductID() + 1);
                }
            }
            PRODUCTS.add(i);
        }
    }

    public ObservableList<Product> getComponents() {
        return PRODUCTS;
    }

    public ObservableList<String> getCategories() {
        return categories;
    }

    public ObservableList<String> getSubcategories() {
        return subcategories;
    }

    public void setReloadComponents(boolean reloadComponents1) {
        reloadComponents = reloadComponents1;
    }

    public void setModified(boolean isModified) {
        modified = isModified;
    }

    public void setLoadedFile(String loadedFile1) {
        loadedFile = loadedFile1;
    }

    public boolean isModified() {
        return modified;
    }
}
