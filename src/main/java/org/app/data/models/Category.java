package org.app.data.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {

    private String name;
    private ArrayList<String> subCategories = new ArrayList<>();

    public Category(String name){
        this.name = name;
    }

    public void addAll(ArrayList<String> subcategories){
        subCategories.addAll(subcategories);
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public ArrayList<String> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(ArrayList<String> list){
        this.subCategories = list;
    }

    public void addSubCategory(String newSubCategory){
        if(!subCategories.contains(newSubCategory)){
            this.subCategories.add(newSubCategory);
        }
        //throw a exception.
    }

    private void writeObject(ObjectOutputStream ost) throws IOException {
        ost.defaultWriteObject();
        ost.writeUTF(getName());
        ost.writeObject(getSubCategories());
    }

    private void readObject(ObjectInputStream ist) throws IOException, ClassNotFoundException {
        String name = ist.readUTF();
        ArrayList<String> subcategories = (ArrayList<String>) ist.readObject();

        this.setName(name);
        this.setSubCategories(subcategories);

    }
}
