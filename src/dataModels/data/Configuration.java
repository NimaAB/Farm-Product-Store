package dataModels.data;

import java.util.ArrayList;

public class Configuration {
    private int nr;
    private String name;
    private double price;

    public Configuration(int nr,String name,double price){
        this.nr=nr;
        this.name=name;
        this.price=price;
    }

    private void setNr(int nr){
        this.nr=nr;
    }
    private int getNr(){
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

    public String toString(){
        return String.format("%s;%s;%s",getNr(),getName(),getPrice());
    }

    public double totalPrice(ArrayList<Components> configurationList){
        double totalPris = 0;
        for(Components item:configurationList){
            totalPris += item.getComponentPrice();
        }
        return totalPris;
    }
}
