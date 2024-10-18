package model;

// Represents a friend profile
public class Friend {
    private String name;
    private WishList toBuyList;

    // EFFECTS: constructs a friend with given name and empty to-buy list
    public Friend(String name) {
        this.name = name;
        toBuyList = new WishList("Kelly");
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
}
