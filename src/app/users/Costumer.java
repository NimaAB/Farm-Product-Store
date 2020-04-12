package app.users;

public class Costumer  {
    private String userName;
    private String password;


    public Costumer(String userName,String passwordt){
       this.userName = userName;
       this.password = password;
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

}
