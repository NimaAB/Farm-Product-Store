package dataModels.users;

import app.controllers.AdminController;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Adminstrator implements Serializable {
    private String userName;
    private String password;

    public Adminstrator(String uName, String password){
        this.userName = uName;
        this.password = password;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword(){
        return password;
    }
}
