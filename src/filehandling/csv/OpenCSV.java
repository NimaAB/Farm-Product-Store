package filehandling.csv;

import dataModels.data.Items;
import dataModels.dataFormats.ParseItems;
import exceptions.dataExceptions.InvalidItemDataException;
import filehandling.ReaderAbstract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class OpenCSV extends ReaderAbstract {
    private File filePath;


    public OpenCSV(File filePath){
        this.filePath = filePath;
    }
    @Override
    public ArrayList<Items> read(File filePath) throws IOException, InvalidItemDataException {
        ArrayList<Items> items = new ArrayList<>();
        FileReader file = new FileReader(filePath);

        try(BufferedReader reader = new BufferedReader(file)){
            String line;
            while((line =reader.readLine())!=null){
                items.add(ParseItems.parseItem(line));
            }
        }
        return items;
    }



    @Override
    //  Har lagt til invaliddataexeption handling

    protected ArrayList<Items> call() throws IOException, InvalidItemDataException {
        try{
            Thread.sleep(3000);
        }catch (InterruptedException ignored){}
        return read(filePath);



    }
}