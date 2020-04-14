package filehandling.csv;

import dataModels.dataFormats.ItemsFormat;
import filehandling.WriterAbstract;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveCSV<T> extends WriterAbstract<T> {
    private final ArrayList<T> items;
    private final String filePath;
    public SaveCSV(ArrayList<T> items, String filePath){
        this.items = items;
        this.filePath = filePath;
    }

    @Override
    protected void write(ArrayList<T> items, String filePath){
        FileWriter file;
        try{
            file = new FileWriter(filePath);
            BufferedWriter writer = new BufferedWriter(file);
            String itemText = ItemsFormat.rewriteItems(items);
            writer.write(itemText);
            writer.close();
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    @Override
    public T call() {
        try{
            Thread.sleep(2000);
        }catch (InterruptedException ignored){}
        write(items,filePath);
        return null;
    }
}
