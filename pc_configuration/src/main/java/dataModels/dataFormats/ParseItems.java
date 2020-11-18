package dataModels.dataFormats;

import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import javafx.scene.control.CheckBox;
import validations.Alerts;
import validations.customExceptions.InvalidDataException;

/**
 * Klassens metode parseItem() tar imot en text fil som string etter at denne skannet
 * og drivr med å endre hver linje av texten til objekter av type Components eller ConfigurationItems
 * avhengig av lengden på linjen
 * */
public class ParseItems {
    public static Object parseItem(String str) throws InvalidDataException {
        String[] itemsArray = str.split(",");

        if(itemsArray.length ==3){
            String nrStr = itemsArray[0];
            String name = itemsArray[1];
            String priceStr = itemsArray[2].replace(",",".");
            try{
               int nr = Integer.parseInt(nrStr);
               double price= Double.parseDouble(priceStr);
               return new ConfigurationItems(nr,name,price);
            } catch (NumberFormatException e){
                Alerts.warning(e.getMessage());
            }
        }
        else if(itemsArray.length==5){
            String componentNumber = itemsArray[0];
            String componentName = itemsArray[1];
            String componentCategory = itemsArray[2];
            String componentSpecs = itemsArray[3];
            String componentPrice = itemsArray[4];
            CheckBox b = new CheckBox();
            return new Components(componentNumber,componentName,componentCategory,componentSpecs,componentPrice,b);
        }
        throw new InvalidDataException("Feil Type: Prgrammet støtter ikke din fil på grunn av ulike antall " +
                "attributter enn det programmet forventer");
    }
}
