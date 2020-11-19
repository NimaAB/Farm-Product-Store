package org.app.dataModels.data;

import javafx.collections.ObservableList;

/**
 * En model av Typen ConfigurationItems
 * */
public class ConfigurationItems {
    private int nr;
    private String name;
    private double price;

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
}
