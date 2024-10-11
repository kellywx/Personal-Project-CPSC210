package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWish {
    private Wish testWish;

    @BeforeEach
    void runBefore() {
        testWish = new Wish("Laptop", "Apple", 2500);
    }

    @Test
    void testConstructor() {
        assertEquals("Laptop", testWish.getName());
        assertEquals("Apple", testWish.getBrand());
        assertEquals(2500, testWish.getPrice());
        assertFalse(testWish.isFulfilled());
    }

    @Test
    void testMarkFulfilled() {
        testWish.markFulfilled();
        assertTrue(testWish.isFulfilled());
    }

    @Test
    void testToString() {
        assertEquals("Item: Laptop   Brand: Apple   Price: $2500   Fulfilled: No", testWish.toString());
        testWish.markFulfilled();
        assertEquals("Item: Laptop   Brand: Apple   Price: $2500   Fulfilled: Yes", testWish.toString());
    }

}
