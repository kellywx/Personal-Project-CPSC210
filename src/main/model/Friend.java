package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents a friend profile
public class Friend implements Writable {
    private String name;
    private WishList toBuyList;

    // EFFECTS: constructs a friend with given name and empty to-buy list
    public Friend(String name) {
        this.name = name;
        toBuyList = new WishList();
    }

    public String getName() {
        return name;
    }

    public WishList getToBuyList() {
        return toBuyList;
    }

    // EFFECTS: returns a string representation of friend
    public String toString() {
        return "Name: " + name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("to-buy List", toBuyList.wishesToJson());
        return json;
    }
}
