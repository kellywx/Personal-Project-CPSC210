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
        wishList = new WishList();
        friendList = new FriendList();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        wallet = new Wallet();
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
                if (command.equals("7")) {
                    keepGoing = false;
                } else {
                    processCommand(command);
                }
            }
        }

        System.out.println("\nGoodbye!");
    }

    // EFFECTS: displays main menu of options to user
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> view wishlist");
        System.out.println("\t2 -> view wallet");
        System.out.println("\t3 -> view friends");
        System.out.println("\t4 -> save to file");
        System.out.println("\t5 -> load from file");
        System.out.println("\t6 -> print");
        System.out.println("\t7 -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes user command from main menu
    private void processCommand(String command) {
        if (command.equals("1")) {
            viewWishList();
        } else if (command.equals("2")) {
            viewWallet();
        } else if (command.equals("3")) {
            viewFriends();
        } else if (command.equals("4")) {
            save();
        } else if (command.equals("5")) {
            load();
        } else if (command.equals("6")) {
            print();
        }else {
            System.out.println("Selection not valid... Try again");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays user's wishlist
    private void viewWishList() {
        System.out.println("Here is your wishlist:");
        displayList(wishList);
    }

    // MODIFIES: this
    // EFFECTS: displays items in given list and the mini menu
    private void displayList(WishList list) {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            for (Wish wish : list.getWishList()) {
                System.out.println(wish);
            }
            displayMiniMenu();
            command = input.nextLine().toLowerCase();

            if (command.equals("b") && list.equals(wishList)) {
                keepGoing = false;
            } else if (command.equals("b")) {
                viewFriends();
                keepGoing = false;
            } else {
                processNewCommand(list, command);
            }
        }
    }

    // EFFECTS: displays another menu of options to user
    private void displayMiniMenu() {
        System.out.println("\ta -> add new item");
        System.out.println("\td -> delete item");
        System.out.println("\tc -> check off item");
        System.out.println("\tb -> back");
    }

    // MODIFIES: this
    // EFFECTS: processes user input from mini menu
    private void processNewCommand(WishList wishList, String command) {
        if (command.equals("a")) {
            addToWishList(wishList);
        } else if (command.equals("d")) {
            if (wishList.getWishList().isEmpty() == false) {
                deleteFromWishList(wishList);
            } else {
                System.out.println("There are no items in this wishlist.");
            }
        } else if (command.equals("c")) {
            if (wishList.getWishList().isEmpty() == false) {
                checkOffWishList(wishList);
            } else {
                System.out.println("There are no items in this wishlist.");
            }
        } else {
            System.out.println("Selection not valid... Try again");
        }
    }

     // MODIFIES: this
    // EFFECTS: checks off selected Wish item
    private void checkOffWishList(WishList wishList) {
        System.out.println("Which item do you want to check off? Enter name:");
        String name = input.nextLine();
        String brand = "";
        Wish selectedWish = null;
        for (Wish wish : wishList.getWishList()) {
            if (wish.getName().equals(name)) {
                System.out.println("Enter brand of item");
                brand = input.nextLine();
                if (wish.getBrand().equals(brand)) {
                    selectedWish = wish;
                    break;
                }
            }
        }
        if (selectedWish == null) {
            System.out.println("Item not found.");
        } else if (selectedWish.isChecked() == true) {
            System.out.println("Item is already checked.");
        } else if (selectedWish != null) {
            processSelectedWish(wishList,selectedWish, name, brand);
        }
    }

    // MODIFIES: this
    // EFFECTS: checks selected Wish if user has enough money in wallet
    private void processSelectedWish(WishList wishList, Wish selectedWish, String name, String brand) {
        if (wallet.getMoney() - selectedWish.getPrice() < 0) {
            System.out.println("You don't have enough money!");
        } else {
            wishList.checkWish(name, brand);
            System.out.println("Successfully checked off!");
            wallet.spendMoney(selectedWish.getPrice());
            System.out.println("Here is your updated wallet: $" + wallet.getMoney());
        }
    }


    // MODIFIES: this
    // EFFECTS: add new Wish to wishlist
    private void addToWishList(WishList wishList) {
        System.out.println("Enter name of item");
        String name = input.nextLine();
        System.out.println("Enter brand of item");
        String brand = input.nextLine();
        System.out.println("Enter price of item");
        String number = input.nextLine();
        while (isInteger(number) == false) {
            System.out.println("Not valid. Try again");
            number = input.nextLine();
        }
        int price = Integer.parseInt(number);
        if (price < 0) {
            System.out.println("Price cannot be negative... Try again.");
        } else {
            wishList.addWish(name, brand, price);
            System.out.println("You have successfully added a new item!");
        }
    }

    // MODIFIES: this
    // EFFECTS: deletes Wish from wishlist
    private void deleteFromWishList(WishList wishList) {
        System.out.println("Enter name of item");
        String name = input.nextLine();

        Wish selectedWish = null;
        String brand = "";
        for (Wish wish : wishList.getWishList()) {
            if (wish.getName().equals(name)) {
                System.out.println("Enter brand of item");
                brand = input.nextLine();
                if (wish.getBrand().equals(brand)) {
                    selectedWish = wish;
                    break;
                }
            }
        }
        if (selectedWish == null) {
            System.out.println("Item not found.");
        } else {
            wishList.deleteWish(name, brand);
            System.out.println("Item has been successfully deleted!");
        }
    }

    // MODIFIES: this
    // EFFECTS: displays user's wallet and option to add money
    private void viewWallet() {
        System.out.println("Here is your current wallet: $" + wallet.getMoney());
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println("\nWould you like to add money?");
            System.out.println("\ty -> yes");
            System.out.println("\tn -> no");
            String command = input.nextLine();
            command = command.toLowerCase();
            if (command.equals("y")) {
                addMoney();
            } else {
                keepGoing = false;
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: adds money to user's wallet
    private void addMoney() {
        System.out.println("How much money would you like to add?");
        String number = input.nextLine();
        while (isInteger(number) == false) {
            System.out.println("Not valid. Try again");
            number = input.nextLine();
        }
        int money = Integer.parseInt(number);
        wallet.addMoney(money);
        System.out.println("Successful! Here is your updated wallet: $" + wallet.getMoney());

    }

    // MODIFIES: this
    // EFFECTS: displays friend list and options for friend
    private void viewFriends() {
        System.out.println("Here is your friend list:");

        for (Friend friend : friendList.getFriendList()) {
            System.out.println(friend);
        }

        System.out.println("\ta -> add new friend");
        System.out.println("\ts -> shop for a friend");
        System.out.println("\tb -> back");

        String command = input.nextLine().toLowerCase();
        if (command.equals("a")) {
            addNewFriend();
            System.out.println("You have successfully added a new friend!");
            viewFriends();
        } else if (command.equals("s")) {
            shopForFriend();
        }
    }

    // MODIFIES: this
    // EFFECTS: find friend from the given input to view to-buy list
    public void shopForFriend() {
        System.out.println("Who do you want to shop for today?");
        String name = input.nextLine();
        if (friendList.getFriend(name) == null) {
            System.out.println("Friend not found.");
            viewFriends();
        } else {
            viewToBuyList(friendList.getFriend(name));
        }
    }

    // MODIFIES: this
    // EFFECTS: displays selected friend's to-buy list
    public void viewToBuyList(Friend friend) {
        displayList(friend.getToBuyList());
    }


    // MODIFIES: this
    // EFFECTS: adds new friend to friend list
    public void addNewFriend() {
        System.out.println("Enter name:");
        String name = input.nextLine();
        if (name.equals("") == false) {
            friendList.addFriend(name);
        }
    }


    // EFFECTS: prints all wishes in wishlist to the console
    private void print() {
        ArrayList<Wish> wishes = wishList.getWishList();
        ArrayList<Friend> friends = friendList.getFriendList();

        System.out.println("My WishList:");
        for (Wish wish : wishes) {
            System.out.println(wish);
        }

        System.out.println("My Friends:");
        for (Friend friend: friends) {
            System.out.println(friend.getName());
            WishList items = friend.getToBuyList();
            for (Wish wish : items.getWishList()) {
                System.out.println(wish);
            }
        }
        System.out.println("My Wallet: $" + wallet.getMoney());

    }

    // EFFECTS: saves the wishlist to file
    private void save() {
        try {
            jsonWriter.open();
            jsonWriter.write(wishList, wallet, friendList);
            jsonWriter.close();
            System.out.println("Saved to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void load() {
        try {
            wishList = jsonReader.readWishList();
            friendList = jsonReader.readFriendList();
            wallet = jsonReader.readWallet();
            System.out.println("Loaded from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // EFFECTS: returns true if string input is able to convert into int
    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}