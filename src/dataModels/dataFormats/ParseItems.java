package dataModels.dataFormats;

import dataModels.Items;
import exceptions.ExceptionHandling;
import exceptions.dataExceptions.InvalidItemDataException;
import exceptions.ioExceptions.InvalidItemFormatException;

public class ParseItems {
    public static Items parseItem(String itemTxt) throws InvalidItemFormatException, InvalidItemDataException {
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
        boolean validTextInput = ExceptionHandling.isValidTextInput(artikkelNavn,spesifikasjoer);
        boolean validNumbers = sjekkeTall != 0;

        if(!(validNumbers && validTextInput)){
           throw new InvalidItemDataException("Feil skriving av komponent info i CSV filen din. "+
                   "Husk rekkefølge på dataene og å bruk av nummer for artikkelnr. og pris.");
        }
        return new Items(artikkernr1,artikkelNavn,spesifikasjoer,kategori,pris1);
    }
}
