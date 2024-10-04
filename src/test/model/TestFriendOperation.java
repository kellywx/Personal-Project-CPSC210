package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class TestFriendOperation {

    private FriendOperation friend;
    private User sender;
    private User receiver;

    @BeforeEach
    void runBefore() {
        friend = new FriendOperation();
        sender = new User(1, "jane_smith", "janesmith@gmail.com", "fakepassword");
        receiver = new User(2, "bill_nye", "billnye@gmail.com", "fakepassword1");
    }

    @Test
    void testSendFriendRequest() {
        friend.sendFriendRequest(sender, receiver);
        List<FriendRequest> pendingRequests = friend.getPending(receiver);
        assertEquals(1, pendingRequests.size());

        // First sender should be jane_smith
        assertEquals(sender, pendingRequests.get(0).getSender());
    }

    @Test
    void testAcceptFriendRequest() {
        friend.sendFriendRequest(sender, receiver);
        List<FriendRequest> pendingRequests = friend.getPending(receiver);
        FriendRequest firstRequest = pendingRequests.get(0);
        friend.acceptFriendRequest(firstRequest);
        assertEquals("ACCEPTED", firstRequest.getStatus());
        List<User> friends = friend.getFriends(receiver);

        // Receiver should now have one friend
        assertEquals(1, friends.size());

        // Sender should now be in the receiver's friend list
        assertEquals(sender, friends.get(0));
    }

    @Test
    void testDenyFriendRequest() {
        friend.sendFriendRequest(sender, receiver);
        List<FriendRequest> pendingRequests = friend.getPending(receiver);
        FriendRequest firstRequest = pendingRequests.get(0);
        friend.denyFriendRequest(firstRequest);
        
        assertEquals("DENIED", firstRequest.getStatus());
        List<User> friends = friend.getFriends(receiver);

        // Receiver should not have any friends
        assertEquals(0, friends.size());
    }

    @Test
    void testNoPendingRequests() {
        List<FriendRequest> pendingRequests = friend.getPending(receiver);
        assertTrue(pendingRequests.isEmpty());
    }
}

