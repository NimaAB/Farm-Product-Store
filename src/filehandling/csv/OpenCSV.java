package filehandling.csv;

import dataModels.dataFormats.ParseItems;
import validations.customExceptions.InvalidFileException;
import validations.customExceptions.InvalidItemDataException;
import filehandling.ReaderAbstract;

import java.io.*;
import java.util.ArrayList;


public class OpenCSV<T> extends ReaderAbstract<T> {
    private final String filePath;

    public OpenCSV(String filePath){
        this.filePath = filePath;
    }

    protected ArrayList<T> read(String filePath) throws InvalidItemDataException,InvalidFileException{
        ArrayList<T> items = new ArrayList<>();
        File path = new File(filePath);
        if(!path.exists()){
            throw new InvalidFileException("Filen som du skal åpne finnes ikke. Prøv en annen fil.");
        }else {
            try {
                FileReader file = new FileReader(path);
                BufferedReader reader = new BufferedReader(file);
                String line;
                while((line =reader.readLine())!=null){
                    items.add((T)ParseItems.parseItem(line));
                }
            } catch (IOException e) { e.printStackTrace(); }
            return items;
        }
    }

    @Override
    public ArrayList<T> call() throws InvalidItemDataException,InvalidFileException{
        try { Thread.sleep(3000); }
        catch (InterruptedException ignored){}
        return read(filePath);
    }
}
