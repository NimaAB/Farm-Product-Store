package org.fileHandling.open;

import java.io.*;
import java.util.ArrayList;

public class OpenBinBehavior<T> extends OpenAbstract<T> {

    @Override
    public ArrayList<T> read(File file) {
        ArrayList<T> objects = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream OBJ_inStream = new ObjectInputStream(fis);
            T object;
            while ((object = (T) OBJ_inStream.readObject()) != null) {
                objects.add(object);
            }
        } catch (EOFException ignored) { }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return objects;
    }
}
