package dataModels.data;

import filehandling.bin.OpenBin;
import filehandling.bin.SaveBin;
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
    private static String filterCategory = "Component Name";
    private static boolean refreshDatabase = true;
    private static boolean modified = false;


    /** LOADS ALL COMPONENTS FROM BIN FILE AND ADDS IT TO: <b>components</b> */
    public static void loadComponents(String filepath) {
        OpenBin<Components> read = new OpenBin<>(filepath);
        ArrayList<Components> componentsList = read.call();
        if(refreshDatabase) {
            for(Components c : componentsList){
                CheckBox checkBox = new CheckBox();
                c.setCHECKBOX(checkBox);
                components.add(c);
                refreshDatabase = false;
            }
        }
    }

    /** DELETES COMPONENTS FROM THE TABLEVIEW */
    public static void deleteSelectedComponents(){
        components.removeIf(components -> components.getCHECKBOX().isSelected());
        modified = true;
    }

    /** ADDS A NEW COMPONENT IN THE TABLEVIEW */
    public static void addComponent(Components component){
        components.add(component);
        modified = true;
    }

    /** UPDATES BIN FILE */
    public static void saveBinData(String filepath){
        ArrayList<Components> updatedDatabase = new ArrayList<>(components);
        SaveBin<Components> write = new SaveBin<>(updatedDatabase, filepath);
        write.call();

        modified = false;
    }

    /** SHOWS ALL COMPONENTS IN THE TABLE VIEW */
   public static void setTableView(TableView<Components> tableView){
        tableView.setItems(components);
   }

    /** ENABLES TABLE VIEW TO BE FILTERED BY COMPONENT NAME, PRICE ETC */
    public static void filterOnChange(ComboBox<String> filterOptions){
        String[] filterCats = {"Component Number", "Component Name", "Component Category", "Component Specs", "Component Price"};
        ObservableList<String> filterCategories = FXCollections.observableArrayList(filterCats);

        filterOptions.setItems(filterCategories);
        filterOptions.setValue("Component Name");
        filterOptions.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            filterCategory = newValue; });
    }

    /** FILTERS AND SEARCHES THROUGH THE TABLE VIEW */
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
    public static ObservableList<Components> getComponents() { return components; }
}
