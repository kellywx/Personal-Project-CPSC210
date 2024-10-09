package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestFriendList {
    private FriendList testFriendList;

    @BeforeEach
    void runBefore() {
        testFriendList = new FriendList();
    }

    @Test
    void testConstructor() {
        assertTrue(testFriendList.getFriendList().isEmpty());
    }

    @Test
    void testAddFriend() {
        testFriendList.addFriend("Amy");
        testFriendList.addFriend("Rachel");
        
        // Testing if friend list has 2 friends, not empty
        assertFalse(testFriendList.getFriendList().isEmpty());
        assertEquals(2, testFriendList.getFriendList().size());

        // Testing if the first friend has been added correctly
        assertEquals("Amy", testFriendList.getFriendList().get(0).getName());
    }

    @Test
    void testDeleteFriend() {
        testFriendList.addFriend("Amy");
        testFriendList.addFriend("Rachel");
        testFriendList.deleteFriend("Rachel");

        // Testing if name matches the corresponding friend
        testFriendList.deleteFriend("Rachel");
        assertEquals(1, testFriendList.getFriendList().size());

        // Testing if name does not match any friends
        testFriendList.deleteFriend("Joey");
        assertEquals(2, testFriendList.getFriendList().size());
    }

}