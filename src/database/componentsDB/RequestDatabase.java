package database.componentsDB;

import dataModels.data.Components;
import filehandling.bin.OpenBin;
import filehandling.bin.SaveBin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import java.util.ArrayList;

public class RequestDatabase {
    private static final ObservableList<Components> DATABASE = FXCollections.observableArrayList();
    private static boolean modified = false;

    /** DONE: READS THE <FILE DATABASE> AND SAVES ITS CONTENTS IN THE <DATABASE> OBSERVABLE LIST */
    public static void toLoadDatabase(String FILE_DATABASE) {
        OpenBin<Components> read = new OpenBin<>(FILE_DATABASE);
        ArrayList<Components> components = read.call();
        //DATABASE.addAll(components);

         /* Når Components objektet er serialisert, CheckBox blir IKKE med derfor vises den ikke på tableview
            For å se hva jeg mener, kjør koden i linje 18 istedenfor <for loop> */

        for(Components c : components){
            CheckBox checkBox = new CheckBox();
            c.setCHECKBOX(checkBox);
            DATABASE.add(c);
        }
    }

    /** DONE: DELETES COMPONENTS FROM FILE DATABASE WHEN CHECKBOX IS SELECTED. */
    public static void toDeleteSelectedComponents(){
        DATABASE.removeIf(components -> components.getCHECKBOX().isSelected());
        modified = true;
    }

    /** DONE: SAVES NEW COMPONENTS TO THE FILE DATABASE */
    public static void toSaveComponent(Components component){
        DATABASE.add(component);
        modified = true;
    }

    /** DONE: UPDATES FILE DATABASE */
    public static void toUpdateDatabase(String FILE_DATABASE){
        ArrayList<Components> updatedDatabase = new ArrayList<>(DATABASE);
        SaveBin<Components> write = new SaveBin<>(updatedDatabase, FILE_DATABASE);
        write.call();

        modified = false;
    }

    public static ObservableList<Components> getDatabase() { return DATABASE; }
}
