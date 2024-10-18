package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import model.Wish;
import model.WishList;

// Represents a reader that reads wishlist from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads wishlist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public WishList read() throws IOException {
        return new WishList("Stub");
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        return "";
    }

    // EFFECTS: parses wishlist from JSON object and returns it
    private WishList parseWishList(JSONObject jsonObject) {
        return new WishList("Stub");
    }

    // MODIFIES: WishList
    // EFFECTS: parses wishes from JSON object and adds them to wishlist
    private void addWishes(WishList wishList, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: WishList
    // EFFECTS: parses wishes from JSON object and adds it to wishlist
    private void addWish(WishList wishList, JSONObject jsonObject) {
        // stub
    }
}
