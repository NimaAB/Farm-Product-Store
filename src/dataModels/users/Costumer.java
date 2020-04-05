package dataModels.users;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Costumer implements Serializable {
    private SimpleStringProperty userName;
    //private SimpleIntegerProperty amount;
    private SimpleStringProperty password;

    public Costumer(String userName,String password){
        this.userName = new SimpleStringProperty(userName);
        this.password = new SimpleStringProperty(password);
    }
    public void setUserName(String userName){
        this.userName.set(userName);
    }
    public String getUserName(){
        return userName.getValue();
    }
    public void setPassword(String password){
        this.password.set(password);
    }
    public String getPassword(){
        return password.getValue();
    }

    private void writeObject(ObjectOutputStream st) throws IOException{
        st.defaultWriteObject();
        st.writeUTF(getUserName());
    }
    private void readObject(ObjectInputStream st) throws IOException,ClassNotFoundException{
        String userName = st.readUTF();
        this.userName = new SimpleStringProperty(userName);
    }
}
