package dataModels.dataFormats;


import dataModels.models.Product;
import javafx.scene.control.CheckBox;
import validations.customExceptions.InvalidDataException;

/**
 * Klassens metode parseItem() tar imot en text fil som string etter at denne skannet
 * og drivr med å endre hver linje av texten til objekter av type Components eller ConfigurationItems
 * avhengig av lengden på linjen
 * */
public class ParseItems {
    public static Product parseItem(String str) throws InvalidDataException {
        String[] itemsArray = str.split(",");

        if(itemsArray.length!=5){
            throw new InvalidDataException("Feil Type: Prgrammet støtter ikke din fil på grunn av ulike antall " +
                    "attributter enn det programmet forventer");
        }
        return new Product("","","",12);
    }
}
