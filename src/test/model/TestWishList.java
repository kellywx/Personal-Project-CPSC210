package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWishList {
    private WishList testWishList;

    @BeforeEach
    void runBefore() {
        testWishList = new WishList();
    }

    @Test
    void testConstructor() {
        assertTrue(testWishList.getWishList().isEmpty());
    }

    @Test
    void testAddWish() {
        testWishList.addWish("Makeup", "Sephora", 30);
        testWishList.addWish("Shirt", "Lululemon", 20);
        
        // Testing if wishlist has 2 items, not empty
        assertFalse(testWishList.getWishList().isEmpty());
        assertEquals(2, testWishList.getWishList().size());

        // Testing if the first item and second item have been added
        assertEquals("Makeup", testWishList.getWishList().get(0).getName());
        assertEquals(20, testWishList.getWishList().get(1).getPrice());
    }

    @Test
    void testDeleteWish() {
        testWishList.addWish("Shirt", "Lululemon", 20);
        testWishList.addWish("Makeup", "Sephora", 30);

        // Testing if both name and brand match the item
        testWishList.deleteWish("Makeup", "Sephora");
        assertEquals(1, testWishList.getWishList().size());

        // Testing if name matches, but brand does not match
        testWishList.deleteWish("Shirt", "Nike");
        assertEquals(1, testWishList.getWishList().size());

        // Testing if brand matches, but name does not match
        testWishList.deleteWish("Shoes", "Lululemon");
        assertEquals(1, testWishList.getWishList().size());

        // Testing if both name and brand do not match
        testWishList.deleteWish("Laptop", "Apple");
        assertEquals(1, testWishList.getWishList().size());
    }

    @Test
    void testFulfillWish() {
        testWishList.addWish("Shirt", "Lululemon", 20);
        testWishList.addWish("Lipstick", "Sephora", 30);
        testWishList.checkWish("Shirt", "Nike");
        assertFalse(testWishList.getWishList().get(0).isChecked());
        testWishList.checkWish("Lipstick", "Lululemon");
        assertFalse(testWishList.getWishList().get(1).isChecked());
        testWishList.checkWish("Lipstick", "Sephora");
        assertTrue(testWishList.getWishList().get(1).isChecked());
    }

    @Test
    void testFindWish() {
        testWishList.addWish("Shirt", "Lululemon", 20);
        testWishList.addWish("Lipstick", "Sephora", 30);
        Wish selectedWish = testWishList.findWish("Lipstick", "Sephora");
        assertEquals("Lipstick", selectedWish.getName());
        assertEquals("Sephora", selectedWish.getBrand());
        assertEquals(30, selectedWish.getPrice());

        assertNull(testWishList.findWish("Lipstick", "FakeSephora"));
        assertNull(testWishList.findWish("FakeLipstick", "Sephora"));
        assertNull(testWishList.findWish("FakeLipstick", "FakeSephora"));
    }

     @Test
    public void testToJson() {
        testWishList.addWish("Laptop", "BrandX", 999.99);
        testWishList.addWish("Phone", "BrandY", 499.99);

        JSONObject json = testWishList.toJson();
        assertTrue(json.has("wishList"));
        JSONArray wishArray = json.getJSONArray("wishList");

        assertEquals(2, wishArray.length());

        // Assert that the first Wish object is correct
        JSONObject firstWish = wishArray.getJSONObject(0);
        assertEquals("Laptop", firstWish.get("name"));
        assertEquals("BrandX", firstWish.get("brand"));
        assertEquals(999.99, firstWish.get("price"));
        assertEquals("No", firstWish.get("checked off?"));

        // Assert that the second Wish object is correct
        JSONObject secondWish = wishArray.getJSONObject(1);
        assertEquals("Phone", secondWish.get("name"));
        assertEquals("BrandY", secondWish.get("brand"));
        assertEquals(499.99, secondWish.get("price"));
        assertEquals("No", secondWish.get("checked off?"));
    }

    @Test
    public void testWishesToJson() {
        testWishList.addWish("Laptop", "BrandX", 999.99);
        testWishList.addWish("Phone", "BrandY", 499.99);

        // Act: get the JSONArray representation of the wishes
        JSONArray jsonArray = testWishList.wishesToJson();

        // Assert: check that the number of elements in the JSONArray is correct
        assertEquals(2, jsonArray.length());

        // Assert that the first Wish object is correct
        JSONObject firstWish = jsonArray.getJSONObject(0);
        assertEquals("Laptop", firstWish.get("name"));
        assertEquals("BrandX", firstWish.get("brand"));
        assertEquals(999.99, firstWish.get("price"));
        assertEquals("No", firstWish.get("checked off?"));

        // Assert that the second Wish object is correct
        JSONObject secondWish = jsonArray.getJSONObject(1);
        assertEquals("Phone", secondWish.get("name"));
        assertEquals("BrandY", secondWish.get("brand"));
        assertEquals(499.99, secondWish.get("price"));
        assertEquals("No", secondWish.get("checked off?"));
    }

}
