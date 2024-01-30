package OOP;


public class User {
    private String username;
    private String password;

    //Constructor
    public User(String username,String password){
        this.username = username;
        this.password = password;
    }
    //Getter setter for username

    public String getUsername() {

        return username;
    }

    public void setUsername(String username) {

        this.username = username;
    }

    //Getter setter for pw

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }
}
