package io.open;

import java.io.File;
import java.util.ArrayList;

public abstract class OpenAbstract<T> {
    public abstract ArrayList<T> read(File file);
}
