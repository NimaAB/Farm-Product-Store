package filehandling.csv;

import filehandling.ReaderAbstract;

import java.io.File;

public class ReaderCSV extends ReaderAbstract {
    private File filePath;
    public ReaderCSV(File filePath){
        this.filePath = filePath;
    }
    @Override
    public String read(File filePath) {
        //reader CSV implementation (Delimeter = ",")
        return null;
    }

    @Override
    protected String call() throws Exception {
        return read(filePath);
    }
}
