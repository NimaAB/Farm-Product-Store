package org.app.filehandling.csv;

import org.app.dataModels.dataFormats.ParseItems;
import org.app.validations.ioExceptions.InvalidFileException;
import org.app.validations.customExceptions.InvalidDataException;
import org.app.filehandling.ReaderAbstract;
import java.io.*;
import java.util.ArrayList;

/**
 * Generisk klasse som arver fra ReaderAbstrakt
 * Klassen implimenterer read() fra ReaderAbstrakt og call() fra Task<>
 */
public class OpenCSV<T> extends ReaderAbstract<T> {
    private final String filePath;

    public OpenCSV(String filePath){
        this.filePath = filePath;
    }

    @SuppressWarnings("unchecked")
    protected ArrayList<T> read(String filePath) throws InvalidDataException, InvalidFileException{
        ArrayList<T> items = new ArrayList<>();
        File path = new File(filePath);
        if(!path.exists()){
            throw new InvalidFileException("Filen du prøver å åpne finnes ikke. Prøv en annen fil.");
        } else {
            try {
                FileReader file = new FileReader(path);
                BufferedReader reader = new BufferedReader(file);
                String line;
                while((line =reader.readLine()) != null){
                    items.add((T)ParseItems.parseItem(line));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return items;
        }
    }

    @Override
    public ArrayList<T> call() throws InvalidDataException,InvalidFileException{
        try { Thread.sleep(3000); }
        catch (InterruptedException ignored){}
        return read(filePath);
    }
}
