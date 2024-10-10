package model;

import java.util.ArrayList;

// Represents a wishlist containing Wish objects
public class WishList {
    private ArrayList<Wish> wishList;

    // EFFECTS: constructs an empty wishlist
    public WishList() {
        wishList = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new item to wishlist
    public void addWish(String name, String brand, int price) {
        wishList.add(new Wish(name, brand, price));
    }

    // MODIFIES: this
    // EFFECTS: removes an item from wishlist
    public void deleteWish(String name, String brand) {
        Wish wishToRemove = null;
        for (Wish wish : wishList) {
            if (wish.getName().equals(name) && wish.getBrand().equals(brand)){
                wishToRemove = wish;
                break;
            }
        }
        if (wishToRemove != null) {
            wishList.remove(wishToRemove);
        } 
    }

    public void fulfillWish(String name, String brand) {
        Wish wishToFulfill = null;
        for (Wish wish : wishList) {
            if (wish.getName().equals(name) && wish.getBrand().equals(brand)){
                wishToFulfill = wish;
                break;
            }
        }
        if (wishToFulfill != null) {
            wishToFulfill.markFulfilled();
        } 
    }

    public ArrayList<Wish> getWishList() {
        return wishList;
    }
}
