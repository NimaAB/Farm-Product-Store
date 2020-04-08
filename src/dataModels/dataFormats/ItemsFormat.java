package dataModels.dataFormats;

import dataModels.Items;

import java.util.ArrayList;

public class ItemsFormat {
    public static final String DELIMITER = ";";

    public static String itemsFormat(Items item){
        return item.getArtikkelnr()+DELIMITER+item.getArtikkelNavn()+DELIMITER
                +item.getKategori()+DELIMITER+item.getSpesifikasjoner()
                +DELIMITER+item.getPris();
    }
    public static String rewriteItems(ArrayList<Items> items){
        StringBuilder itemText = new StringBuilder();
        for(Items item: items){
            itemText.append(itemsFormat(item));
            itemText.append("\n");
        }
        return itemText.toString();
    }
}
