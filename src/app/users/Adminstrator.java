package app.users;



public class Adminstrator  {
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

    //Adder Items til TabelView:

    //Sletter Items fra TableView:

    //Lagrer to bin:

    //lagrer to csv:
}
