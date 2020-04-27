package validations;

import dataModels.data.Components;
import dataModels.dataCollection.TableViewCollection;
import validations.customExceptions.InvalidItemDataException;
import validations.customExceptions.InvalidNumberException;

import java.io.Serializable;

public class Validator implements Serializable {
    private static transient int componentNumber;
    private static transient String componentName;
    private static transient String componentCategory;
    private static transient String componentSpecs;
    private static transient double componentPrice;

    private static String replaceComma(String toValidate){
        if(toValidate.contains(",")){
            toValidate = toValidate.replace(',','-');
        }
        return toValidate;
    }

    public static void validate_componentName(String componentName){
        if(componentName.isEmpty()){ throw new IllegalArgumentException("Komponent navn kan ikke være tomt"); }
        Validator.componentName = replaceComma(componentName);
    }

    public static void validate_componentCategory(String componentCategory){
        if(componentCategory.isEmpty() || componentCategory.equals("All")){
            throw new IllegalArgumentException("Komponent kategori kan ikke være tomt"); }
        Validator.componentCategory = componentCategory;
    }

    public static void validate_componentSpecs(String componentSpecs){
        if(componentSpecs.isEmpty()){ throw new IllegalArgumentException("Spesifikasjoner kan ikke være tomt"); }
        Validator.componentSpecs = replaceComma(componentSpecs);
    }

    public static void validate_componentNumber(String txtComponentNumber){
        try {
            componentNumber = Integer.parseInt(txtComponentNumber);
            for(Components c : TableViewCollection.getComponents()){
                if(c.getComponentNr() == componentNumber){
                    throw new InvalidNumberException("Komponent nummeret \"" + componentNumber + "\" er tatt");
                }
            }
        } catch (InvalidNumberException e){
            throw new IllegalArgumentException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ugyldig komponent nummer format"); }
    }

    public static void validate_componentPrice(String txtComponentPrice){
        try {
            if(txtComponentPrice.contains(",")){
                String strPrice = txtComponentPrice.replace(',','.');
                double componentPrice = Double.parseDouble(strPrice);
                if(componentPrice <= 0) { throw new InvalidItemDataException("Prisen må være større enn 0."); }
                Validator.componentPrice = componentPrice;
            } else {
                double componentPrice = Double.parseDouble(txtComponentPrice);
                if(componentPrice <= 0) { throw new InvalidItemDataException("Prisen må være større enn 0."); }
                Validator.componentPrice = componentPrice;
            }
        } catch (InvalidItemDataException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ugyldig komponent pris Format");
        }
    }

    public static String getComponentName(){ return componentName; }
    public static String getComponentCategory(){ return componentCategory; }
    public static String getComponentSpecs(){ return componentSpecs; }
    public static double getComponentPrice(){ return componentPrice; }
    public static int getComponentNumber(){ return componentNumber; }
}
