
package dataModels.data;

import filehandling.bin.OpenBin;
import filehandling.bin.SaveBin;
import filehandling.csv.OpenCSV;
import filehandling.csv.SaveCSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import validations.MyAlerts;
import validations.customExceptions.InvalidFileException;

import java.util.ArrayList;

public class DataCollection {
    public static ObservableList<Components> components = FXCollections.observableArrayList();
    private static final ObservableList<Components> selectedItems = FXCollections.observableArrayList();
    private static final ObservableList<ConfigurationItems> configItems = FXCollections.observableArrayList();
    public static boolean modified = false;
    private static boolean reloadComponents = true;
    private static String loadedFile;
    private static String filterChoice = "Komponent Navn";

    /** Laster opp alle komponenter fra en fil og legger den til obsListen: <b>components</b>*/
    public static void loadComponents(String filepath) {
        String fileExtension = filepath.substring(filepath.lastIndexOf("."));
        ArrayList<Components> componentsList;
        boolean loaded;

        try {
            loadedFile = filepath;

            if (fileExtension.equals(".bin")) {
                OpenBin<Components> read = new OpenBin<>(filepath);
                componentsList = read.call();
                loaded = false;
            } else {
                OpenCSV<Components> read = new OpenCSV<>(filepath);
                componentsList = read.call();
                loaded = true;
            }

            if (reloadComponents) {
                for (Components c : componentsList) {
                    CheckBox checkBox = new CheckBox();
                    c.setCHECKBOX(checkBox);
                    components.add(c);
                    reloadComponents = false;
                }
            }

        } catch (InvalidFileException ignored){
            loaded = false;
        }

        if (loaded){
            components.clear();
        }
    }

    /** Sletter alle komponenter som er valgt fra tabellen */
    public static void deleteSelectedComponents(){
        components.removeIf(components -> components.getCHECKBOX().isSelected());
        modified = true;
    }

    /** Legger en ny komponent i tabellen */
    public static void addComponent(Components component){
        components.add(component);
        modified = true;
    }

    /** Oppdaterer filen når bruker logger ut eller programmen slutter */
    public static void saveData(){
        ArrayList<Components> data = new ArrayList<>(components);
        String fileExtension = loadedFile.substring(loadedFile.lastIndexOf("."));

        if(fileExtension.equals(".bin")){
            SaveBin<Components> write = new SaveBin<>(data, loadedFile);
            write.call();
        } else {
            SaveCSV<Components> write = new SaveCSV<>(data, loadedFile);
            write.call();
        }

        modified = false;
    }

    /** Viser alle komponenter i tabellen */
    public static void setTableView(TableView<Components> tableView){
        tableView.setItems(components);
    }

    /** Gjør det mulig til å filtrere tabellen ved komponent navn, pris, kategori osv. */
    public static void fillFilterComboBox(ComboBox<String> filterOptions){
        String[] filterCats = {"Komponent Nr", "Komponent Navn", "Komponent Kategori", "Komponent Speks.", "Komponent Pris"};
        ObservableList<String> filterCategories = FXCollections.observableArrayList(filterCats);

        filterOptions.setItems(filterCategories);
        filterOptions.setValue("Komponent Navn");
        filterOptions.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            filterChoice = newValue; });
    }

    /** Filtrerer og søker gjennom tabellen */
    public static void filterTableView(TableView<Components> tableView, TextField filterTextField){
        FilteredList<Components> filteredList = new FilteredList<>(components, components -> true);
        filterTextField.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate((components)->{
                String nr = Integer.toString(components.getComponentNr());
                String name = components.getComponentName().toLowerCase();
                String category = components.getComponentCategory().toLowerCase();
                String specs = components.getComponentSpecs();
                String price = Double.toString(components.getComponentPrice());
                String filter = newValue.toLowerCase();

                switch (filterChoice) {
                    case "Komponent Nr": if(nr.equals(filter)){ return true; } break;
                    case "Komponent Navn": if(name.contains(filter)){ return true; } break;
                    case "Komponent Kategori": if(category.contains(filter)){ return true; } break;
                    case "Komponent Speks.": if(specs.contains(filter)){ return true; } break;
                    case "Komponent Pris": if(price.contains(filter)){ return true; } break;
                }
                return newValue.isEmpty();
            });
        });

        SortedList<Components> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }




    /** Viser alle komponent kategorier i en comboBox */
    public static void fillCategoryComboBox(ComboBox<String> comboBox ){
        ObservableList<String> categories = FXCollections.observableArrayList();
        categories.add("All");
        for(Components item : components){
            if(!categories.contains(item.getComponentCategory())){
                categories.add(item.getComponentCategory());
            }
        }
        comboBox.setValue("Velg Kategori");
        comboBox.setItems(categories);
    }

    /** Viser komponenter i tabellen basert på kategori */
    public static void selectedTable (String categoryName, TableView<Components> tableView ){
        ObservableList<Components> selectedCatogries = FXCollections.observableArrayList();
        for (Components obj : components){
            if (obj.getComponentCategory().equals(categoryName)){
                selectedCatogries.add(obj);
            }
        }
        if(categoryName.equals("All")){
            selectedCatogries = components;
        }
        SortedList<Components> sortedList = new SortedList<>(selectedCatogries);
        tableView.setItems(sortedList);
    }

    /** Viser valgte ConfigItems i listview */
    public static void setListView(ListView <ConfigurationItems> shoppingCart){
        shoppingCart.setItems(configItems);
    }

    /** Legger ConfigItems i listview */
    public static void addToShoppingCart(){
        for(Components c : components){
            if(c.getCHECKBOX().isSelected()){
                if(selectedItems.contains(c)){
                    boolean response = MyAlerts.confirmAlert("Vil du legge <" + c.getComponentName() + "> igjen?");
                    if(response){
                        selectedItems.add(c);
                        int nr = c.getComponentNr();
                        String navn = c.getComponentName();
                        double pris = c.getComponentPrice();
                        configItems.add(new ConfigurationItems(nr,navn,pris));
                    }
                } else {
                    selectedItems.add(c);
                    int nr = c.getComponentNr();
                    String navn = c.getComponentName();
                    double pris = c.getComponentPrice();
                    configItems.add(new ConfigurationItems(nr,navn,pris));
                }
            }
        }
    }

    /** Viser total prisen til alle ConfigItems */
    public static void showTotalPrice(Label totalPriceLbl){
        double totalPrice = ConfigurationItems.totalPrice(configItems);
        totalPriceLbl.setText(Double.toString(totalPrice));
    }

    /** Resetter listview for en ny konfigurasjon */
    public static void newConfiguration(){
        selectedItems.clear();
        configItems.clear();
    }

    /** Sletter ConfigItems fra listview */
    public static void  deleteItemList (ListView<ConfigurationItems> list, Label totalPriceLbl){
        configItems.removeIf(item -> item.getNr() == list.getSelectionModel().getSelectedItem().getNr());
        showTotalPrice(totalPriceLbl);
    }
}
