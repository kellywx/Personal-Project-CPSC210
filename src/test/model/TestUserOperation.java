package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestUserOperation{

    private UserOperation testUser;

    @BeforeEach
    void runBefore() {
        testUser = new UserOperation();
    }

    @Test
    void testRegisterUser() {
        User user = testUser.registerUser("jane_smith", "janesmith@gmail.com", "fakepassword");
        assertEquals("Jane Smith", user.getUsername());
        assertEquals("jamesmith@gmail.com", user.getEmail());
        assertEquals("fakepassword", user.getPassword());
    }

    @Test
    void testRegisterUserWithExistingUsername() {
        testUser.registerUser("jane_smith", "janesmith@gmail.com", "fakepassword");
        User anotherUser = testUser.registerUser("jane_smith", "janesmith@gmail.com", "fakepassword1");
        assertNull(anotherUser);
    }

    @Test
    void testLoginUser() {
        testUser.registerUser("jane_smith", "janesmith@gmail.com", "fakepassword");
        User user = testUser.loginUser("jane_smith", "fakepassword");
        assertEquals("jane_smith", user.getUsername());
    }

    @Test
    void testLoginUserWrongPassword() {
        testUser.registerUser("jane_smith", "janesmith@gmail.com", "fakepassword");
        User user = testUser.loginUser("jane_smith", "wrongpassword");
        assertNull(user);
    }

    @Test
    void testFindUserByUsername() {
        testUser.registerUser("jane_smith", "janesmith@gmail.com", "fakepassword");
        User foundUser = testUser.findUserByUsername("jane_smith");
        assertEquals("jane_smith", foundUser.getUsername());
    }

    @Test
    void testFindUserByUsernameNotFound() {
        User foundUser = testUser.findUserByUsername("N/A");
        assertNull(foundUser);
    }
}

