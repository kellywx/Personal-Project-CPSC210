package persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import model.Wish;

public class JsonTest {
    protected void checkWish(String name, String brand, int price, String checkedOff, Wish wish) {
        assertEquals(name, wish.getName());
        assertEquals(brand, wish.getBrand());
        assertEquals(price, wish.getPrice());
        assertEquals(checkedOff, wish.isCheckedOff());
    }
}

