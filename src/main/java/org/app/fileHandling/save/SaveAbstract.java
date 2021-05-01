package org.app.fileHandling.save;

import org.app.validation.ioExceptions.InvalidTypeException;

import java.util.ArrayList;

public abstract class SaveAbstract<T> {
     public abstract void write(String path, ArrayList<T> list) throws InvalidTypeException;

}
