package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendOperation {
    private List<FriendRequest> friendRequests = new ArrayList<>();

    // Store each user's list of friends
    private Map<User, List<User>> friends = new HashMap<>();

    // MODIFIES: this
    // EFFECTS: constructs a new friend request from sender to receiver
    public void sendFriendRequest(User sender, User receiver) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: accepts friend request by setting status to "ACCEPTED"
    public void acceptFriendRequest(FriendRequest request) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: deletes friend request by setting status to "DENIED"
    public void deleteFriendRequest(FriendRequest request) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: returns a list of friend requests with status "PENDING"
    public List<FriendRequest> getPending(User user) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: returns list of friends
    public List<User> getFriends(User user) {
        // stub
    }
}
