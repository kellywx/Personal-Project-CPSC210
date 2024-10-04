package model;

import java.util.HashMap;
import java.util.Map;

public class UserOperation {

    // Using Map to store user data in pairs
    private Map<String, User> users = new HashMap<>();

    // MODIFIES: this
    // EFFECTS: Registers a new user 
    public User registerUser(String username, String email, String password) {
        if (users.containsKey(username)) {
            return null;
        }

        User user = new User(users.size()+1, username, email, password);
        users.put(username, user);
        return user;
    }

    // MODIFIES: this
    // EFFECTS: Checks user's username and password, then authenticate
    public User loginUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        // Failed to login
        return null;
    }

    // MODIFIES: this
    // EFFECTS: Searches for a user using username
    public User findUserByUsername(String username) {
        return users.get(username);
    }
}


