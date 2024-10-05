package model;

import java.util.ArrayList;
import java.util.List;

public class FriendOperation {
    private List<FriendRequest> friendRequests = new ArrayList<>();

    // Store each user's list of friends
    private List<User> friends = new ArrayList<>();

    // MODIFIES: this
    // EFFECTS: constructs a new friend request from sender to receiver
    public void sendFriendRequest(User sender, User receiver) {
        FriendRequest request = new FriendRequest(sender, receiver);
        friendRequests.add(request);
    }


    // MODIFIES: this
    // EFFECTS: accepts friend request by setting status to "ACCEPTED"
    public void acceptFriendRequest(FriendRequest request) {
        request.setStatus("ACCEPTED");
        friends.add(request.getSender());
    }

    // MODIFIES: this
    // EFFECTS: denys friend request by setting status to "DENIED"
    public void denyFriendRequest(FriendRequest request) {
        request.setStatus("DENIED");
    }

    // MODIFIES: this
    // EFFECTS: returns a list of user's friend requests with status "PENDING"
    public List<FriendRequest> getPending(User user) {
        List<FriendRequest> pendingRequests = new ArrayList<>();
        for (FriendRequest request : friendRequests) {
            if (request.getReceiver().equals(user) && request.getStatus().equals("PENDING")) {
                pendingRequests.add(request);
            }
        }
        return pendingRequests;
    }

    // MODIFIES: this
    // EFFECTS: returns user's list of friends
    public List<User> getFriends(User user) {
        return friends;
    }
}
