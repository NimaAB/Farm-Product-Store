package database.componentsDB;

import dataModels.data.Components;
import filehandling.csv.OpenCSV;
import filehandling.csv.SaveCSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import java.io.File;
import java.util.ArrayList;

public class RequestDatabase {
    private static final String FILE_DATABASE = "src/database/componentsDB/dbComponents.txt";
    private static final ObservableList<Components> DATABASE = FXCollections.observableArrayList();

    /** DONE: READS THE <FILE DATABASE> AND SAVES ITS CONTENTS IN THE <DATABASE> OBSERVABLE LIST */
    public static void toLoadDatabase() {
        OpenCSV csv = new OpenCSV(new File(FILE_DATABASE));
        ArrayList<Components> fileContents = csv.read(new File(FILE_DATABASE));

        for(Components lines : fileContents){
            String componentNumber = Integer.toString(lines.getComponentNr());
            String componentName = lines.getComponentName();
            String componentCategory = lines.getComponentCategory();
            String componentSpecs = lines.getComponentSpecs();
            String componentPrice = Double.toString(lines.getComponentPrice());
            CheckBox b = new CheckBox();

            Components c = new Components(componentNumber,componentName,componentCategory,componentSpecs,componentPrice,b);
            DATABASE.add(c);
        }
    }

    /** DONE: DELETES COMPONENTS FROM FILE DATABASE WHEN CHECKBOX IS SELECTED. */
    public static void toDeleteSelectedComponents(){
        // REMOVES SELECTED COMPONENTS FROM THE TABLEVIEW
        DATABASE.removeIf(components -> components.getCHECKBOX().isSelected());

        // DELETES COMPONENTS FROM THE FILE DATABASE
        ArrayList<Components> updatedDatabase = new ArrayList<>(DATABASE);
        SaveCSV read = new SaveCSV(updatedDatabase,new File(FILE_DATABASE));
        read.run();
    }

    /** DONE: SAVES NEW COMPONENTS TO THE FILE DATABASE */
    public static void toSaveComponent(Components component){
        // ADDS THE COMPONENT TO THE TABLEVIEW
        DATABASE.add(component);

        // SAVES NEW COMPONENT IN THE FILE DATABASE
        ArrayList<Components> updatedDatabase = new ArrayList<>(DATABASE);
        SaveCSV write = new SaveCSV(updatedDatabase,new File(FILE_DATABASE));
        write.run();
    }

    public static ObservableList<Components> getDatabase() { return DATABASE; }
}
