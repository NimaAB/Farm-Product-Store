package dataModels.data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.CheckBox;
import validations.Validations;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Components implements Serializable {
    private transient SimpleIntegerProperty componentNr;
    private transient SimpleStringProperty componentName;
    private transient SimpleStringProperty componentSpecs;
    private transient SimpleStringProperty componentCategory;
    private transient SimpleDoubleProperty componentPrice;
    private transient CheckBox checkBox;
    private transient final Validations DATA = new Validations();

    public Components(String componentNr, String componentName, String componentCategory, String componentSpecs, String componentPrice, CheckBox checkBox){
        DATA.validate_componentNumber(componentNr);
        DATA.validate_componentName(componentName);
        DATA.validate_componentCategory(componentCategory);
        DATA.validate_componentSpecs(componentSpecs);
        DATA.validate_componentPrice(componentPrice);

        this.componentNr = new SimpleIntegerProperty(DATA.getComponentNumber());
        this.componentName = new SimpleStringProperty(DATA.getComponentName());
        this.componentCategory = new SimpleStringProperty(DATA.getComponentCategory());
        this.componentSpecs = new SimpleStringProperty(DATA.getComponentSpecs());
        this.componentPrice = new SimpleDoubleProperty(DATA.getComponentPrice());
        this.checkBox = checkBox;
    }

    public void setComponentNr(String componentNr) {
        DATA.validate_componentNumber(componentNr);
        this.componentNr.set(DATA.getComponentNumber());
    }

    public void setComponentName(String componentName) {
        DATA.validate_componentName(componentName);
        this.componentName.set(DATA.getComponentName());
    }

    public void setComponentSpecs(String componentSpecs) {
        DATA.validate_componentSpecs(componentSpecs);
        this.componentSpecs.set(DATA.getComponentSpecs());
    }

    public void setComponentCategory(String componentCategory) {
        DATA.validate_componentCategory(componentCategory);
        this.componentCategory.set(DATA.getComponentCategory());
    }

    public void setComponentPrice(String componentPrice) {
        DATA.validate_componentPrice(componentPrice);
        this.componentPrice.set(DATA.getComponentPrice());
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
