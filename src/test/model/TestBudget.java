package model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestBudget {
    private Budget testBudget;

    @BeforeEach
    void runBefore() {
        testBudget = new Budget(100);
    }

    @Test
    void testConstructor() {
        assertEquals(100, testBudget.getMoney());
    }

    @Test
    void testAddMoney() {
        testBudget.addMoney(100);
        assertEquals(200, testBudget.getMoney());
    }

    @Test
    void testSpendMoney() {
        testBudget.spendMoney(30);
        assertEquals(70, testBudget.getMoney());
    }

}
