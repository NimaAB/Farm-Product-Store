package validations;

import validations.customExceptions.InvalidItemDataException;

public class Validations {
    private transient int componentNumber;
    private transient String componentName;
    private transient String componentCategory;
    private transient String componentSpecs;
    private transient double componentPrice;

    public void validate_componentName(String componentName){
        if(componentName.isEmpty()){ throw new IllegalArgumentException("Component name cannot be empty"); }
        this.componentName = componentName;
    }

    public void validate_componentCategory(String componentCategory){
        if(componentCategory.isEmpty()){ throw new IllegalArgumentException("Component category cannot be empty"); }
        this.componentCategory = componentCategory;
    }

    public void validate_componentSpecs(String componentSpecs){
        if(componentSpecs.isEmpty()){ throw new IllegalArgumentException("Specifications cannot be empty"); }
        this.componentSpecs = componentSpecs;
    }

    public void validate_componentNumber(String txtComponentNumber){
        try {
            this.componentNumber = Integer.parseInt(txtComponentNumber);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Component Number Format"); }
    }

    public void validate_componentPrice(String txtComponentPrice){
        try {
            if(txtComponentPrice.contains(",")){
                String strPrice = txtComponentPrice.replace(',','.');
                double componentPrice = Double.parseDouble(strPrice);
                if(componentPrice <= 0) { throw new InvalidItemDataException("Price must be greater than 0."); }
                this.componentPrice = componentPrice;
            } else {
                double componentPrice = Double.parseDouble(txtComponentPrice);
                if(componentPrice <= 0) { throw new InvalidItemDataException("Price must be greater than 0."); }
                this.componentPrice = componentPrice;
            }
        } catch (InvalidItemDataException e) {
            throw new IllegalArgumentException(e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Component Price Format");
        }
    }

    public String getComponentName(){ return componentName; }
    public String getComponentCategory(){ return componentCategory; }
    public String getComponentSpecs(){ return componentSpecs; }
    public double getComponentPrice(){ return componentPrice; }
    public int getComponentNumber(){ return componentNumber; }
}
