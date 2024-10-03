package model;

// Represents a new user
public class User {
    private int id;
    private String username;
    private String email;
    private String password;

    // EFFECTS: constructs a user with given id, username, email,
    // and password
    public User(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getID() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    
}
