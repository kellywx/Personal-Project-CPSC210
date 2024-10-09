package model;

// Represents user's budget for shopping
public class Budget {
    private int money;

    // REQUIRES: money > 0
    // EFFECTS: constructs the amount of money user has to spend
    public Budget(int money) {
        this.money = money;
    }

    // MODIFIES: this
    // EFFECTS: adds given amount of money to budget
    public void addMoney(int money) {
        this.money += money;
    }

    // MODIFIES: this
    // EFFECTS: subtracts given amount of money from budget
    public void spendMoney(int money) {
        this.money -= money;
    }

    public int getMoney() {
        return money;
    }

}
