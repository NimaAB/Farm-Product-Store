package dataModels.dataFormats;

import java.util.ArrayList;

public class ItemsFormat {
    public static<T> String itemsFormat(T item){
        return (String) item;
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
