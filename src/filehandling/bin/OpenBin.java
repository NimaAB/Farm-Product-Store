package filehandling.bin;


import dataModels.data.Items;
import filehandling.ReaderAbstract;

import java.io.*;
import java.util.ArrayList;

public class OpenBin extends ReaderAbstract {
    private String filePath;
    public OpenBin(String filePath){
        this.filePath = filePath;
    }

    @Override
    public ArrayList<Items> read(String filePath){
        ArrayList<Items> items = new ArrayList<>();
        try{
            FileInputStream fs = new FileInputStream(filePath);
            ObjectInputStream objFromFile = new ObjectInputStream(fs);
            Items item;
            while((item = (Items) objFromFile.readObject()) != null){
                items.add(item);
            }
        }catch (IOException|ClassNotFoundException e){
            //Denne exception-en er ikke for bruker derfor er det ingen behov for å vise det fram.
            e.getStackTrace();
        }
        return items;
    }

    @Override
    protected ArrayList<Items> call(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException ignored){}
        return read(filePath);
    }
}
