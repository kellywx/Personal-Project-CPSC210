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
        assertFalse(testWish.isChecked());
    }

    @Test
    void testMarkChecked() {
        testWish.markChecked();
        assertTrue(testWish.isChecked());
    }

    @Test
    void testToString() {
        assertEquals("Item: Laptop   Brand: Apple   Price: $2500   Checked: No", testWish.toString());
        testWish.markChecked();
        assertEquals("Item: Laptop   Brand: Apple   Price: $2500   Checked: Yes", testWish.toString());
    }

}
