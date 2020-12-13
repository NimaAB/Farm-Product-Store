package io.open;

import validations.ioExceptions.InvalidFileException;

import java.io.File;
import java.util.ArrayList;

public abstract class OpenAbstract {
    public abstract <T> ArrayList<T> read(File file);
}
