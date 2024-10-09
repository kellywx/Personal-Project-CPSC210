package model;

import java.util.ArrayList;
import java.util.List;

// Represents a wishlist containing Wish objects
public class WishList {
    private List<Wish> wishList;

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

    public List<Wish> getWishList() {
        return wishList;
    }
}
