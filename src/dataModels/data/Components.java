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
    private transient CheckBox CHECKBOX;
    private transient final Validations DATA = new Validations();

    public Components(String componentNr, String componentName, String componentCategory, String componentSpecs, String componentPrice, CheckBox CHECKBOX){
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
        this.CHECKBOX = CHECKBOX;
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

    public void setCHECKBOX(CheckBox CHECKBOX) {
        this.CHECKBOX = CHECKBOX;
    }

    public String toString(){
        return String.format("%s;%s;%s;%s;%s",
                componentNr.get(), componentName.get(), componentCategory.get(),
                componentSpecs.get(), componentPrice.get());
    }

    public int getComponentNr() { return componentNr.get(); }
    public String getComponentName() { return componentName.get(); }
    public String getComponentSpecs() { return componentSpecs.get(); }
    public String getComponentCategory() { return componentCategory.get(); }
    public double getComponentPrice() { return componentPrice.get(); }
    public CheckBox getCHECKBOX() { return CHECKBOX; }

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
