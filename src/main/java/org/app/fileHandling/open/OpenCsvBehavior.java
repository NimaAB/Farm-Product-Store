package org.app.fileHandling.open;

import org.app.data.dataFormats.ParseItems;
import org.app.validation.ioExceptions.InvalidTypeException;


import java.io.*;
import java.util.ArrayList;

public class OpenCsvBehavior<T> extends OpenAbstract<T>{
    @Override
    public ArrayList<T> read(File file) throws InvalidTypeException{
        ArrayList<T> items = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file.getPath());
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while((line =reader.readLine()) != null){
                    items.add((T) ParseItems.parseItem(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // ikke sikker enda!!
        if(items.size()==0){
            throw new InvalidTypeException("Filen du vil laste opp er tom!");
        }
        return items;
    }
}
