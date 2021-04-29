package dataModels.dataFormats;


import dataModels.models.Product;
import validations.customExceptions.InvalidArgument;
import validations.ioExceptions.InvalidTypeException;

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


        Product object = new Product();
        try{
            int id = Integer.parseInt(inputArray[0]);
            String name = inputArray[1];
            String category = inputArray[2];
            String subcategory = inputArray[3];
            String specs = inputArray[4];
            double price = Double.parseDouble(inputArray[5]);
            object.setProductID(id);
            object.setProductName(name);
            object.setCategory(category);
            object.setSubCategory(subcategory);
            object.setSpecification(specs);
            object.setPrice(price);
        }catch (NumberFormatException | InvalidArgument e ){
            e.printStackTrace();
        }
        
        return object;
    }
}
