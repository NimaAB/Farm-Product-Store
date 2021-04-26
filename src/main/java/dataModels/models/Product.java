package dataModels.models;


import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import validations.customExceptions.InvalidArgument;
import validations.customExceptions.InvalidNumberException;
import validations.customExceptions.InvalidTextInputException;
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

    public Product() {
    }

    public int getProductID() {
        return this.productID.getValue();
    }

    public void setProductID() {
        this.productID = new SimpleIntegerProperty(id + 1);
        id = this.productID.getValue();
    }

    public void setProductID(int id) {
        this.productID = new SimpleIntegerProperty(id);
        Product.id = id;
    }

    public String getProductName() {
        return this.productName.getValue();
    }

    public void setProductName(String name) throws InvalidTextInputException {

        if (name.isEmpty() || name.isBlank() || Character.isDigit(name.charAt(0)) ) {
            throw new InvalidTextInputException("Feil: Produkt navn er tom eller starter med et nummer");
        }
        this.productName = new SimpleStringProperty(name);
    }

    public String getCategory() {
        return this.category.getValue();
    }

    public void setCategory(String category) throws InvalidArgument  {
        if (category.isEmpty() || category.isBlank()) {
            throw new InvalidArgument("Feil: Velg kategori");

        }
        this.category = new SimpleStringProperty(category);
    }

    public String getSubCategory() {
        return this.subcategory.getValue();
    }

    public void setSubCategory(String subCategory) throws InvalidTextInputException  {
        //validation call
        if (subCategory.isEmpty() || subCategory.isBlank()) {
            throw new InvalidTextInputException("Feil: Velg under kategori!");

        }
        this.subcategory = new SimpleStringProperty(subCategory);
    }

    public String getSpecification() {
        return this.specification.getValue();
    }

    public void setSpecification(String specs) throws InvalidTextInputException  {
        //validation call
        if(specs.isEmpty() || specs.isBlank()) throw new InvalidTextInputException("Feil: Produkt beskrivelse mangler");

        this.specification = new SimpleStringProperty(specs);
    }

    public double getPrice() {
        return this.price.getValue();
    }

    public void setPrice(Double price) throws InvalidArgument{
        //validation call
        if (price <= 0){
            throw new InvalidNumberException("Ugyldig pris!");

        }
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
        try {
            int productID = ist.readInt();
            String name = ist.readUTF();
            String category = ist.readUTF();
            String subcategory = ist.readUTF();
            String specs = ist.readUTF();
            double price = ist.readDouble();

            setProductID(productID);
            setProductName(name);
            setCategory(category);
            setSubCategory(subcategory);
            setSpecification(specs);
            setPrice(price);
        } catch (InvalidTextInputException | InvalidArgument e) {
            e.printStackTrace();
        }
    }
}
