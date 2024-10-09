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
        testWishList = new WishList();
    }

    @Test
    void testConstructor() {
        assertTrue(testWishList.getWishList().isEmpty());
    }

    @Test
    void testAddWish() {
        testWishList.addWish("Lipstick", "Rare", 30);
        testWishList.addWish("T-shirt", "Lululemon", 20);
        
        // Testing if wishlist has 2 items, not empty
        assertFalse(testWishList.getWishList().isEmpty());
        assertEquals(2, testWishList.getWishList().size());

        // Testing if the first item and second item have been added
        assertEquals("Lipstick", testWishList.getWishList().get(0).getName());
        assertEquals(20, testWishList.getWishList().get(1).getPrice());
    }

    @Test
    void testDeleteWish() {
        testWishList.addWish("T-shirt", "Lululemon", 20);
        testWishList.addWish("Lipstick", "Rare", 30);

        // Testing if both name and brand match the item
        testWishList.deleteWish("Lipstick", "Rare");
        assertEquals(1, testWishList.getWishList().size());

        // Testing if name matches, but brand does not match
        testWishList.deleteWish("T-shirt", "Nike");
        assertEquals(1, testWishList.getWishList().size());

        // Testing if brand matches, but name does not match
        testWishList.deleteWish("Shoes", "Nike");
        assertEquals(1, testWishList.getWishList().size());

        // Testing if both name and brand do not match
        testWishList.deleteWish("Laptop", "Apple");
        assertEquals(1, testWishList.getWishList().size());
    }

}
