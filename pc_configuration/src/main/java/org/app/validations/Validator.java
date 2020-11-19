package org.app.validations;

import org.app.validations.customExceptions.InvalidDataException;
import org.app.validations.customExceptions.InvalidNumberException;
import java.io.Serializable;
import java.util.regex.Pattern;

/** Denne klasse brukes til å validere input verdier på feltene
 *  når admin oppretter nye komponenter og når verdier blir redigert
 *  direkte på tableView-en */

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

    public static void validate_componentName(String componentName) {

        if(componentName.isEmpty()){
            throw new IllegalArgumentException("Komponent navn kan ikke være tomt");
        }
        String newComponentName = Character.toString(componentName.charAt(0)).toUpperCase()+componentName.substring(1);
        Validator.componentName = replaceComma(newComponentName);
    }

    public static void validate_componentCategory(String componentCategory) {
        boolean isCorrect = Pattern.matches("^[A-ZÆØÅ]?.+$",componentCategory) && !componentCategory.contains(",");
        if(!isCorrect){
            throw new IllegalArgumentException("Komponent kategori må være på riktig format og ikke være tomt"); }
        Validator.componentCategory = componentCategory;
    }

    public static void validate_componentSpecs(String componentSpecs) {
        if(componentSpecs.isEmpty()){ throw new IllegalArgumentException("Spesifikasjoner kan ikke være tomt"); }
        String newComponentSpecs = Character.toString(componentSpecs.charAt(0)).toUpperCase()
                +componentSpecs.substring(1);
        Validator.componentSpecs = replaceComma(newComponentSpecs);
    }

    public static void validate_componentNumber(String txtComponentNumber){
        try {
            componentNumber = Integer.parseInt(txtComponentNumber);
            if(componentNumber < 0){ throw new InvalidDataException("Nummeret må være større enn null"); }
        } catch (InvalidDataException | InvalidNumberException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ugyldig komponent nummer format"); }
    }

    public static void validate_componentPrice(String txtComponentPrice){
        try {
            if(txtComponentPrice.contains(",")){
                String strPrice = txtComponentPrice.replace(',','.');
                double componentPrice = Double.parseDouble(strPrice);
                if(componentPrice <= 0) { throw new InvalidDataException("Prisen må være større enn 0."); }
                Validator.componentPrice = componentPrice;
            } else {
                double componentPrice = Double.parseDouble(txtComponentPrice);
                if(componentPrice <= 0) { throw new InvalidDataException("Prisen må være større enn 0."); }
                Validator.componentPrice = componentPrice;
            }
        } catch (InvalidDataException e) {
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
