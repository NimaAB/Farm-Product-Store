package dataModels.data;

import database.RequestDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DataCollection {
    private static ObservableList<Components> components;
    private static String filterCategory = "Component Name";
    private static boolean refreshDatabase = true;

    public static void loadComponents(String FILE_DATABASE) {
        if(refreshDatabase){
            RequestDatabase.toLoadDatabase(FILE_DATABASE);
            components = RequestDatabase.getDatabase();
            refreshDatabase = false; }
    }

    public static void setTableView(TableView<Components> tableView){
        tableView.setItems(components);
    }

    public static void filterOnChange(ComboBox<String> filterOptions){
        String[] filterCats = {"Component Number", "Component Name", "Component Type", "Component Specs", "Component Price"};
        ObservableList<String> filterCategories = FXCollections.observableArrayList(filterCats);

        filterOptions.setItems(filterCategories);
        filterOptions.setValue("Component Name");
        filterOptions.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->{
            filterCategory = newValue; });
    }

    public static void filterTableView(TableView<Components> tableView, TextField filterTextField){
        FilteredList<Components> filteredList = new FilteredList<>(components, components -> true);
        filterTextField.textProperty().addListener((observable,oldValue,newValue)->{
            filteredList.setPredicate((components)->{
                String fname = Integer.toString(components.getComponentNr());
                String lname = components.getComponentName().toLowerCase();
                String email = components.getComponentCategory().toLowerCase();
                String phone = components.getComponentSpecs();
                String birthdate = Double.toString(components.getComponentPrice());
                String filter = newValue.toLowerCase();

                switch (filterCategory) {
                    case "Component Number": if(fname.equals(filter)){ return true; } break;
                    case "Component Name": if(lname.contains(filter)){ return true; } break;
                    case "Component Type": if(email.contains(filter)){ return true; } break;
                    case "Component Specs": if(phone.contains(filter)){ return true; } break;
                    case "Component Price": if(birthdate.contains(filter)){ return true; } break;
                }
                return newValue.isEmpty();
            });
        });

        SortedList<Components> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }
}
