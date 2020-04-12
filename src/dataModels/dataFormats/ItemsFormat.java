package dataModels.dataFormats;

import dataModels.data.Components;

import java.util.ArrayList;

public class ItemsFormat {
    public static final String DELIMITER = ";";

    // Den er unødvendig hvis items har en toString metode
    // selvfølgelig hvis den har andre oppgaver, kan vi beholde den

    public static String itemsFormat(Components item){
        return item.getComponentNr()+DELIMITER+item.getComponentName()+DELIMITER
                +item.getComponentCategory()+DELIMITER+item.getComponentSpecs()
                +DELIMITER+item.getComponentPrice();
    }
    public static String rewriteItems(ArrayList<Components> items){
        StringBuilder itemText = new StringBuilder();
        for(Components item: items){
            itemText.append(itemsFormat(item));
            itemText.append("\n");
        }
        return itemText.toString();
    }
}
