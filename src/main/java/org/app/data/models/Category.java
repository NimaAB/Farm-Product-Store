package org.app.data.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

public class Category {

    private final String name;
    private final ObservableList<String> subCategories = FXCollections.observableArrayList();

    public Category(String name){
        this.name = name;
    }

    public void addAll(ArrayList<String> subcategories){
        subCategories.addAll(subcategories);
    }

    public String getName() {
        return name;
    }

    public ObservableList<String> getSubCategories() {
        return subCategories;
    }
}
