package dataModels.dataFormats;

import dataModels.data.Components;
import javafx.scene.control.CheckBox;
import validations.customExceptions.InvalidItemDataException;

public class ParseItems {
    public static Components parseItem(String strComponent) throws InvalidItemDataException {
        String[] component = strComponent.split(";");
        String componentNumber = component[0];
        String componentName = component[1];
        String componentCategory = component[2];
        String componentSpecs = component[3];
        String componentPrice = component[4];
        CheckBox b = new CheckBox();

        return new Components(componentNumber,componentName,componentCategory,componentSpecs,componentPrice,b);
    }
}
