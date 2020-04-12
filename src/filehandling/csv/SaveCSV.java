package filehandling.csv;

import dataModels.data.Items;
import dataModels.dataFormats.ItemsFormat;
import filehandling.WriterAbstract;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveCSV extends WriterAbstract {
    private ArrayList<Items> items;
    private String filePath;
    public SaveCSV(ArrayList<Items> items, String filePath){
        this.items = items;
        this.filePath = filePath;
    }

    @Override
    public void write(ArrayList<Items> items, String filePath){
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
    protected Void call() {
        try{
            Thread.sleep(2000);
        }catch (InterruptedException ignored){}
        write(items,filePath);
        return null;
    }
}
