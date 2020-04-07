package dataModels.dataFormats;

import dataModels.Items;
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
        //disse to skal gå gjennom en validering (Det er midlertidig løsning):
        int artikkelnrInt = Integer.parseInt(artikkelnr);
        double prisD = Double.parseDouble(pris);

        return new Items(artikkelnrInt,artikkelNavn,spesifikasjoer,kategori,prisD);
    }
}
