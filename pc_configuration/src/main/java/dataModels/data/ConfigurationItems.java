package dataModels.data;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * En model av Typen ConfigurationItems
 * */
public class ConfigurationItems implements Serializable {
    private int nr;
    private String name;
    private double price;
    private static final long serialVersionUID = 1;

    public ConfigurationItems(int nr, String name, double price){
        this.nr=nr;
        this.name=name;
        this.price=price;
    }

    private void setNr(int nr){
        this.nr=nr;
    }
    public int getNr(){
        return nr;
    }

    private void setName(String name) {
        this.name = name;
    }
    private String getName() {
        return name;
    }

    private void setPrice(double price) {
        this.price = price;
    }
    private double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s",getNr(),getName(),getPrice());
    }
    public String toString(String delimiter){
        return String.format("%s"+delimiter+"%s"+delimiter+"%s",getNr(),getName(),getPrice());
    }

    public static double totalPrice(ObservableList<ConfigurationItems> configurationItemsList){
        double totalPris = 0;
        for(ConfigurationItems item: configurationItemsList){
            totalPris += item.getPrice();
        }
        return totalPris;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(getNr());
        s.writeUTF(getName());
        s.writeDouble(getPrice());
    }
    private void readObject(ObjectInputStream st) throws IOException,ClassNotFoundException{
        Integer nr = st.readInt();
        String name = st.readUTF();
        Double price = st.readDouble();

        setNr(nr);
        setName(name);
        setPrice(price);

        this.nr = nr;
        this.name = name;
        this.price = price;
    }

}
