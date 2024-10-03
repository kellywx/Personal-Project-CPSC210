package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestUser {
    private User testUser;

    @BeforeEach
    void runBefore() {
        testUser = new User(1, "Jane Smith", "janesmith@gmail.com", "fakepassword");
    }

    @Test
    void testConstructor() {
        assertEquals(1, testUser.getID());
        assertEquals("Jane Smith", testUser.getUsername());
        assertEquals("janesmith@gmail.com", testUser.getEmail());
        assertEquals("fakepassword", testUser.getPassword());
    }


}