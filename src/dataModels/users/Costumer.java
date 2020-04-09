package dataModels.users;

import dataModels.Items;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Costumer implements Serializable {
    private String userName;
    private String password;
    private ArrayList<Items> configurationList;

    public Costumer(String userName,String password,ArrayList<Items> configurationList){
       this.userName = userName;
       this.password = password;
       this.configurationList = configurationList;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return password;
    }

    public void setConfigurationList(ArrayList<Items> configurationList){
        this.configurationList=configurationList;
    }
    public ArrayList<Items> getConfigurationList() {
        return configurationList;
    }
}
