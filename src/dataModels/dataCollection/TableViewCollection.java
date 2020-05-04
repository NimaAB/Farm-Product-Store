package dataModels.dataCollection;

import dataModels.data.Components;
import filehandling.bin.OpenBin;
import filehandling.bin.SaveBin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import validations.Alerts;

import java.util.ArrayList;

/**
 * klassen samler data til TableView og metodene gir muligheten for å behandle data.
 * */
public class TableViewCollection {
    private static final ObservableList<Components> components = FXCollections.observableArrayList();
    private static ObservableList<String> categories = FXCollections.observableArrayList();
    private static boolean reloadComponents = true;
    private static boolean modified = false;
    private static String filterChoice = "Navn";
    private static String loadedFile;

    /** Laster opp alle komponenter fra en fil og legger den til obsListen: <b>components</b>*/
    public static void loadComponents(String filePath) {
        ArrayList<Components> componentsList;
        OpenBin<Components> read = new OpenBin<>(filePath);
        loadedFile = filePath;
        componentsList = read.call();
        if (reloadComponents) {
            setComponents(componentsList,".bin");
            reloadComponents = false;
        }
    }

    /** Sletter alle komponenter som er valgt fra tabellen */
    public static void deleteSelectedComponents(){
        components.removeIf(components -> components.getCheckBox().isSelected());
        modified = true;
    }

    /** Legger en ny komponent i tabellen */
    public static void addComponent(Components component){
        for(Components c : getComponents()){
            if(component.getComponentNr()==c.getComponentNr()){
                components.remove(c);
                break;
            }
        }
        components.add(component);
        modified = true;
    }

    /** Oppdaterer filen når bruker logger ut eller programmen slutter */
    public static void saveData(){
        ArrayList<Components> data =new ArrayList<>(getComponents());
        SaveBin<Components> write = new SaveBin<>(data, loadedFile);
        write.call();
        modified = false;
    }

    /** Viser alle komponenter i tabellen */
    public static void setTableView(TableView<Components> tableView){
        tableView.setItems(getComponents());
    }

    /** Viser alle kategorier i en comboBox i skjemaen der admin oppretter nye komponenter */
    public static void fillCategoryComboBox(ComboBox<String> categoryOptions){
        String[] definedCategories = {"Minne","Prosessor","Grafikkort","Harddisk","Hovedkort","Utvidelseskort"};
        ObservableList<String> categories = FXCollections.observableArrayList(definedCategories);

        for(Components c : components){
            if(!categories.contains(c.getComponentCategory())){
                categories.add(c.getComponentCategory());
            }
        }
        categoryOptions.setEditable(true);
        categoryOptions.setPromptText("Velg Kategori");
        categoryOptions.setItems(categories);
        TableViewCollection.categories = categories;
    }

    /** Gjør det mulig til å filtrere tabellen ved komponent navn, pris, kategori osv. */
    public static void fillFilterComboBox(ComboBox<String> filterOptions){
        String[] filterCats = {"Komponent Nr", "Navn", "Kategori", "Spesifikasjoner", "Pris"};
        ObservableList<String> filterCategories = FXCollections.observableArrayList(filterCats);
        filterOptions.setItems(filterCategories);
        filterOptions.setValue("Navn");
        filterOptions.setOnAction(e -> filterChoice = filterOptions.getValue());
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

    /** Getter og Setter methods */
    public static void setComponents(ArrayList<Components> items,String fileExtention){
        if(items == null){
            components.clear();
        } else {
            for(Components i: items){
                for(Components c : components){
                    while(i.getComponentNr() == c.getComponentNr()){
                        i.setComponentNr(Integer.toString(i.getComponentNr() + 1));
                    }
                }
                CheckBox checkBox = new CheckBox();
                i.setCheckBox(checkBox);
                components.add(i);
            }

            switch (fileExtention){
                case ".bin": modified=false; break;
                case ".csv": modified=true;break;
                default:
                    System.err.println("Wrong Exention");
                    break;
            }

        }
    }
    public static ObservableList<Components> getComponents() { return components; }
    public static ObservableList<String> getCategories() { return categories; }
    public static void setReloadComponents(boolean reloadComponents1){ reloadComponents = reloadComponents1; }
    public static void setModified(boolean isModified) { modified = isModified; }
    public static boolean isModified() { return modified; }
}
