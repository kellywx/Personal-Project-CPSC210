package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a wishlist containing Wish objects
public class WishList implements Writable {
    private ArrayList<Wish> wishList;

    // EFFECTS: constructs an empty wishlist
    public WishList() {
        wishList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new Wish item to wishlist
    public void addWish(String name, String brand, double price) {
        Wish wish = new Wish(name, brand, price);
        wishList.add(wish);
        EventLog.getInstance().logEvent(new Event(
                "Added wish: " + wish.getName() + " from " + wish.getBrand() + " ($" + wish.getPrice() + ")"));
    }

    // MODIFIES: this
    // EFFECTS: removes a Wish item from wishlist
    public void deleteWish(String name, String brand) {
        Wish wishToRemove = null;
        for (Wish wish : wishList) {
            if (wish.getName().equals(name) && wish.getBrand().equals(brand)) {
                wishToRemove = wish;
                break;
            }
        }
        if (wishToRemove != null) {
            wishList.remove(wishToRemove);
        }

        EventLog.getInstance().logEvent(new Event("Deleted wish: " + wishToRemove.getName() + " from "
                + wishToRemove.getBrand() + " ($" + wishToRemove.getPrice() + ")"));
    }

    // MODIFIES: this
    // EFFECTS: finds Wish in wishlist and marks it as checked
    public void checkWish(String name, String brand) {
        Wish wishToCheck = null;
        for (Wish wish : wishList) {
            if (wish.getName().equals(name) && wish.getBrand().equals(brand)) {
                wishToCheck = wish;
                break;
            }
        }
        if (wishToCheck != null) {
            wishToCheck.markChecked();
        }

        EventLog.getInstance().logEvent(new Event("Checked off wish: " + wishToCheck.getName() + " from "
                + wishToCheck.getBrand() + " ($" + wishToCheck.getPrice() + ")"));
    }

    public Wish findWish(String name, String brand) {
        Wish selectedWish = null;
        for (Wish wish : getWishList()) {
            if (wish.getName().equals(name) && wish.getBrand().equals(brand)) {
                selectedWish = wish;
                break;
            }
        }
        return selectedWish;
    }

    public ArrayList<Wish> getWishList() {
        return wishList;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("wishList", wishesToJson());
        return json;
    }

    // EFFECTS: returns wishes in this wishlist as a JSON array
    public JSONArray wishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Wish wish : wishList) {
            jsonArray.put(wish.toJson());
        }

        return jsonArray;
    }
}
