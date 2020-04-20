package dataModels.dataFormats;

import dataModels.data.ConfigurationItems;

import java.util.ArrayList;

public class ItemsFormat {
    public static String configFormat(ConfigurationItems item){
        return item.toString(";");
    }
    public static <T> String itemsFormat(T item){
        if(item instanceof ConfigurationItems){
            return configFormat((ConfigurationItems) item);
        }
        return item.toString();
    }
    public static <T> String rewriteItems(ArrayList<T> items){
        StringBuilder itemText = new StringBuilder();
        for(T item: items){
            itemText.append(itemsFormat(item));
            itemText.append("\n");
        }
        return itemText.toString();
    }
}
