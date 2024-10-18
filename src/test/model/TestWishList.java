package model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWishList {
    private WishList testWishList;

    @BeforeEach
    void runBefore() {
        testWishList = new WishList("Kelly");
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

}
