package model;

import java.math.BigDecimal;

// Represents a wishlist item with name, brand, price,
// and whether or not it is fulfilled
public class Wish {
    private String name;
    private String brand;
    private BigDecimal price;
    private Boolean isFulfilled;

    // REQUIRES: price > 0
    // EFFECTS: constructs a wishlist item with given name, brand,
    // price, and not fulfilled
    public Wish(String name, String brand, BigDecimal price) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: marks the wish item as fulfilled
    public boolean markFulfilled() {
        return true;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isFulfilled() {
        return isFulfilled;
    }
}
