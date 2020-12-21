package dataModels.dataFormats;

import java.util.ArrayList;

/**
 * All Data som blir skrevet til csv fil
 * går gjennom denne klassen for å få sin text format.
 * to av metodene er generiske pga. vi printer ut to Typer til csv-fil
 * Components og ConfigurationItems.
 */

public class ItemsFormat {

    public static <T> String itemsFormat(T item){
        return item.toString();
    }
    public static <T> String itemsText(ArrayList<T> items){
        StringBuilder itemText = new StringBuilder();
        for(T item: items){
            itemText.append(itemsFormat(item));
            itemText.append("\n");
        }
        return itemText.toString();
    }
}
