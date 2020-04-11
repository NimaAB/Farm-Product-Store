package dataModels.dataFormats;

import dataModels.data.Items;
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

        String firstCharInANavn = Character.toString(artikkelNavn.charAt(0));
        String firstCharinSpesifikasjoner = Character.toString(spesifikasjoer.charAt(0));

        String redigertArtikkelNavn= firstCharInANavn.toUpperCase() + artikkelNavn.substring(1);
        String redigertSpesifikasjoner = firstCharinSpesifikasjoner.toUpperCase() + spesifikasjoer.substring(1);

        Number [] numbers = ExceptionHandling.convertNumberInputsWcheck(artikkelnr,pris);
        int artikkernr1 = (int) numbers[0];
        double pris1 = (double) numbers[1];
        int sjekkeTall =(int) numbers[2];
        boolean validTextInput = ExceptionHandling.isValidTextInput(redigertArtikkelNavn,redigertSpesifikasjoner);
        boolean validNumbers = sjekkeTall != 0;

        if(!(validNumbers && validTextInput)){
           throw new InvalidItemDataException("Feil skriving av komponent info i CSV filen din."+
                   "Husk rekkefølge på data, og å bruk av nummer for artikkelnr. og pris.");
        }
        return new Items(artikkernr1,redigertArtikkelNavn,redigertSpesifikasjoner,kategori,pris1);
    }
}
