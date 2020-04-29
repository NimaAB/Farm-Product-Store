
package dataModels.dataCollection;

import dataModels.data.Components;
import filehandling.bin.OpenBin;
import filehandling.bin.SaveBin;
import filehandling.csv.OpenCSV;
import filehandling.csv.SaveCSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import validations.customExceptions.InvalidFileException;
import java.util.ArrayList;

public class TableViewCollection {
    private static final ObservableList<Components> components = FXCollections.observableArrayList();
    private static boolean reloadComponents = true;
    private static boolean modified = false;
    private static String filterChoice = "Komponent Navn";
    private static String loadedFile;

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
            } else{
                OpenCSV<Components> read = new OpenCSV<>(filepath);
                componentsList = read.call();
                loaded = true;
            }

            if (reloadComponents) {
                for (Components c : componentsList) {
                    CheckBox checkBox = new CheckBox();
                    c.setCheckBox(checkBox);
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
        components.removeIf(components -> components.getCheckBox().isSelected());
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

    /** Viser alle kategorier i en comboBox i skjemaen der admin oppretter nye komponenter */
    public static void fillCategoryComboBox(ComboBox<String> categoryOptions, TextField categoryChoice){
        // Inneholder alle kategorier - både definert og nye
        ObservableList<String> categories = FXCollections.observableArrayList();

        // Definert kategorier
        String[] definedCategories = {"Nytt Kategori","Minne","Prosessor","Grafikkort"};
        categories.addAll(definedCategories);

        // Legger nye kategorier i dropdown-en
        for(Components c : components){
            if(!categories.contains(c.getComponentCategory())){
                categories.add(c.getComponentCategory());
            }
        }

        // Passer på verdien av dropdown
        categoryOptions.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            try {
                if(newValue.equals("Nytt Kategori")){
                    categoryChoice.setText("");
                    categoryChoice.setDisable(false);
                    categoryChoice.setText(categoryChoice.getText());
                } else {
                    categoryChoice.setDisable(true);
                    categoryChoice.setText(newValue);
                }
            } catch (NullPointerException ignored){}

        });
        categoryOptions.setValue("Minne");
        categoryOptions.setItems(categories);
    }

    /** Gjør det mulig til å filtrere tabellen ved komponent navn, pris, kategori osv. */
    public static void fillFilterComboBox(ComboBox<String> filterOptions){
        String[] filterCats = {"Komponent Nr", "Navn", "Kategori", "Spesifikasjoner", "Pris"};
        ObservableList<String> filterCategories = FXCollections.observableArrayList(filterCats);

        filterOptions.setItems(filterCategories);
        filterOptions.setValue("Navn");
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
                    case "Navn": if(name.contains(filter)){ return true; } break;
                    case "Kategori": if(category.contains(filter)){ return true; } break;
                    case "Spesifikasjoner": if(specs.contains(filter)){ return true; } break;
                    case "Pris": if(price.contains(filter)){ return true; } break;
                }
                return newValue.isEmpty();
            });
        });

        SortedList<Components> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    /** Dropdown som vises når kategori blir oppdatert direkte i tableview-en */
    public static ObservableList<String> onEditCategories(){
        ObservableList<String> onEditCategories = FXCollections.observableArrayList();
        for(Components c : components){
            if(!onEditCategories.contains(c.getComponentCategory())){
                onEditCategories.add(c.getComponentCategory());
            }
        }
        return onEditCategories;
    }

    /** Getter og Setter methods */
    public static ObservableList<Components> getComponents() { return components; }
    public static void setModified(boolean modified) { TableViewCollection.modified = modified; }
    public static boolean isModified() { return modified; }
}
