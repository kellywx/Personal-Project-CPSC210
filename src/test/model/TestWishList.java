package model;

import static org.junit.Assert.assertEquals;

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
        assertEquals(0, testWishList.getWishList().size());
    }

    @Test
    void testAddWish() {
        testWishList.addWish("Lipstick", "Rare", 30);
        testWishList.addWish("T-shirt", "Lululemon", 20);
        assertEquals(2, testWishList.getWishList().size());
    }

    @Test
    void testDeleteWish() {
        testWishList.addWish("Lipstick", "Rare", 30);
        testWishList.addWish("T-shirt", "Lululemon", 20);
        testWishList.deleteWish("Lipstick", "Rare", 20);
        assertEquals(1, testWishList.getWishList().size());
    }

}
