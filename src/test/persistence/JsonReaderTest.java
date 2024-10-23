package persistence;

import org.junit.jupiter.api.Test;

import model.FriendList;
import model.Friend;
import model.Wish;
import model.WishList;
import model.Wallet;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WishList wishList = reader.readWishList();
            FriendList friendList = reader.readFriendList();
            Wallet wallet = reader.readWallet();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWishList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWishList.json");
        try {
            WishList wishList = reader.readWishList();
            FriendList friendList = reader.readFriendList();
            Wallet wallet = reader.readWallet();
            assertEquals(0, wishList.getWishList().size());
            assertEquals(0, friendList.getFriendList().size());
            assertEquals(0, wallet.getMoney());

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWishList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWishList.json");
        try {
            WishList wishList = reader.readWishList();
            ArrayList<Wish> wishes = wishList.getWishList();
            assertEquals(2, wishes.size());
            checkWish("Shoes", "Nike", 90, "No", wishes.get(0));
            checkWish("Laptop", "Apple", 2500, "Yes", wishes.get(1));
            FriendList friendList = reader.readFriendList();
            ArrayList<Friend> friends = friendList.getFriendList();
            assertEquals(1, friends.size());
            ArrayList<Wish> monicaWishes = friends.get(0).getToBuyList().getWishList();
            assertEquals(1, monicaWishes.size());
            checkWish("Shirt", "Ralph Lauren", 30, "No",monicaWishes.get(0));

        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
