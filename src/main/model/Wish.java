package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents an item with name, brand, price, and whether or not it is checked
public class Wish implements Writable {
    private String name;
    private String brand;
    private int price;
    private Boolean isChecked;

    // REQUIRES: price > 0
    // EFFECTS: constructs a Wish with given name, brand,
    // price, and not checked
    public Wish(String name, String brand, int price) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        isChecked = false;
    }

    // MODIFIES: this
    // EFFECTS: marks Wish as checked
    public void markChecked() {
        isChecked = true;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public int getPrice() {
        return price;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public String isCheckedOff() {
        if (isChecked) {
            return "Yes";
        } else {
            return "No";
        }
    }

    // EFFECTS: returns a string representation of Wish
    public String toString() {
        String status = "No";
        if (isChecked == true) {
            status = "Yes";
        }
        return "Item: " + name + "   Brand: " + brand + "   Price: $" + price + "   Checked: " + status;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("brand", brand);
        json.put("price", price);
        json.put("checked off?", isCheckedOff());
        return json;
    }
}
