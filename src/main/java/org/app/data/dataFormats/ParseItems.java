package org.data.dataFormats;


import org.data.models.Product;
import org.validation.Validator;
import org.validation.customExceptions.EmptyFieldException;
import org.validation.customExceptions.InvalidTextInputException;
import org.validation.ioExceptions.InvalidTypeException;

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
                    "attributter enn det programmet forventer");
        }

        try{
            Integer id = Validator.isValidID(inputArray[0]);
            String name = Validator.validateName(inputArray[1]);
            String category = Validator.validateCategory(inputArray[2]);
            String subcategory = Validator.validateCategory(inputArray[3]);
            String specs = Validator.validateSpecs(inputArray[4]);
            double price = Validator.validatePrice(inputArray[5]);

            Product object = new Product(name, category, subcategory, specs, price);
            object.setProductID(id);
            return object;
        }catch (EmptyFieldException | InvalidTextInputException e){
            e.printStackTrace();
        }
        
        return null;
    }
}
