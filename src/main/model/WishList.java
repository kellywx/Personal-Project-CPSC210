package model;

import java.util.ArrayList;
import java.util.Iterator;

// Represents a wishlist which contains ArrayList of Wish objects
public class WishList {
    private ArrayList<Wish> wishList;

    // EFFECTS: constructs a new wishlist
    public WishList() {
        wishList = new ArrayList<>();
    }

    public void addWish(String name, String brand, int price) {
        wishList.add(new Wish(name, brand, price));
    }

    public void deleteWish(String name, String brand) {
        Iterator<Wish> iterator = wishList.iterator();
        // Iterate through the wishlist
        while (iterator.hasNext()) {
            Wish wish = iterator.next();
            if (wish.getName().contains(name) && wish.getBrand().contains(brand)) {
                iterator.remove();
            }
        };
    }

    public ArrayList<Wish> getWishList() {
        return wishList;
    }
}
