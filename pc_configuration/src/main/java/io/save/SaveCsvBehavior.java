package io.save;

import dataModels.dataFormats.ItemsFormat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveCsvBehavior extends SaveAbstract{
    @Override
    public <T> void write(String path, ArrayList<T> list) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String itemText = ItemsFormat.itemsText(list);
            bufferedWriter.write(itemText);
            bufferedWriter.close();
        } catch (IOException e){
            e.getStackTrace();
        }
    }
}
