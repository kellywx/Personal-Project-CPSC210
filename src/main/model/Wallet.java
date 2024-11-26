package model;

import org.json.JSONObject;

import persistence.Writable;

// Represents user's wallet with amount of money
public class Wallet implements Writable {
    private double money;

    // EFFECTS: constructs a new wallet with $0.00
    public Wallet() {
        money = 0.00;
    }

    // REQUIRES: money > 0
    // MODIFIES: this
    // EFFECTS: adds given amount of money to wallet
    public void addMoney(double money) {
        this.money += money;
        String str = Double.toString(money);
        EventLog.getInstance().logEvent(new Event("Added " + str + " to wallet"));
    }

    // REQUIRES: money > 0
    // MODIFIES: this
    // EFFECTS: subtracts given amount of money from wallet
    public void spendMoney(double money) {
        this.money -= money;
        String str = Double.toString(money);
        EventLog.getInstance().logEvent(new Event("Spent " + str));
    }

    public double getMoney() {
        return money;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("wallet", money);
        return json;
    }

}
