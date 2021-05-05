package org.app.data.dataCollection;

import org.app.data.models.Product;
import org.app.fileHandling.FileInfo;
import org.app.fileHandling.IOClient;

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
    private boolean reloadProducts = true;
    private boolean modified = false;
    private String filterChoice = "Navn";
    private String loadedFile;
    private static TableViewCollection INSTANCE;

    /**
     * Når et objekt av denne klasse blir opprettet, skal den alltid passe på om dataene i tableview er endret.
     */
    private TableViewCollection() {
        PRODUCTS.addListener((ListChangeListener<Product>) change -> setModified(true));
    }

    /**
     * Bruker en singel instans av denne klassen slik at dataene i tableview synkroniserer.
     */
    public static TableViewCollection getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new TableViewCollection();
        return INSTANCE;
    }

    /**
     * Laster opp alle produkter fra en fil og legger den til obsListen: <b>products</b>
     */
    public void loadProducts(String filePath) {
        IOClient<Product> open = new IOClient<>(new FileInfo(filePath));
        loadedFile = filePath;
        if (reloadProducts) {
            open.runOpenThread("Laster opp produkter...");
            reloadProducts = false;
        }
    }

    /**
     * Sletter alle produkter som er valgt fra tabellen
     */
    public void deleteSelectedProducts(ObservableList<Product> selectedProducts) {
        if (selectedProducts.size() >= 1) {
            PRODUCTS.removeAll(selectedProducts);
            setModified(true);
        }
    }

    /**
     * Legger en ny produkt i tabellen
     */
    public void addProducts(Product product) {
        for (Product p : getProducts()) {
            if (product.getProductID() == p.getProductID()) {
                PRODUCTS.remove(p);
                break;
            }
        }
        PRODUCTS.add(product);
        setModified(true);
    }

    /**
     * Oppdaterer filen når bruker logger ut eller programmen slutter
     */
    public void saveData() {
        ArrayList<Product> data = new ArrayList<>(getProducts());
        IOClient <Product> save = new IOClient<>(new FileInfo(loadedFile), data);
        save.runSaveThread("lagrer filen...");
        setModified(false);
    }

    /**
     * Viser alle produkter i tabellen
     */
    public void setTableView(TableView<Product> tableView) {
        tableView.setItems(getProducts());
    }


    /**
     * Gjør det mulig til å filtrere tabellen ved produkt navn, pris, kategori osv.
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
    public void setProducts(ArrayList<Product> items) {
        for (Product i : items) {
            for (Product p : PRODUCTS) {
                while (i.getProductID() == p.getProductID()) {
                    i.setProductID(p.getProductID() + 1);
                }
            }
            PRODUCTS.add(i);
        }
    }

    public ObservableList<Product> getProducts() {
        return PRODUCTS;
    }

    public void setReloadProducts(boolean isProductsReloaded) {
        reloadProducts = isProductsReloaded;
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
