package io.open;

import validations.ioExceptions.InvalidTypeException;

import java.io.File;
import java.util.ArrayList;

public abstract class OpenAbstract<T> {
    public abstract ArrayList<T> read(File file) throws InvalidTypeException;
}
