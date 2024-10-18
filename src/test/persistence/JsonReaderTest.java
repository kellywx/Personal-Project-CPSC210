package persistence;

import org.junit.jupiter.api.Test;

import model.Wish;
import model.WishList;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            WishList wishList = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWishList() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWishList.json");
        try {
            WishList wishList = reader.read();
            assertEquals("My Wishlist", wishList.getName());
            assertEquals(0, wishList.getWishList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWishList() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWishList.json");
        try {
            WishList wishList = reader.read();
            assertEquals("My Wishlist", wishList.getName());
            ArrayList<Wish> wishes = wishList.getWishList();
            assertEquals(2, wishes.size());
            checkWish("Shoes", "Nike", 90, wishes.get(0));
            checkWish("Laptop", "Apple", 2500, wishes.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
