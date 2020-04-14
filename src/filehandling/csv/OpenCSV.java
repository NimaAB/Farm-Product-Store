package filehandling.csv;

import dataModels.dataFormats.ParseItems;
import validations.customExceptions.InvalidItemDataException;
import filehandling.ReaderAbstract;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OpenCSV<T> extends ReaderAbstract<T> {
    private final String filePath;

    public OpenCSV(String filePath){
        this.filePath = filePath;
    }

    @SuppressWarnings("unchecked")
    protected ArrayList<T> read(String filePath) throws InvalidItemDataException {
        ArrayList<T> items = new ArrayList<>();
        try {
            FileReader file = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(file);
            String line;
            while((line =reader.readLine())!=null){
                items.add((T)ParseItems.parseItem(line)); }
        } catch (IOException e) { e.printStackTrace(); }
        return items;
    }

    @Override
    public ArrayList<T> call() throws InvalidItemDataException {
        try { Thread.sleep(3000); }
        catch (InterruptedException ignored){}
        return read(filePath);
    }
}
