package persistence;

import org.junit.jupiter.api.Test;

import model.Wish;
import model.WishList;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            WishList wishList = new WishList("My Wishlist");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWishList() {
        try {
            WishList wishList = new WishList("My Wishlist");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWishList.json");
            writer.open();
            writer.writeWishList(wishList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWishList.json");
            wishList = reader.read();
            assertEquals("My Wishlist", wishList.getName());
            assertEquals(0, wishList.getWishList().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWishList() {
        try {
            WishList wishList = new WishList("My Wishlist");
            wishList.addWish("Shoes", "Nike", 90);
            wishList.addWish("Laptop", "Apple", 2500);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWishList.json");
            writer.open();
            writer.writeWishList(wishList);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWishList.json");
            wishList = reader.read();
            assertEquals("My Wishlist", wishList.getName());
            ArrayList<Wish> wishes = wishList.getWishList();
            assertEquals(2, wishes.size());
            assertEquals("Shoes", wishes.get(0).getName());
            assertEquals("Apple", wishes.get(1).getBrand());
            assertEquals(2500, wishes.get(1).getPrice());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
