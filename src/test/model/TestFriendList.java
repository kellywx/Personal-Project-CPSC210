package model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONArray;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertNull;

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
        testFriendList.addFriend("Monica");
        testFriendList.addFriend("Rachel");
        
        // Testing if friend list has 2 friends, not empty
        assertFalse(testFriendList.getFriendList().isEmpty());
        assertEquals(2, testFriendList.getFriendList().size());

        // Testing if the first friend has been added correctly
        assertEquals("Monica", testFriendList.getFriendList().get(0).getName());
    }

    @Test
    void testDeleteFriend() {
        testFriendList.addFriend("Monica");
        testFriendList.addFriend("Rachel");
        testFriendList.deleteFriend("Rachel");

        // Testing if name matches the corresponding friend
        testFriendList.deleteFriend("Rachel");
        assertEquals(1, testFriendList.getFriendList().size());

        // Testing if name does not match any friends in friend list
        testFriendList.deleteFriend("Joey");
        assertEquals(1, testFriendList.getFriendList().size());
    }

    @Test
    void testGetFriend() {
        testFriendList.addFriend("Monica");
        testFriendList.addFriend("Rachel");

        assertEquals("Rachel",testFriendList.getFriend("Rachel").getName());
        assertEquals(0,testFriendList.getFriend("Rachel").getToBuyList().getWishList().size());

        assertNull(testFriendList.getFriend("Joey"));
    }

    @Test
    public void testToJson() {
        testFriendList.addFriend("Rachel");
        testFriendList.addFriend("Monica");
        JSONObject json = testFriendList.toJson();

        assertTrue(json.has("friendList"));
        JSONArray jsonArray = json.getJSONArray("friendList");
        assertEquals(2, jsonArray.length());
    }

    @Test
    public void testFriendsToJson() {
        testFriendList.addFriend("Phoebe");
        testFriendList.addFriend("Chandler");

        JSONArray jsonArray = testFriendList.friendsToJson();
        assertEquals(2, jsonArray.length());

        JSONObject firstFriend = jsonArray.getJSONObject(0);
        assertEquals("Phoebe", firstFriend.getString("name"));

        JSONObject secondFriend = jsonArray.getJSONObject(1);
        assertEquals("Chandler", secondFriend.getString("name"));
    }

}
