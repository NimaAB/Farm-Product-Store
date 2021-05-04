package org.app.data.models;

import org.app.validation.customExceptions.InvalidTextInputException;

import java.io.Serializable;
import java.util.ArrayList;

public class Category implements Serializable {
    private static final long serialVersionUID = 1;
    private String name;
    private ArrayList<String> subCategories = new ArrayList<>();

    public Category(String name){
        this.name = name;
    }

    public void setSubCategories(ArrayList<String> subcategories){
        subCategories.addAll(subcategories);
    }

    public ArrayList<String> getSubCategories() {
        return subCategories;
    }

    public String getName() {
        return name;
    }

    public void addSubCategory(String newSubCategory) throws InvalidTextInputException{
        if(subCategories.contains(newSubCategory)){
            throw new InvalidTextInputException("En sub-kategori med navnet: " + newSubCategory + " allerede finnes!");
        }
        this.subCategories.add(newSubCategory);
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' + "\n"+
                ", subCategories=" + subCategories +
                '}';
    }
}
