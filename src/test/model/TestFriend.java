package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFriend {
    private Friend testFriend;

    @BeforeEach
    void runBefore() {
        testFriend = new Friend("Ross");
    }

    @Test
    void testConstructor() {
        assertEquals("Ross", testFriend.getName());
        assertTrue(testFriend.getToBuyList().getWishList().isEmpty());
    }

    @Test
    void testToString() {
        assertEquals("Name: Ross", testFriend.toString());
    }

}
