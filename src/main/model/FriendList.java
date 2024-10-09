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
        friendList.add(new Friend(name));
    }

    // MODIFIES: this
    // EFFECTS: removes a friend from friend list
    public void deleteFriend(String name) {
        Friend friendToRemove = null;
        for (Friend friend : friendList) {
            if (friend.getName().equals(name)){
                friendToRemove = friend;
                break;
            }
        }
        if (friendToRemove != null) {
            friendList.remove(friendToRemove);
        } 
    }

    public List<Friend> getFriendList() {
        return friendList;
    }

}
