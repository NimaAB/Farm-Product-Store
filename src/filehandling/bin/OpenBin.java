package filehandling.bin;


import dataModels.data.Components;
import filehandling.ReaderAbstract;

import java.io.*;
import java.util.ArrayList;

public class OpenBin extends ReaderAbstract {
    private File filePath;
    public OpenBin(File filePath){
        this.filePath = filePath;
    }

    @Override
    public ArrayList<Components> read(File filePath){
        ArrayList<Components> items = new ArrayList<>();

        try{
            FileInputStream file = new FileInputStream(filePath);
            ObjectInputStream objFromFile = new ObjectInputStream(file);
            Components item;
            while((item = (Components) objFromFile.readObject()) != null){
                items.add(item);
            }
        }catch (IOException|ClassNotFoundException e){
            //Denne exception-en er ikke for bruker derfor er det ingen behov for å vise det fram.
            e.getStackTrace();
        }
        return items;
    }

    @Override
    protected ArrayList<Components> call(){
        try{
            Thread.sleep(3000);
        }catch (InterruptedException ignored){}
        return read(filePath);
    }
}
