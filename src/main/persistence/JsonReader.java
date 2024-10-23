package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

import model.Friend;
import model.FriendList;
import model.Wallet;
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
    public WishList readWishList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWishList(jsonObject);
    }

    // EFFECTS: reads friendlist from file and returns it;
    // throws IOException if an error occurs reading data from file
    public FriendList readFriendList() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseFriendList(jsonObject);
    }

    // EFFECTS: reads wallet from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Wallet readWallet() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseWallet(jsonObject);
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
        WishList wishList = new WishList();
        addWishes(wishList, jsonObject);
        return wishList;
    }

     // EFFECTS: parses wishlist from JSON object and returns it
     private FriendList parseFriendList(JSONObject jsonObject) {
        FriendList friendList = new FriendList();
        addFriends(friendList, jsonObject);
        return friendList;
    }


    // EFFECTS: parses wallet from JSON object and returns it
    private Wallet parseWallet(JSONObject jsonObject) {
        Wallet wallet = new Wallet();
        addMoney(wallet, jsonObject);
        return wallet;
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
        String checkedOff = jsonObject.getString("checked off?");
        // PROBLEM!!!
        wishList.addWish(name, brand, price);
        Wish selectedWish = wishList.findWish(name, brand);
        if (checkedOff.equals("Yes")) {
            selectedWish.markChecked();
        }
    }

    // MODIFIES: FriendList
    // EFFECTS: parses friendList from JSON object and adds them to friendlist
    private void addFriends(FriendList friendList, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("friendList");
        for (Object json : jsonArray) {
            JSONObject nextFriend = (JSONObject) json;
            addFriend(friendList, nextFriend);
        }
    }

    // MODIFIES: FriendList
    // EFFECTS: parses friendList from JSON object and adds it to friendlist
    private void addFriend(FriendList friendList, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        friendList.addFriend(name);
        Friend friend = friendList.getFriend(name);
        WishList wishList = friend.getToBuyList();
        addToBuyList(jsonObject, wishList);
    }

    private void addToBuyList(JSONObject jsonObject, WishList wishList) {
        JSONArray jsonArray = jsonObject.getJSONArray("to-buy List");
        for (Object json : jsonArray) {
            JSONObject dummy = (JSONObject) json;
            String name = dummy.getString("name");
            int price = dummy.getInt("price");
            String brand = dummy.getString("brand");
            wishList.addWish(name, brand, price);       }
    }


    // MODIFIES: Wallet
    // EFFECTS: parses friendList from JSON object and adds it to friendlist
    private void addMoney(Wallet wallet, JSONObject jsonObject) {
        int money = jsonObject.getInt("wallet");
        wallet.addMoney(money);
    }
}
