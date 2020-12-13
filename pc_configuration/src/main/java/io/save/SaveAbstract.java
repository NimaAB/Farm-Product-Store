package io.save;

import java.util.ArrayList;

public abstract class SaveAbstract  {
     public abstract <T> void write(String path, ArrayList<T> list);

}
