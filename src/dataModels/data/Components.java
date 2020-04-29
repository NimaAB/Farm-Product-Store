package dataModels.data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import validations.Validator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * En model av Typen Component som er Serialisert.
 * */

public class Components implements Serializable {
    private static final long serialVersionUID = 1;
    private transient SimpleIntegerProperty componentNr;
    private transient SimpleStringProperty componentName;
    private transient SimpleStringProperty componentSpecs;
    private transient SimpleStringProperty componentCategory;
    private transient SimpleDoubleProperty componentPrice;
    private transient CheckBox checkBox;

    public Components(String componentNr, String componentName, String componentCategory, String componentSpecs, String componentPrice, CheckBox checkBox){
        Validator.validate_componentNumber(componentNr);
        Validator.validate_componentName(componentName);
        Validator.validate_componentCategory(componentCategory);
        Validator.validate_componentSpecs(componentSpecs);
        Validator.validate_componentPrice(componentPrice);

        this.componentNr = new SimpleIntegerProperty(Validator.getComponentNumber());
        this.componentName = new SimpleStringProperty(Validator.getComponentName());
        this.componentCategory = new SimpleStringProperty(Validator.getComponentCategory());
        this.componentSpecs = new SimpleStringProperty(Validator.getComponentSpecs());
        this.componentPrice = new SimpleDoubleProperty(Validator.getComponentPrice());
        this.checkBox = checkBox;
    }

    public void setComponentNr(String componentNr) {
        Validator.validate_componentNumber(componentNr);
        this.componentNr.set(Validator.getComponentNumber());
    }

    public void setComponentName(String componentName) {
        Validator.validate_componentName(componentName);
        this.componentName.set(Validator.getComponentName());
    }

    public void setComponentSpecs(String componentSpecs) {
        Validator.validate_componentSpecs(componentSpecs);
        this.componentSpecs.set(Validator.getComponentSpecs());
    }

    public void setComponentCategory(String componentCategory) {
        Validator.validate_componentCategory(componentCategory);
        this.componentCategory.set(Validator.getComponentCategory());
    }

    public void setComponentPrice(String componentPrice) {
        Validator.validate_componentPrice(componentPrice);
        this.componentPrice.set(Validator.getComponentPrice());
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public String toString(){
        return String.format("%s,%s,%s,%s,%s",
                componentNr.getValue(), componentName.getValue(), componentCategory.getValue(),
                componentSpecs.getValue(), componentPrice.getValue());
    }

    public int getComponentNr() { return componentNr.getValue(); }
    public String getComponentName() { return componentName.getValue(); }
    public String getComponentSpecs() { return componentSpecs.getValue(); }
    public String getComponentCategory() { return componentCategory.getValue(); }
    public double getComponentPrice() { return componentPrice.getValue(); }
    public CheckBox getCheckBox() { return checkBox; }

    private void writeObject(ObjectOutputStream s) throws IOException{
        s.defaultWriteObject();
        s.writeInt(getComponentNr());
        s.writeUTF(getComponentName());
        s.writeUTF(getComponentCategory());
        s.writeUTF(getComponentSpecs());
        s.writeDouble(getComponentPrice());
    }
    private void readObject(ObjectInputStream st) throws IOException,ClassNotFoundException{
        int componentNr = st.readInt();
        String componentName = st.readUTF();
        String componentCategory = st.readUTF();
        String componentSpecs = st.readUTF();
        double componentPrice = st.readDouble();

        this.componentNr = new SimpleIntegerProperty(componentNr);
        this.componentName = new SimpleStringProperty(componentName);
        this.componentCategory = new SimpleStringProperty(componentCategory);
        this.componentSpecs = new SimpleStringProperty(componentSpecs);
        this.componentPrice = new SimpleDoubleProperty(componentPrice);
    }
}
