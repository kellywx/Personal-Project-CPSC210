package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Represents a wishlist containing Wish objects
public class WishList implements Writable{
    private ArrayList<Wish> wishList;
    private String name;

    // EFFECTS: constructs an empty wishlist
    public WishList(String name) {
        wishList = new ArrayList<>();
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: adds a new Wish item to wishlist
    public void addWish(String name, String brand, int price) {
        wishList.add(new Wish(name, brand, price));
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
    }

    public ArrayList<Wish> getWishList() {
        return wishList;
    }

    public String getName() {
        return name;
    }

     @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("wishList", wishesToJson());
        return json;
    }

    // EFFECTS: returns wishes in this wishlist as a JSON array
    private JSONArray wishesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Wish wish : wishList) {
            jsonArray.put(wish.toJson());
        }

        return jsonArray;
    }
}
