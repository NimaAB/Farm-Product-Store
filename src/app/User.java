package app;

public class User {
    private static boolean isAdmin;
    private String userName;
    private String password;

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() { return userName; }
    public String getPassword() { return password;}

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public static boolean isAdmin(){
        return isAdmin;
    }

}
