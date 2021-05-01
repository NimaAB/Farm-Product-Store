package org.app.data.dataFormats;


import org.app.data.models.Product;
import org.app.validation.Validator;
import org.app.validation.customExceptions.EmptyFieldException;
import org.app.validation.customExceptions.InvalidNumberFormat;
import org.app.validation.customExceptions.InvalidTextInputException;
import org.app.validation.ioExceptions.InvalidTypeException;

/**
 * Klassens metode parseItem() tar imot en text fil som string etter at denne skannet
 * og drivr med å endre hver linje av texten til objekter av type Components eller ConfigurationItems
 * avhengig av lengden på linjen
 * */
public class ParseItems {
    public static Product parseItem(String str) throws InvalidTypeException {
        String[] inputArray = str.split(""+ItemsFormat.DELIMITER);
        if(inputArray.length != 6){
            throw new InvalidTypeException("Feil Type: Programmet støtter ikke din fil på grunn av ulike antall " +
                    "attributter enn det programmet forventer!");
        }
        Product object;
        try{
            Integer id = Validator.isValidID(inputArray[0]);
            String name = Validator.validateName(inputArray[1]);
            String category = Validator.validateCategory(inputArray[2]);
            String subcategory = Validator.validateCategory(inputArray[3]);
            String specs = Validator.validateSpecs(inputArray[4]);
            if(specs.contains("|")){
                specs = specs.replace('|',ItemsFormat.DELIMITER.charAt(0));
            }
            double price = Validator.validatePrice(inputArray[5]);

            object = new Product(name, category, subcategory, specs, price);
            object.setProductID(id);
        }catch (EmptyFieldException | InvalidTextInputException | InvalidNumberFormat e){
            throw new InvalidTypeException("Feil: Dataene i CSV-filen støttes ikke av programmet!\n" +
                    "Sjekk om alle dataene er i riktig format og rekkefølge.");
        }

        return object;
    }
}
