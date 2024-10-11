package model;

import static org.junit.Assert.assertEquals;

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
        assertEquals(0, testWallet.getMoney());
    }

    @Test
    void testAddMoney() {
        testWallet.addMoney(100);
        assertEquals(100, testWallet.getMoney());
    }

    @Test
    void testSpendMoney() {
        testWallet.addMoney(100);
        testWallet.spendMoney(30);
        assertEquals(70, testWallet.getMoney());
    }

}
