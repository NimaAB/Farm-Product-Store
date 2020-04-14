package filehandling.bin;

import filehandling.WriterAbstract;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveBin<T> extends WriterAbstract<T> {
    private final ArrayList<T> items;
    private final String filePath;
    public SaveBin(ArrayList<T> items, String filePath){
        this.items = items;
        this.filePath = filePath;
    }

    @Override
    protected void write(ArrayList<T> items, String filePath) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for(T item:items){ objectOutputStream.writeObject(item); }

            fileOutputStream.close();
            objectOutputStream.close();
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    @Override
    public T call() {
        try { Thread.sleep(2000); }
        catch (InterruptedException ignored){}
        write(items,filePath);
        return null;
    }
}
