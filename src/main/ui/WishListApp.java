package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Wallet;
import model.Friend;
import model.FriendList;
import model.Wish;
import model.WishList;
import persistence.JsonReader;
import persistence.JsonWriter;

// Wishlist application
public class WishListApp {
    private WishList wishList;
    private FriendList friendList;
    private Wallet wallet;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/wishlist.json";


    // EFFECTS: constructs wishlist and runs the wishlist application
    public WishListApp() throws FileNotFoundException {
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
        wishList = new WishList("Kelly's Wishlist");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runWishList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWishList() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMainMenu();
            command = input.nextLine().toLowerCase();
            if (command.isEmpty() == false) {
                if (command.equals("5")) {
                    keepGoing = false;
                } else {
                    processCommand(command);
                }
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command from main menu
    private void processCommand(String command) {
        if (command.equals("1")) {
            addToWishList();
        } else if (command.equals("2")) {
            printWishList();
        } else if (command.equals("3")) {
            saveWishList();
        } else if (command.equals("4")) {
            loadWishList();
        } else {
            System.out.println("Selection not valid... Try again");
        }
    }

    // EFFECTS: displays main menu of options to user
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> add to wishlist");
        System.out.println("\t2 -> print wishlist");
        System.out.println("\t3 -> save wishlist to file");
        System.out.println("\t4 -> load wishlist from file");
        System.out.println("\t5 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: prompt user for name, brand, and price and adds to wishlist
    private void addToWishList() {
        System.out.println("Please enter name of item: ");
        String name = input.nextLine();
        System.out.println("Please enter brand of item: ");
        String brand = input.nextLine();
        System.out.println("Please enter price of item: ");
        String number = input.nextLine();
        int price = Integer.parseInt(number);
        wishList.addWish(name, brand, price);
    }

    // EFFECTS: prints all wishes in wishlist to the console
    private void printWishList() {
        ArrayList<Wish> wishes = wishList.getWishList();

        for (Wish wish : wishes) {
            System.out.println(wish);
        }
    }

    // EFFECTS: saves the wishlist to file
    private void saveWishList() {
        try {
            jsonWriter.open();
            jsonWriter.write(wishList);
            jsonWriter.close();
            System.out.println("Saved " + wishList.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadWishList() {
        try {
            wishList = jsonReader.read();
            System.out.println("Loaded " + wishList.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}