package org.app.data.models;

import java.util.ArrayList;

public class Category {
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

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' + "\n"+
                ", subCategories=" + subCategories +
                '}';
    }

}
