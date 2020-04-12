package filehandling.csv;

import dataModels.data.Components;
import dataModels.dataFormats.ParseItems;
import validations.customExceptions.InvalidItemDataException;
import filehandling.ReaderAbstract;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OpenCSV extends ReaderAbstract {
    private final File filePath;

    public OpenCSV(File filePath){
        this.filePath = filePath;
    }

    @Override
    public ArrayList<Components> read(File filePath) throws InvalidItemDataException {
        ArrayList<Components> items = new ArrayList<>();
        try{
            FileReader file = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(file);
            String line;
            while((line =reader.readLine())!=null){
                items.add(ParseItems.parseItem(line)); }
        } catch (IOException e) { e.printStackTrace(); }
        return items;
    }

    @Override
    protected ArrayList<Components> call() throws IOException, InvalidItemDataException {
        try{ Thread.sleep(3000); }
        catch (InterruptedException ignored){}
        return read(filePath);
    }
}
