package dataModels.dataFormats;

import dataModels.data.Components;
import dataModels.data.ConfigurationItems;
import javafx.scene.control.CheckBox;
import validations.Alerts;
import validations.customExceptions.InvalidItemDataException;

public class ParseItems {
    public static Object parseItem(String str) throws InvalidItemDataException {
        String[] stringArray = str.split(";");
        String[] itemsArray = str.split(",");

        if(stringArray.length ==3){
            String nrStr = stringArray[0];
            String name = stringArray[1];
            String priceStr = stringArray[2].replace(",",".");
            try{
               int nr = Integer.parseInt(nrStr);
               double price= Double.parseDouble(priceStr);
               return new ConfigurationItems(nr,name,price);
            }catch (NumberFormatException e){
                Alerts.warning(e.getMessage());
            }
        }
        else if(itemsArray.length==5){
            String componentNumber = stringArray[0];
            String componentName = stringArray[1];
            String componentCategory = stringArray[2];
            String componentSpecs = stringArray[3];
            String componentPrice = stringArray[4];
            CheckBox b = new CheckBox();
            return new Components(componentNumber,componentName,componentCategory,componentSpecs,componentPrice,b);
        }
        throw new InvalidItemDataException("Invalid Type of data");
    }
}
