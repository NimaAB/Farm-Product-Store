package org.app.fileHandling.save;

import org.app.data.dataFormats.ItemsFormat;
import org.app.validation.ioExceptions.InvalidTypeException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SaveCsvBehavior<T> extends SaveAbstract<T>{
    @Override
    public void write(String path, ArrayList<T> list) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            String itemText = ItemsFormat.itemsText(list);
            bufferedWriter.write(itemText);
            bufferedWriter.close();
        } catch (IOException | InvalidTypeException e){
            e.getStackTrace();
        }
    }
}
