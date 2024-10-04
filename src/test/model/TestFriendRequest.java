package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestFriendRequest {

    private User sender;
    private User receiver;
    private FriendRequest friendRequest;

    @BeforeEach
    void runBefore() {
        sender = new User(1, "jane_smith", "janesmith@gmail.com", "fakepassword1");
        receiver = new User(2, "bill_nye", "billnye@gmail.com", "fakepassword2");
        friendRequest = new FriendRequest(sender, receiver);
    }

    @Test
    void testFriendRequestCreation() {
        assertEquals(sender, friendRequest.getSender());
        assertEquals(receiver, friendRequest.getReceiver());
        assertEquals("PENDING", friendRequest.getStatus());
    }

    @Test
    void testAcceptFriendRequest() {
        friendRequest.setStatus("ACCEPTED");
        assertEquals("ACCEPTED", friendRequest.getStatus());
    }

    @Test
    void testDenyFriendRequest() {
        friendRequest.setStatus("DENIED");
        assertEquals("DENIED", friendRequest.getStatus());
    }
}

