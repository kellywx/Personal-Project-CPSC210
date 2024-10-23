package persistence;

import org.json.JSONObject;

import model.FriendList;
import model.Wallet;
import model.WishList;

import java.io.*;

// Referenced from the JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a writer that writes JSON representation of wishlist, friend list, and wallet to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file
    // cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of wishlist, friend list, and wallet to
    // file
    public void write(WishList wishList, Wallet wallet, FriendList friendList) {

        JSONObject combinedJson = new JSONObject();

        mergeJson(combinedJson, wishList.toJson());
        mergeJson(combinedJson, friendList.toJson());
        mergeJson(combinedJson, wallet.toJson());

        saveToFile(combinedJson.toString(TAB));
    }

    private void mergeJson(JSONObject target, JSONObject source) {
        for (String key : source.keySet()) {
            target.put(key, source.get(key));
        }
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
