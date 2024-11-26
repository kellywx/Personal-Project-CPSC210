package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a list containing Friend objects
public class FriendList implements Writable {
    private ArrayList<Friend> friendList;

    // EFFECTS: constructs an empty friend list
    public FriendList() {
        friendList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new friend to friend list
    public void addFriend(String name) {
        friendList.add(new Friend(name));
        EventLog.getInstance().logEvent(new Event("Added New Friend: " + name));
    }

    // MODIFIES: this
    // EFFECTS: removes a friend from friend list
    public void deleteFriend(String name) {
        Friend friendToRemove = null;
        for (Friend friend : friendList) {
            if (friend.getName().equals(name)) {
                friendToRemove = friend;
                break;
            }
        }
        if (friendToRemove != null) {
            friendList.remove(friendToRemove);
            EventLog.getInstance().logEvent(new Event("Deleted Friend: " + name));
        }
    }

    public Friend getFriend(String name) {
        Friend selectedFriend = null;
        for (Friend friend : getFriendList()) {
            if (friend.getName().equals(name)) {
                selectedFriend = friend;
                break;
            }
        }
        if (selectedFriend != null) {
            EventLog.getInstance().logEvent(new Event("Selected Friend: " + name));
            return selectedFriend;
        } else {
            return null;
        }

    }

    public ArrayList<Friend> getFriendList() {
        return friendList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("friendList", friendsToJson());
        return json;
    }

    // EFFECTS: returns wishes in this wishlist as a JSON array
    public JSONArray friendsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Friend friend : friendList) {
            jsonArray.put(friend.toJson());
        }

        return jsonArray;
    }

}
