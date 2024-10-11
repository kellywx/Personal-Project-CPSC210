package ui;

import java.util.Scanner;

import model.Budget;
import model.Friend;
import model.FriendList;
import model.Wish;
import model.WishList;


// Wishlist application
public class WishListApp {
    private WishList wishList;
    private FriendList friendList;
    private Budget budget;
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
            displayMenu();
            command = input.nextLine();
            command = command.toLowerCase();
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
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("1")) {
            viewWishList();
        } else if (command.equals("2")) {
            viewBudget();
        } else if (command.equals("3")) {
            viewFriends();
        }
        else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes wishlist and friend list
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
        budget =  new Budget();
        budget.addMoney(50);
        input = new Scanner(System.in);
        input.useDelimiter("\r?\n|\r");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1. view wishlist");
        System.out.println("\t2. view budget");
        System.out.println("\t3. view friends");
        System.out.println("\t4. quit");
    }

    // MODIFIES: this
    // EFFECTS: lets user view wishlist
    private void viewWishList() {
        System.out.println("Here is your wishlist:");
        function(wishList);
    }

    private void function(WishList list) {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            for (Wish wish : list.getWishList()) {
                System.out.println(wish);
            }
            displayMiniMenu();
            command = input.nextLine();
            command = command.toLowerCase();

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

    private void displayMiniMenu() {
        System.out.println("\ta -> add new item");
        System.out.println("\td -> delete item");
        System.out.println("\tc -> check off item");
        System.out.println("\tb -> back");
    }

    private void processNewCommand(WishList wishList, String command) {
        if (command.equals("a")) {
            addToWishList(wishList);
        } else if (command.equals("d")) {
            if (wishList.getWishList().isEmpty() == false) {
                deleteFromWishList(wishList);
            } else {
                System.out.println("Empty List");
            }
        } else if (command.equals("c")) {
            if (wishList.getWishList().isEmpty() == false) {
                checkOffWishList(wishList);
            } else {
                System.out.println("Empty List");
            }
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void checkOffWishList(WishList wishList) {
        System.out.println("Which item do you want to check off? Enter name:");
        String name = input.nextLine();
        while (name.isEmpty()) {
            System.out.println("Try again");
            name = input.nextLine();  // Read input again
        }

        String brand = "";
        Wish selectedWish = null;
        for (Wish wish: wishList.getWishList()) {
            if (wish.getName().equals(name)) {
                System.out.println("Enter brand of item");
                brand = input.nextLine();
                while (brand.isEmpty()) {
                    System.out.println("Try again");
                    brand = input.nextLine();
                }
                if (wish.getBrand().equals(brand)) {
                    selectedWish = wish;
                    break;
                }
            }
        }
        if (selectedWish == null) {
            System.out.println("No such item");
        } else if (selectedWish.isFulfilled() == true) {
            System.out.println("Already checked off");
        } else if (selectedWish!=null) {
            if (budget.getMoney() - selectedWish.getPrice() < 0) {
                System.out.println("You don't have enough money!");
            } else {
                wishList.fulfillWish(name, brand);
                System.out.println("Successfully checked off.");
                budget.spendMoney(selectedWish.getPrice());
                System.out.println("Here is your new budget: $" + budget.getMoney());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: lets user add wish to wishlist
    private void addToWishList(WishList wishList) {
        System.out.println("Enter name of item");
        String name = input.nextLine();
        while (name.isEmpty()) {
            System.out.println("Try again");
            name = input.nextLine();
        }

        System.out.println("Enter brand of item");
        String brand = input.nextLine();
        while (brand.isEmpty()) {
            System.out.println("Try again");
            brand = input.nextLine();
        }

        System.out.println("Enter price of item");
        String number = input.nextLine();
        while (number.isEmpty()) {
            System.out.println("Try again");
            number = input.nextLine();
        }
        int price = Integer.parseInt(number);

        //input.nextLine();
        
        if (price < 0) {
            System.out.println("Price cannot be negative. Try again.");
        } else {
            wishList.addWish(name, brand, price);
            System.out.println("You have successfully added a new item!");
        }
    }

    private void deleteFromWishList(WishList wishList) {
        System.out.println("Enter name of item");
        String name = input.nextLine();
        while (name.isEmpty()) {
            System.out.println("Try again");
            name = input.nextLine();
        }

        Wish selectedWish = null;
        String brand = "";
        for (Wish wish: wishList.getWishList()) {
            if (wish.getName().equals(name)) {
                System.out.println("Enter brand of item");
                brand = input.nextLine();
                while (brand.isEmpty()) {
                    System.out.println("Try again");
                    brand = input.nextLine();
                }
                if (wish.getBrand().equals(brand)) {
                    selectedWish = wish;
                    break;
                }
            }
        }
        if (selectedWish == null) {
            System.out.println("No such item");
        } else {
            wishList.deleteWish(name, brand);
            System.out.println("Item has been successfully deleted");
        }
        
    }

    private void viewBudget() {
        System.out.println("Here is your current budget: $" + budget.getMoney());
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

    private void addMoney() {
        System.out.println("How much money would you like to add?");
        String number = input.nextLine();
        while (number.isEmpty() || isInteger(number) == false) {
            System.out.println("Try again");
            number = input.nextLine();
        }
        int money = Integer.parseInt(number);
        budget.addMoney(money);
        System.out.println("Successful. Here is your new budget: $" + budget.getMoney());
        }

    public boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void viewFriends() {
        System.out.println("Here is your friend list:");

        for (Friend friend : friendList.getFriendList()) {
        System.out.println(friend);
        }

        System.out.println("\ta -> add new friend");
        System.out.println("\ts -> shop for a friend");
        System.out.println("\tb -> back");


        String command = input.nextLine();
        command = command.toLowerCase();
        while (command.isEmpty()) {
            System.out.println("Try again");
            command = input.nextLine();
        }
        if (command.equals("a")) {
            addNewFriend();
            System.out.println("Add Successful");
            viewFriends();
        } else if (command.equals("s")) {
            System.out.println("Who do you want to shop for today?");
            String name = input.nextLine();
            while (name.isEmpty()) {
                System.out.println("Try again");
                name = input.nextLine();
            }
            Friend selectedFriend = null;
            for (Friend friend : friendList.getFriendList()) {
                if (friend.getName().equals(name)) {
                    selectedFriend = friend;
                    break;
                }
            }
            if (selectedFriend == null) {
                System.out.println("No friend");
            } else {
                viewToBuyList(selectedFriend);
            }
        } 

    }
    public void viewToBuyList(Friend friend) {
        function(friend.getToBuyList());
    }

    public void addNewFriend() {
        System.out.println("Enter name:");
        String name = input.nextLine();
        while (name.isEmpty()) {
            System.out.println("Try again");
            name = input.nextLine();
        }
        if (name.equals("")== false) {
            friendList.addFriend(name);
        }
    }
}


