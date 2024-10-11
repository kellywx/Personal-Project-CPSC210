package ui;

import java.util.Scanner;

import model.Wallet;
import model.Friend;
import model.FriendList;
import model.Wish;
import model.WishList;

// Wishlist application
public class WishListApp {
    private WishList wishList;
    private FriendList friendList;
    private Wallet wallet;
    private Scanner input;

    // EFFECTS: runs the wishlist application
    public WishListApp() {
        runWishList();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runWishList() {
        boolean keepGoing = true;
        String command = null;

        init();

        while (keepGoing) {
            displayMainMenu();
            command = input.nextLine().toLowerCase();
            if (command.isEmpty() == false) {
                if (command.equals("4")) {
                    keepGoing = false;
                } else {
                    processCommand(command);
                }
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user input from main menu
    private void processCommand(String command) {
        if (command.equals("1")) {
            viewWishList();
        } else if (command.equals("2")) {
            viewBudget();
        } else if (command.equals("3")) {
            viewFriends();
        } else {
            System.out.println("Selection not valid... Try again");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes wishlist, friend list, friends, and wallet
    private void init() {
        wishList = new WishList();
        wishList.addWish("Shoes", "Nike", 100);
        wishList.addWish("Makeup", "Sephora", 50);
        wishList.addWish("Shirt", "Lululemon", 30);
        friendList = new FriendList();
        friendList.addFriend("Monica");
        friendList.addFriend("Rachel");
        friendList.addFriend("Phoebe");
        Friend monica = friendList.getFriendList().get(0);
        Friend rachel = friendList.getFriendList().get(1);
        Friend phoebe = friendList.getFriendList().get(2);
        monica.getToBuyList().addWish("Shoes", "Adidas", 80);
        rachel.getToBuyList().addWish("Necklace", "EverJewels", 30);
        phoebe.getToBuyList().addWish("Books", "Indigo", 40);
        wallet = new Wallet();
        wallet.addMoney(50);
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
    }

    // EFFECTS: displays main menu of options to user
    private void displayMainMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> view wishlist");
        System.out.println("\t2 -> view wallet");
        System.out.println("\t3 -> view friends");
        System.out.println("\t4 -> quit");
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
        emptyInput(name);
        String brand = "";
        Wish selectedWish = null;
        for (Wish wish : wishList.getWishList()) {
            if (wish.getName().equals(name)) {
                System.out.println("Enter brand of item");
                brand = input.nextLine();
                emptyInput(brand);
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
            processSelectedWish(selectedWish, name, brand);
        }
    }

    // MODIFIES: this
    // EFFECTS: checks selected Wish if user has enough money in wallet
    private void processSelectedWish(Wish selectedWish, String name, String brand) {
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
    // EFFECTS: prints "Try again" if user input is empty
    private void emptyInput(String string) {
        while (string.isEmpty()) {
            System.out.println("Try again");
            string = input.nextLine();
        }
    }

    // MODIFIES: this
    // EFFECTS: add new Wish to wishlist
    private void addToWishList(WishList wishList) {
        System.out.println("Enter name of item");
        String name = input.nextLine();
        emptyInput(name);
        System.out.println("Enter brand of item");
        String brand = input.nextLine();
        emptyInput(brand);
        System.out.println("Enter price of item");
        String number = input.nextLine();
        emptyInput(number);
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
        emptyInput(name);

        Wish selectedWish = null;
        String brand = "";
        for (Wish wish : wishList.getWishList()) {
            if (wish.getName().equals(name)) {
                System.out.println("Enter brand of item");
                brand = input.nextLine();
                emptyInput(brand);
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
    private void viewBudget() {
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
        emptyInput(number);
        while (isInteger(number) == false) {
            System.out.println("Not valid. Try again");
            number = input.nextLine();
        }
        int money = Integer.parseInt(number);
        wallet.addMoney(money);
        System.out.println("Successful! Here is your updated wallet: $" + wallet.getMoney());

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
        emptyInput(command);
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
        emptyInput(name);
        Friend selectedFriend = null;
        for (Friend friend : friendList.getFriendList()) {
            if (friend.getName().equals(name)) {
                selectedFriend = friend;
                break;
            }
        }
        if (selectedFriend == null) {
            System.out.println("Friend not found.");
        } else {
            viewToBuyList(selectedFriend);
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
        emptyInput(name);
        if (name.equals("") == false) {
            friendList.addFriend(name);
        }
    }
}
