package model;

import java.util.HashMap;
import java.util.Map;

public class UserOperation {

    // Using Map to store user data in pairs
    private Map<String, User> users = new HashMap<>();

    // MODIFIES: this
    // EFFECTS: Registers a new user 
    public User registerUser(String username, String email, String password) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Checks user's username and password, then authenticate
    public User loginUser(String username, String password) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: Searches for a user using username
    public User findUserByUsername(String username) {
        // stub
    }
}


