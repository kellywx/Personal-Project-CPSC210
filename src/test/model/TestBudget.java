package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBudget {
    private Budget testBudget;

    @BeforeEach
    void runBefore() {
        testBudget = new Budget();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testBudget.getMoney());
    }

    @Test
    void testAddMoney() {
        testBudget.addMoney(100);
        assertEquals(100, testBudget.getMoney());
    }

    @Test
    void testSpendMoney() {
        testBudget.addMoney(100);
        testBudget.spendMoney(30);
        assertEquals(70, testBudget.getMoney());
    }

}
