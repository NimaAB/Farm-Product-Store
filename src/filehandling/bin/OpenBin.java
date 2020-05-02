package filehandling.bin;


import filehandling.ReaderAbstract;
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
        try{ Thread.sleep(2000); }
        catch (InterruptedException ignored){}
        return read(filePath);
    }
}
