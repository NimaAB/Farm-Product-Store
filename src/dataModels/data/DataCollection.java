package dataModels.data;

import filehandling.bin.OpenBin;
import filehandling.bin.SaveBin;
import filehandling.csv.OpenCSV;
import filehandling.csv.SaveCSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.ArrayList;

public class DataCollection {
    public static ObservableList<Components> components = FXCollections.observableArrayList();
    public static String loadedFile;
    private static String filterCategory = "Component Name";
    private static boolean refreshDatabase = true;
    private static boolean modified = false;

    /** Laster opp alle komponenter fra en fil og legger den til obsListen: <b>components</b>*/
    public static void loadComponents(String filepath) {
        components.clear();
        ArrayList<Components> componentsList;
        String fileExtension = filepath.substring(filepath.lastIndexOf("."));

        if(fileExtension.equals(".bin")){
            OpenBin<Components> read = new OpenBin<>(filepath);
            componentsList = read.call();
        } else {
            OpenCSV<Components> read = new OpenCSV<>(filepath);
            componentsList = read.call();
        }

        if(refreshDatabase) {
            for(Components c : componentsList){
                CheckBox checkBox = new CheckBox();
                c.setCHECKBOX(checkBox);
                components.add(c);
                refreshDatabase = false;
            }
        }
        loadedFile = filepath;
        modified = false;
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
        String[] filterCats = {"Component Number", "Component Name", "Component Category", "Component Specs", "Component Price"};
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
                    case "Component Number": if(nr.equals(filter)){ return true; } break;
                    case "Component Name": if(name.contains(filter)){ return true; } break;
                    case "Component Type": if(category.contains(filter)){ return true; } break;
                    case "Component Specs": if(specs.contains(filter)){ return true; } break;
                    case "Component Price": if(price.contains(filter)){ return true; } break;
                }
                return newValue.isEmpty();
            });
        });

        SortedList<Components> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    public static boolean isModified() { return modified; }
}
