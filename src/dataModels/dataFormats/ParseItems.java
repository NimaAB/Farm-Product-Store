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

        Number [] numbers = ExceptionHandling.convertNumberInputsWcheck(artikkelnr,pris);
        int artikkernr1 = (int) numbers[0];
        double pris1 = (double) numbers[1];
        int sjekkeTall =(int) numbers[2];
        //TODO:make checks for all of the arguments up in a new method in the ExceptionValidation.
        // the method will return a bolean which will be used here. But the number validation will be here.
        if(sjekkeTall != 0){
            return new Items(artikkernr1,artikkelNavn,spesifikasjoer,kategori,pris1);
        }

        return null;
    }
}
