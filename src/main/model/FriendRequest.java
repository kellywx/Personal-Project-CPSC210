package model;

// Represents a friend request
public class FriendRequest {
    private User sender;
    private User receiver;
    private String status;

    // EFFECTS: Constructs a friend request from the given sender
    // to the given receiver, with a status of "PENDING"
    public FriendRequest(User sender, User receiver) {
        this.sender = sender;
        this.receiver = receiver;
        this.status = "PENDING";
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public String getStatus() {
        return status;
    }
}