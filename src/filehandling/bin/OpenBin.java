package filehandling.bin;

import dataModels.data.Components;
import filehandling.ReaderAbstract;
import java.io.*;
import java.util.ArrayList;


public class OpenBin<T> extends ReaderAbstract<T> {
    private final String filePath;

    public OpenBin(String filePath){
        this.filePath = filePath;
    }


    @Override
    protected ArrayList<T> read(String filepath){
        ArrayList<T> objects = new ArrayList<>();
        try {
            File file = new File(filepath);
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream OBJ_inStream = new ObjectInputStream(fis);

            T object;

            while((object = (T) OBJ_inStream.readObject()) != null){
                objects.add(object); }

        } catch (EOFException ignored) {
        } catch (ClassNotFoundException | IOException e) { e.printStackTrace(); }

        return objects;
    }

    @Override
    public ArrayList<T> call(){
        try{ Thread.sleep(3000); }
        catch (InterruptedException ignored){}
        return read(filePath);
    }
}
