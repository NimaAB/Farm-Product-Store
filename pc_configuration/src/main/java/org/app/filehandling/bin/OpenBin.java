package org.app.filehandling.bin;


import org.app.filehandling.ReaderAbstract;
import org.app.validations.ioExceptions.InvalidFileException;

import java.io.*;
import java.util.ArrayList;

/**
 * Generisk klasse som arver fra ReaderAbstrakt
 * Klassen implimenterer read() fra ReaderAbstrakt og call() fra Task<>
 */
public class OpenBin<T> extends ReaderAbstract<T> {
    private final String filePath;

    public OpenBin(String filePath){
        this.filePath = filePath;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected ArrayList<T> read(String filepath) throws InvalidFileException{
        ArrayList<T> objects = new ArrayList<>();
        File file = new File(filepath);
        if(!file.exists()){
            throw new InvalidFileException("\""+file.toString()+"\""+" eksisterer ikke!");
        }else {
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream OBJ_inStream = new ObjectInputStream(fis);

                T object;

                while ((object = (T) OBJ_inStream.readObject()) != null) {
                    objects.add(object);
                }
            } catch (EOFException ignored) {
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            return objects;
        }
    }

    @Override
    public ArrayList<T> call() throws InvalidFileException {
        try{ Thread.sleep(2000); }
        catch (InterruptedException ignored){}
        return read(filePath);
    }
}
