package io.save;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SaveBinBehavior<T> extends SaveAbstract<T>{

    @Override
    public void write(String path, ArrayList<T> list) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            for(T item : list){ objectOutputStream.writeObject(item); }

            fileOutputStream.close();
            objectOutputStream.close();
        }catch (IOException e){
            e.getStackTrace();
        }
    }
}
