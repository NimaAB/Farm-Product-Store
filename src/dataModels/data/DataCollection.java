
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

import javax.print.attribute.standard.Finishings;
import java.util.ArrayList;
import java.util.Observable;

public class DataCollection {
    public static ObservableList<Components> components = FXCollections.observableArrayList();
    public static ObservableList<ConfigurationItems> selectedItems = FXCollections.observableArrayList();
    public static String loadedFile;
    private static String filterCategory = "Komponent Navn";
    private static boolean refreshDatabase = true;
    private static boolean modified = false;

    /** Laster opp alle komponenter fra en fil og legger den til obsListen: <b>components</b>*/
    public static void loadComponents(String filepath) {
        boolean loaded;
        ArrayList<Components> componentsList;
        String fileExtension = filepath.substring(filepath.lastIndexOf("."));
        try {
            if (fileExtension.equals(".bin")) {
                OpenBin<Components> read = new OpenBin<>(filepath);
                componentsList = read.call();
                loaded = false;
            } else {
                OpenCSV<Components> read = new OpenCSV<>(filepath);
                componentsList = read.call();
                loaded = true;
            }
            if (refreshDatabase) {
                for (Components c : componentsList) {
                    CheckBox checkBox = new CheckBox();
                    c.setCHECKBOX(checkBox);
                    components.add(c);
                    refreshDatabase = false;
                }
            }
            loadedFile = filepath;
            modified = false;
        } catch (InvalidFileException ignored){
            loaded = false;
        }
        if(loaded){
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
            SaveBin<Components> write = new SaveBin<>(data,loadedFile);
            write.call();
        } else {
            SaveCSV<Components> write = new SaveCSV<>(data,loadedFile);
            write.call();
        }
        modified = false;
    }

    /** Viser alle komponenter i tabellen */
    public static void setTableView(TableView<Components> tableView){
        tableView.setItems(components);
    }

    /** Gjør det mulig til å filtrere tabellen ved komponent navn, pris, kategori osv. */
    public static void filterOnChange(ComboBox<String> filterOptions){
        String[] filterCats = {"Komponent Nr", "Komponent Navn", "Komponent Kategori", "Komponent Speks.", "Komponent Pris"};
        ObservableList<String> filterCategories = FXCollections.observableArrayList(filterCats);

        filterOptions.setItems(filterCategories);
        filterOptions.setValue("Component Name");
        filterOptions.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            filterCategory = newValue; });
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

                switch (filterCategory) {
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

    public static void fillCombox (ComboBox <String> comboBox ){
        comboBox.setValue("Velg Katogri");

        // den katogrie listen må vi koble til katogriene som allerede bestemt på admin nivå.
        comboBox.getItems().addAll("All","Processor", "Screen", "RAM", "Keyboard", "Mouse");


    }
    public static void selectedTable (String categoryName, TableView<Components> tableView ){
        ObservableList<Components> selectedCatogries = FXCollections.observableArrayList();

        for (Components obj : components){
            if (obj.getComponentCategory().equals(categoryName)){
                selectedCatogries.add(obj);
            }
        }
        //Sjekk om All-Categories er valgt, Hvis ja så setter den TV som før:
        if(categoryName.equals("All")){
            selectedCatogries = components;
        }
        SortedList<Components> sortedList = new SortedList<>(selectedCatogries);
        tableView.setItems(sortedList);
    }

    public static void addToShoppingCart(ListView <ConfigurationItems> shoppingCart){

        for ( Components itemObj : components){
            if (itemObj.getCHECKBOX().isSelected()){
                itemObj.getCHECKBOX().setSelected(false);
                String itemName = itemObj.getComponentName();
                double  itemPrice = itemObj.getComponentPrice();
                int itemNr = itemObj.getComponentNr();

                ConfigurationItems item = new ConfigurationItems(itemNr,itemName,itemPrice);
                boolean alerta;
                boolean sjekk = false;
                for ( ConfigurationItems doesItExist : selectedItems){
                    if (doesItExist.toString().equals(item.toString())){
                        alerta = MyAlerts.confirmAlert("Varen finnes allerede i handlekurven.\n Vil du legge til ? " );
                        sjekk = true;
                        if (alerta){
                            selectedItems.add(item);
                            break;
                        }
                    }
                }
                if (!sjekk){
                    selectedItems.add(item);
                    break;
                }
            }
        }
        shoppingCart.setItems(selectedItems);
    }

    public static boolean isModified() { return modified; }
}