package dataModels.models;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Product implements Serializable {
    private static final long serialVersionUID = 1;
    private transient SimpleIntegerProperty productID;
    private transient SimpleStringProperty productName;
    private transient SimpleStringProperty category;
    private transient SimpleStringProperty specification;
    private transient SimpleDoubleProperty price;

    private static int id = 0;
    public Product (String productName,String category, String specs, double price){
        this.productID = new SimpleIntegerProperty(id + 1);
        id = this.productID.getValue();
        this.productName = new SimpleStringProperty(productName);
        this.category = new SimpleStringProperty(category);
        this.specification = new SimpleStringProperty(specs);
        this.price = new SimpleDoubleProperty(price);
    }

    public int getProductID(){
        return this.productID.getValue();
    }
    public void setProductID(int id){
        this.productID.setValue(id);
    }

    public String getProductName() {
        return this.productName.getValue();
    }
    public void setProductName(String name){
        //validation call
        this.productName.setValue(name);
    }

    public String getCategory() {
        return this.category.getValue();
    }
    public void setCategory(String category){
        //validation call
        this.category.setValue(category);
    }

    public String getSpecification() {
        return this.specification.getValue();
    }
    public void setSpecification(String specs){
        //validation call
        this.specification.setValue(specs);
    }

    public double getPrice() {
        return this.price.getValue();
    }
    public void setPrice(String price){
        //validation call will include parsing.
        this.price.setValue(Double.parseDouble(price));
    }

    private void writeObject(ObjectOutputStream ost) throws IOException {
        ost.defaultWriteObject();
        ost.writeInt(getProductID());
        ost.writeUTF(getProductName());
        ost.writeUTF(getCategory());
        ost.writeUTF(getSpecification());
        ost.writeDouble(getPrice());
    }

    private void readObject(ObjectInputStream ist) throws IOException,ClassNotFoundException{
        int productID = ist.readInt();
        String name = ist.readUTF();
        String category = ist.readUTF();
        String specs = ist.readUTF();
        String price = ist.readUTF();

        setProductID(productID);
        setProductName(name);
        setCategory(category);
        setSpecification(specs);
        setPrice(price);

    }

}
