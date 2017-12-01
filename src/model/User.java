
package model;

public class User {
    private String username;
    private String password;
	
	
    public User() {
    }


    public User(String username, String password) {
        super();
        this.password = password;
        this.username = username;
    }



    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", password=" + password + '}';
    }
        
        
    
}
