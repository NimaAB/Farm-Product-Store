package filehandling.csv;

import dataModels.Items;
import dataModels.dataFormats.ParseItems;
import filehandling.ReaderAbstract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReaderCSV extends ReaderAbstract {
    private File filePath;
    public ReaderCSV(File filePath){
        this.filePath = filePath;
    }
    @Override
    public ArrayList<Items> read(File filePath) throws IOException{
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
    protected ArrayList<Items> call() throws Exception {
        try{
            Thread.sleep(3000);
        }catch (InterruptedException ignored){}
        return read(filePath);
    }
}
