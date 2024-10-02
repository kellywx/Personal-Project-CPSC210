package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

// Represents a wishlist which contains ArrayList of Wish objects
public class WishList {
    private ArrayList<Wish> wishList;

    // EFFECTS: constructs a new wishlist
    public WishList() {
        wishList = new ArrayList<>();
    }

    public void addWish(String name, String brand, BigDecimal price) {
        // stub
    }

    public void deleteWish(String name, String brand, BigDecimal price) {
        // stub
    }

}
