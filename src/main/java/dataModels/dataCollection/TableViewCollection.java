package dataModels.dataCollection;

import dataModels.models.Product;
import io.FileInfo;
import io.fileThreads.OpenThread;
import io.fileThreads.SaveThread;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import validations.Alerts;
import validations.ioExceptions.InvalidTypeException;
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
        try {
            OpenThread<Product> openTh = new OpenThread<>(new FileInfo(filePath));
            ArrayList<Product> componentList = openTh.call();
            loadedFile = filePath;
            if (reloadComponents) {
                setComponents(componentList);
                reloadComponents = false;
            }
        } catch (InvalidTypeException e) {
            Alerts.warning(e.getMessage());
        }
        modified = false;
    }

    /**
     * Sletter alle komponenter som er valgt fra tabellen
     */
    public void deleteSelectedComponents(ObservableList<Product> selectedProducts) {
        if (selectedProducts.size() >= 1) {
            PRODUCTS.removeAll(selectedProducts);
        } else {
            return;
        }
        //throw new UnsupportedOperationException("Not yet implemented");
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
    }

    /**
     * Oppdaterer filen når bruker logger ut eller programmen slutter
     */
    public void saveData() {
        ArrayList<Product> data = new ArrayList<>(getComponents());
        SaveThread<Product> saveTh = new SaveThread<>(new FileInfo(loadedFile), data);
        saveTh.call();
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
        String[] definedCategories = {"Korn", "Traktør", "Jødsel", "Arbeidsklær"};
        ObservableList<String> categories = FXCollections.observableArrayList(definedCategories);

        String[] kornTyper = {"Korn Type 1", "Korn Type 2"};
        String[] klærTyper = {"Klær Type 1", "Klær Type 2"};
        String[] jødselTyper = {"Jødsel Type 1", "Jødsel Type 2"};
        String[] traktørTyper = {"Traktor Type 1", "Traktor Type 2"};

        for (Product p : PRODUCTS) {
            String category = p.getCategory().substring(0, 1).toUpperCase() + p.getCategory().substring(1);
            if (!categories.contains(category)) {
                categories.add(p.getCategory());
            }
        }

        categoryOptions.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                switch (newValue) {
                    case "Korn":
                        subcategories.clear();
                        subcategories.addAll(kornTyper);
                        break;
                    case "Arbeidsklær":
                        subcategories.clear();
                        subcategories.addAll(klærTyper);
                        break;
                    case "Jødsel":
                        subcategories.clear();
                        subcategories.addAll(jødselTyper);
                        break;
                    case "Traktør":
                        subcategories.clear();
                        subcategories.addAll(traktørTyper);
                        break;
                }
                subcategoryOptions.setItems(subcategories);
            }
        });

        subcategoryOptions.setEditable(true);
        subcategoryOptions.setPromptText("Velg Subkategori");

        categoryOptions.setEditable(true);
        categoryOptions.setPromptText("Velg Kategori");
        categoryOptions.setItems(categories);
        this.categories = categories;
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
