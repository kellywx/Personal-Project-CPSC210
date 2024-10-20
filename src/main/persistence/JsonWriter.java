package persistence;
import org.json.JSONObject;

import model.WishList;

import java.io.*;

// Represents a writer that writes JSON representation of wishlist to file
public class JsonWriter {

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of wishlist to file
    public void write(WishList wishList) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        // stub
    }
}

