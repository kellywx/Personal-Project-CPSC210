package model;

// Represents user's budget for shopping
public class Budget {
    private int money;

    // EFFECTS: constructs a new budget of zero dollars
    public Budget() {
        money = 0;
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
