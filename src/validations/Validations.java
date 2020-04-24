package validations;

import validations.customExceptions.InvalidItemDataException;

public class Validations {
    private transient int componentNumber;
    private transient String componentName;
    private transient String componentCategory;
    private transient String componentSpecs;
    private transient double componentPrice;

    private String replaceComma(String toValidate){
        if(toValidate.contains(",")){
            toValidate = toValidate.replace(',','-');
        }
        return toValidate;
    }

    public void validate_componentName(String componentName){
        if(componentName.isEmpty()){ throw new IllegalArgumentException("Komponent navn kan ikke være tomt"); }
        this.componentName = replaceComma(componentName);
    }

    public void validate_componentCategory(String componentCategory){
        if(componentCategory.isEmpty() || componentCategory.equals("All")){
            throw new IllegalArgumentException("Komponent kategori kan ikke være tomt"); }
        this.componentCategory = componentCategory;
    }

    public void validate_componentSpecs(String componentSpecs){
        if(componentSpecs.isEmpty()){ throw new IllegalArgumentException("Spesifikasjoner kan ikke være tomt"); }
        this.componentSpecs = replaceComma(componentSpecs);
    }

    public void validate_componentNumber(String txtComponentNumber){
        try {
            this.componentNumber = Integer.parseInt(txtComponentNumber);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ugyldig komponent nummer format"); }
    }

    public void validate_componentPrice(String txtComponentPrice){
        try {
            if(txtComponentPrice.contains(",")){
                String strPrice = txtComponentPrice.replace(',','.');
                double componentPrice = Double.parseDouble(strPrice);
                if(componentPrice <= 0) { throw new InvalidItemDataException("Prisen må være større enn 0."); }
                this.componentPrice = componentPrice;
            } else {
                double componentPrice = Double.parseDouble(txtComponentPrice);
                if(componentPrice <= 0) { throw new InvalidItemDataException("Prisen må være større enn 0."); }
                this.componentPrice = componentPrice;
            }
        } catch (InvalidItemDataException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ugyldig komponent pris Format");
        }
    }

    public String getComponentName(){ return componentName; }
    public String getComponentCategory(){ return componentCategory; }
    public String getComponentSpecs(){ return componentSpecs; }
    public double getComponentPrice(){ return componentPrice; }
    public int getComponentNumber(){ return componentNumber; }
}
