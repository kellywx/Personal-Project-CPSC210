package model;

import static org.junit.Assert.assertEquals;

import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestWallet {
    private Wallet testWallet;

    @BeforeEach
    void runBefore() {
        testWallet = new Wallet();
    }

    @Test
    void testConstructor() {
        assertEquals(0.00, testWallet.getMoney(),0.00001);
    }

    @Test
    void testAddMoney() {
        testWallet.addMoney(100.50);
        assertEquals(100.50, testWallet.getMoney(),0.00001);
    }

    @Test
    void testSpendMoney() {
        testWallet.addMoney(100.50);
        testWallet.spendMoney(30.00);
        assertEquals(100.50 - 30.00, testWallet.getMoney(),0.00001);
    }

    @Test
    public void testToJson() {
        JSONObject json = testWallet.toJson();

        assertTrue(json.has("wallet"));
        assertEquals(0.00, json.getDouble("wallet"),0.00001);

    }

    @Test
    public void testToJsonAddMoney() {
        testWallet.addMoney(100.00);
        JSONObject json = testWallet.toJson();

        assertTrue(json.has("wallet"));
        assertEquals(100.00, json.getDouble("wallet"),0.00001);

    }

}
