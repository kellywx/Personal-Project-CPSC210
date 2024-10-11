package model;

// Represents user's wallet with amount of money
public class Wallet {
    private int money;

    // EFFECTS: constructs a new wallet with $0
    public Wallet() {
        money = 0;
    }

    // REQUIRES: money > 0
    // MODIFIES: this
    // EFFECTS: adds given amount of money to wallet
    public void addMoney(int money) {
        this.money += money;
    }

    // REQUIRES: money > 0
    // MODIFIES: this
    // EFFECTS: subtracts given amount of money from wallet
    public void spendMoney(int money) {
        this.money -= money;
    }

    public int getMoney() {
        return money;
    }

}
