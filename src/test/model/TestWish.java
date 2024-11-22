package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWish {
    private Wish testWish;

    @BeforeEach
    void runBefore() {
        testWish = new Wish("Laptop", "Apple", 2500.00);
    }

    @Test
    void testConstructor() {
        assertEquals("Laptop", testWish.getName());
        assertEquals("Apple", testWish.getBrand());
        assertEquals(2500.00, testWish.getPrice(),0.00001);
        assertFalse(testWish.isChecked());
        assertEquals("No", testWish.isCheckedOff());
    }

    @Test
    void testMarkChecked() {
        testWish.markChecked();
        assertTrue(testWish.isChecked());
        assertEquals("Yes", testWish.isCheckedOff());
    }

    @Test
    void testToString() {
        assertEquals("Item: Laptop   Brand: Apple   Price: $2500.0   Checked: No", testWish.toString());
        testWish.markChecked();
        assertEquals("Item: Laptop   Brand: Apple   Price: $2500.0   Checked: Yes", testWish.toString());
    }

    @Test
    public void testToJson() {
        // Arrange: create a Wish object
        Wish wish = new Wish("Laptop", "BrandX", 999.99);

        // Act: get the JSON representation
        JSONObject json = wish.toJson();

        // Assert: check that the JSON object contains the correct data
        assertEquals("Laptop", json.get("name"));
        assertEquals("BrandX", json.get("brand"));
        assertEquals(999.99, json.get("price"));
        assertEquals("No", json.get("checked off?"));  // By default, isChecked should be false, so "No"
    }

}
