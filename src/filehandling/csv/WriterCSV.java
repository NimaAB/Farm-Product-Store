package filehandling.csv;

import filehandling.WriterAbstract;

import java.io.File;
import java.util.ArrayList;

public class WriterCSV extends WriterAbstract {

    @Override
    protected <E> void write(ArrayList<E> objects, File filePath) {

    }

    @Override
    protected Void call() throws Exception {
        return null;
    }
}
