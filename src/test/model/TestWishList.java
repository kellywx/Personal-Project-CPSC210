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
        assertFalse(testWishList.getWishList().isEmpty());
        assertEquals(2, testWishList.getWishList().size());
        assertEquals("Lipstick", testWishList.getWishList().get(0).getName());
        assertEquals(20, testWishList.getWishList().get(1).getPrice());
    }

    @Test
    void testDeleteWish() {
        testWishList.addWish("Lipstick", "Rare", 30);
        testWishList.deleteWish("Lipstick", "Rare");
        assertTrue(testWishList.getWishList().isEmpty());
    }

}
