package filehandling.jobj;


import dataModels.Items;
import filehandling.ReaderAbstract;

import java.io.*;
import java.util.ArrayList;

public class ReaderJOBJ extends ReaderAbstract {
    private File filePath;
    public ReaderJOBJ(File filePath){
        this.filePath = filePath;
    }

    @Override
    public ArrayList<Items> read(File filePath){
        ArrayList<Items> items = new ArrayList<>();

        try{
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream objFromFile = new ObjectInputStream(file);
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
