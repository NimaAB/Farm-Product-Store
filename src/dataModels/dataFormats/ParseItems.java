package dataModels.dataFormats;

import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import javafx.scene.control.CheckBox;
import validations.Alerts;
import validations.customExceptions.InvalidItemDataException;

public class ParseItems {
    public static Object parseItem(String str) throws InvalidItemDataException {
        String[] itemsArray = str.split(",");

        if(itemsArray.length ==3){
            String nrStr = itemsArray[0];
            String name = itemsArray[1];
            String priceStr = itemsArray[2].replace(",",".");
            try{
               int nr = Integer.parseInt(nrStr);
               double price= Double.parseDouble(priceStr);
               return new ConfigurationItems(nr,name,price);
            }catch (NumberFormatException e){
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
        throw new InvalidItemDataException("Invalid Type of data");
    }
}
