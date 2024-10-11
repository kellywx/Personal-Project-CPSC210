package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFriend {
    private Friend testFriend;

    @BeforeEach
    void runBefore() {
        testFriend = new Friend("Amy");
    }

    @Test
    void testConstructor() {
        assertEquals("Amy", testFriend.getName());
        assertTrue(testFriend.getToBuyList().getWishList().isEmpty());
    }

    @Test
    void testToString() {
        assertEquals("Name: Amy", testFriend.toString());
    }
}
