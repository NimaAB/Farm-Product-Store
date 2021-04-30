package org.app.data.models;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import org.app.validation.Validator;
import org.app.validation.customExceptions.EmptyFieldException;
import org.app.validation.customExceptions.InvalidTextInputException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Product implements Serializable {
    private static final long serialVersionUID = 1;
    private transient SimpleIntegerProperty productID;
    private transient SimpleStringProperty productName;
    private transient SimpleStringProperty category;
    private transient SimpleStringProperty subcategory;
    private transient SimpleStringProperty specification;
    private transient SimpleDoubleProperty price;

    private static int id = 0;

    public Product(String productName, String category, String subCategory, String specs, double price) {
        this.productID = new SimpleIntegerProperty(id + 1);
        id = this.productID.getValue();
        this.productName = new SimpleStringProperty(productName);
        this.category = new SimpleStringProperty(category);
        this.subcategory = new SimpleStringProperty(subCategory);
        this.specification = new SimpleStringProperty(specs);
        this.price = new SimpleDoubleProperty(price);
    }

    public int getProductID() {
        return this.productID.getValue();
    }

    public void setProductID() {
        this.productID = new SimpleIntegerProperty(id + 1);
        id = this.productID.getValue();
    }

    public void setProductID(Integer id) {

        this.productID = new SimpleIntegerProperty(id);
        Product.id = id;
    }

    public String getProductName() {
        return this.productName.getValue();
    }

    public void setProductName(String newName) throws InvalidTextInputException {
        String name = Validator.validateName(newName);
        this.productName = new SimpleStringProperty(name);
    }

    public String getCategory() {
        return this.category.getValue();
    }

    public void setCategory(String newCategory) throws InvalidTextInputException, EmptyFieldException{
        String category = Validator.validateCategory(newCategory);
        this.category = new SimpleStringProperty(category);
    }

    public String getSubCategory() {
        return this.subcategory.getValue();
    }

    public void setSubCategory(String newSubCategory) throws InvalidTextInputException, EmptyFieldException {
        String subCategory = Validator.validateCategory(newSubCategory);
        this.subcategory = new SimpleStringProperty(subCategory);
    }

    public String getSpecification() {
        return this.specification.getValue();
    }

    public void setSpecification(String newSpecs) throws InvalidTextInputException {
        String specs = Validator.validateSpecs(newSpecs);
        this.specification = new SimpleStringProperty(specs);
    }

    public double getPrice() {
        return this.price.getValue();
    }

    public void setPrice(Double price) {
        this.price = new SimpleDoubleProperty(price);
    }

    public String toString() {
        return "{" + "\n" +
                "\t\"productID\" :" + "\"" + getProductID() + "\"," + "\n" +
                "\t\"productName\" :" + "\"" + getProductName() + "\"," + "\n" +
                "\t\"category\" :" + "\"" + getCategory() + "\"," + "\n" +
                "\t\"subcategory\" :" + "\"" + getSubCategory() + "\"," + "\n" +
                "\t\"specification\" :" + "\"" + getSpecification() + "\"," + "\n" +
                "\t\"price\" :" + "\"" + getPrice() + "\"" + "\n" +
                "}";
    }

    public String csvFormat(char delimiter) {
        String format = "%s" + delimiter + "%s" + delimiter + "%s" + delimiter + "%s" + delimiter + "%s" + delimiter + "%s";
        return String.format(format, getProductID(), getProductName(),
                getCategory(), getSubCategory(), getSpecification(), getPrice());
    }

    private void writeObject(ObjectOutputStream ost) throws IOException {
        ost.defaultWriteObject();
        ost.writeInt(getProductID());
        ost.writeUTF(getProductName());
        ost.writeUTF(getCategory());
        ost.writeUTF(getSubCategory());
        ost.writeUTF(getSpecification());
        ost.writeDouble(getPrice());
    }

    private void readObject(ObjectInputStream ist) throws IOException, ClassNotFoundException {

        int productID = ist.readInt();
        String name = ist.readUTF();
        String category = ist.readUTF();
        String subcategory = ist.readUTF();
        String specs = ist.readUTF();
        double price = ist.readDouble();

        this.setProductID(productID);
        this.setProductName(name);
        this.setCategory(category);
        this.setSubCategory(subcategory);
        this.setSpecification(specs);
        this.setPrice(price);
    }
}
