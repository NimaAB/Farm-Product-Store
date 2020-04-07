package dataModels.dataFormats;

import dataModels.Items;
import exceptions.ExceptionHandling;
import exceptions.ioExceptions.InvalidItemFormatException;

public class ParseItems {
    public static Items parseItem(String itemTxt) throws InvalidItemFormatException{
        String [] items = itemTxt.split(";");
        if(items.length !=5){
            throw new InvalidItemFormatException("Fiel bruk av skilleten (;)");
        }
        String artikkelnr = items[0];
        String artikkelNavn = items[1];
        String kategori = items[2];
        String spesifikasjoer = items[3];
        String pris = items[4];

        //TODO:make a seperate method for this in a new class maybe!??
        Number [] numbers = ExceptionHandling.numberInputs(artikkelnr,pris);
        int artikkernr1 = (int) numbers[0];
        double pris1 = (double) numbers[1];
        int sjekkeTall =(int) numbers[2];
        //TODO:make cheks for all of the arguments up.
        if(sjekkeTall != 0){
            return new Items(artikkernr1,artikkelNavn,spesifikasjoer,kategori,pris1);
        }

        return null;
    }
}
