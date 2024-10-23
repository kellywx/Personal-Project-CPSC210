package persistence;

import org.junit.jupiter.api.Test;

import model.Friend;
import model.FriendList;
import model.Wallet;
import model.Wish;
import model.WishList;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyEverything() {
        try {
            WishList wishList = new WishList();
            FriendList friendList = new FriendList();
            Wallet wallet = new Wallet();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWishList.json");
            writer.open();
            writer.write(wishList,wallet,friendList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWishList.json");
            wishList = reader.readWishList();
            friendList = reader.readFriendList();
            wallet = reader.readWallet();
            assertEquals(0, wishList.getWishList().size());
            assertEquals(0, friendList.getFriendList().size());
            assertEquals(0, wallet.getMoney());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWishList() {
        try {
            WishList wishList = new WishList();
            wishList.addWish("Shoes", "Nike", 90);
            wishList.addWish("Laptop", "Apple", 2500);
            wishList.checkWish("Shoes", "Nike");
            Wallet wallet = new Wallet();
            wallet.addMoney(100);
            FriendList friendList = new FriendList();
            friendList.addFriend("Rachel");
            friendList.addFriend("Monica");
            Friend rachel = friendList.getFriend("Rachel");
            rachel.getToBuyList().addWish("Makeup", "Sephora", 50);
            Friend monica = friendList.getFriend("Monica");
            monica.getToBuyList().addWish("Shirt", "Ralph Lauren", 30);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWishList.json");
            writer.open();
            writer.write(wishList, wallet, friendList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWishList.json");
            wishList = reader.readWishList();
            friendList = reader.readFriendList();
            wallet = reader.readWallet();
            ArrayList<Wish> wishes = wishList.getWishList();
            assertEquals(2, wishes.size());
            checkWish("Shoes", "Nike", 90, "Yes",wishes.get(0));
            checkWish("Laptop", "Apple", 2500, "No",wishes.get(1));
            ArrayList<Friend> friends = friendList.getFriendList();
            assertEquals(2, friends.size());
            ArrayList<Wish> rachelWishes = friends.get(0).getToBuyList().getWishList();
            assertEquals(1, rachelWishes.size());
            checkWish("Makeup", "Sephora", 50, "No",rachelWishes.get(0));
            ArrayList<Wish> monicaWishes = friends.get(1).getToBuyList().getWishList();
            assertEquals(1, monicaWishes.size());
            monicaWishes.get(0).markChecked();
            checkWish("Shirt", "Ralph Lauren", 30, "Yes",monicaWishes.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
