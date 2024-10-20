package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

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
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWishList(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses wishlist from JSON object and returns it
    private WishList parseWishList(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        WishList wishList = new WishList(name);
        addWishes(wishList, jsonObject);
        return wishList;
    }

    // MODIFIES: WishList
    // EFFECTS: parses wishList from JSON object and adds them to wishlist
    private void addWishes(WishList wishList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("wishList");
        for (Object json : jsonArray) {
            JSONObject nextWish = (JSONObject) json;
            addWish(wishList, nextWish);
        }
    }

    // MODIFIES: WishList
    // EFFECTS: parses wishList from JSON object and adds it to wishlist
    private void addWish(WishList wishList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String brand = jsonObject.getString("brand");
        int price = jsonObject.getInt("price");
        wishList.addWish(name, brand, price);
    }
}
