package model;

import java.util.ArrayList;
import java.util.List;

// Represents a list containing Friend objects
public class FriendList {
    private List<Friend> friendList;

    // EFFECTS: constructs an empty friend list
    public FriendList() {
        friendList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new friend to friend list
    public void addFriend(String name) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: removes a friend from friend list
    public void deleteFriend(String name) {
        // stub
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

}
