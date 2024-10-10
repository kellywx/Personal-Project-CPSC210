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
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("4")) {
                keepGoing = false;
            } else {
                processCommand(command);
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
        wishList.addWish("T-shirt", "Lululemon", 30);
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

        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            for (Wish wish : wishList.getWishList()) {
                System.out.println(wish);
            }

            System.out.println("\ta -> add new item");
            System.out.println("\td -> delete item");
            System.out.println("\tc -> check off item");
            System.out.println("\tb -> back");
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                keepGoing = false;
            } else {
                if (command.equals("a")) {
                    addToWishList();
                } else if (command.equals("d")) {
                    deleteFromWishList();
                } else if (command.equals("c")) {
                    checkOffWishList();
                }
            }
        }
    }

    private void checkOffWishList() {
        System.out.println("Which item do you want to check off? Enter name:");
        String name = input.nextLine();
        name = input.nextLine();
        System.out.println("Enter brand of item");
        String brand = input.nextLine();
        Wish selectedWish = null;
        for (Wish wish : wishList.getWishList()) {
            if (wish.getName().equals(name) && wish.getBrand().equals(brand)) {
                selectedWish = wish;
                break;
            }
        }
        if ((budget.getMoney() - selectedWish.getPrice()) < 0) {
            System.out.println("You don't have enough money!");
            viewWishList();
        } else {
            wishList.fulfillWish(name, brand);
            System.out.println("Successfully checked off.");
            budget.spendMoney(selectedWish.getPrice());
            System.out.println("Here is your new budget: $" + budget.getMoney());
        }
    }

    // MODIFIES: this
    // EFFECTS: lets user add wish to wishlist
    private void addToWishList() {
        System.out.println("Enter name of item");
        String name = input.nextLine();

        name = input.nextLine();

        System.out.println("Enter brand of item");
        String brand = input.nextLine();

        System.out.println("Enter price of item");
        int price = input.nextInt();
        

        if (price < 0) {
            System.out.println("Price cannot be negative. Try again.");
        } else {
            wishList.addWish(name, brand, price);
        }

        System.out.println("You have successfully added a new item!");
    }

    private void deleteFromWishList() {
        System.out.println("Enter name of item");
        String name = input.nextLine();

        name = input.nextLine();

        System.out.println("Enter brand of item");
        String brand = input.nextLine();

        wishList.deleteWish(name, brand);

        System.out.println("Item has been successfully deleted");
    }

    private void viewBudget() {
        System.out.println("Here is your current budget: $" + budget.getMoney());
        System.out.println("\nWould you like to add money?");
        System.out.println("\ty -> yes");
        System.out.println("\tn -> no");
        String command = input.next();
        command = command.toLowerCase();
        if (command.equals("y")) {
                addMoney();
        }
    }

    private void addMoney() {
        System.out.println("How much money would you like to add?");
        int money = input.nextInt();
        budget.addMoney(money);
        System.out.println("Successful. Here is your new budget: $" + budget.getMoney());
    }

    private void viewFriends() {
        System.out.println("Here is your friend list:");

        for (Friend friend : friendList.getFriendList()) {
        System.out.println(friend);
        }

        System.out.println("\ta -> add new friend");
        System.out.println("\ts -> shop for a friend");
        System.out.println("\tb -> back");


        String command = input.next();
        command = command.toLowerCase();
        if (command.equals("a")) {
            addNewFriend();
            System.out.println("Add Successful");
            viewFriends();
            
        } else if (command.equals("s")) {
            System.out.println("Who do you want to shop for today?");
            String name = input.nextLine();
            name = input.nextLine();
            Friend selectedFriend = null;
            for (Friend friend : friendList.getFriendList()) {
                if (friend.getName().equals(name)) {
                    selectedFriend = friend;
                    break;
                }
            }
            viewToBuyList(selectedFriend);
        } 

    }
    public void viewToBuyList(Friend friend) {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            for (Wish wish : friend.getToBuyList().getWishList()) {
                System.out.println(wish);
            }
            System.out.println("\ta -> add new item");
            System.out.println("\td -> delete item");
            System.out.println("\tc -> check off item");
            System.out.println("\tb -> back");
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("b")) {
                keepGoing = false;
            } else {
                if (command.equals("a")) {
                    addToWishList();
                } else if (command.equals("d")) {
                    deleteFromWishList();
                } else if (command.equals("c")) {
                    checkOffWishList();
                }
            }
        }
    }

    public void addNewFriend() {
        System.out.println("Enter name:");
        String name = input.nextLine();
        name = input.nextLine();
        if (name.equals("")== false) {
            friendList.addFriend(name);
        }
    }
}

// QUESTION 1: I have to add name = input.nextLine() after each one
// QUESTION 2: how can I reuse my addToWishList(), deleteFromWishList() etc. code?


