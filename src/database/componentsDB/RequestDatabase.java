package database.componentsDB;

import dataModels.data.Components;
import filehandling.bin.OpenBin;
import filehandling.bin.SaveBin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class RequestDatabase {
    private static final ObservableList<Components> DATABASE = FXCollections.observableArrayList();

    /** DONE: READS THE <FILE DATABASE> AND SAVES ITS CONTENTS IN THE <DATABASE> OBSERVABLE LIST */
    public static void toLoadDatabase(String FILE_DATABASE) {
        OpenBin<Components> read = new OpenBin<>(FILE_DATABASE);
        ArrayList<Components> components = read.call();
        DATABASE.addAll(components);
    }

    /** DONE: DELETES COMPONENTS FROM FILE DATABASE WHEN CHECKBOX IS SELECTED. */
    public static void toDeleteSelectedComponents(String FILE_DATABASE){
        // REMOVES SELECTED COMPONENTS FROM THE TABLEVIEW
        DATABASE.removeIf(components -> components.getCHECKBOX().isSelected());

        // DELETES COMPONENTS FROM THE FILE DATABASE
        ArrayList<Components> updatedDatabase = new ArrayList<>(DATABASE);
        SaveBin<Components> write = new SaveBin<>(updatedDatabase, FILE_DATABASE);
        write.call();
    }

    /** DONE: SAVES NEW COMPONENTS TO THE FILE DATABASE */
    public static void toSaveComponent(Components component, String FILE_DATABASE){
        // ADDS THE COMPONENT TO THE TABLEVIEW
        DATABASE.add(component);

        // SAVES NEW COMPONENT IN THE FILE DATABASE
        ArrayList<Components> updatedDatabase = new ArrayList<>(DATABASE);
        SaveBin<Components> write = new SaveBin<>(updatedDatabase, FILE_DATABASE);
        write.call();
    }

    public static ObservableList<Components> getDatabase() { return DATABASE; }
}
