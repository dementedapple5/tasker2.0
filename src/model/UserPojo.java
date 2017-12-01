
package model;

public class UserPojo {
    
    private String username;
    private String password;

    public UserPojo() {}

    public UserPojo(String username, String password) {
            super();
            this.username = username;
            this.password = password;
    }

    public String getPassword() {
            return password;
    }

    public String getUsername() {
            return username;
    }
}
