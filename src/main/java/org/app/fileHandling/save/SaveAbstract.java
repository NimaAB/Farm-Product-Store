package org.app.fileHandling.save;

import java.util.ArrayList;

public abstract class SaveAbstract<T> {
     public abstract void write(String path, ArrayList<T> list);

}
