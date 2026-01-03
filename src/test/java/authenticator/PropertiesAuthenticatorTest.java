package authenticator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertiesAuthenticatorTest {
    private PropertiesAuthenticator authenticator;

    @BeforeEach
    void setUp() {
        authenticator = new PropertiesAuthenticator();
        if (!authenticator.authenticate("admin", "admin")) {
            authenticator.addUser("admin", "admin");
        }
    }

    @Test
    void testSuccessfulAuthentication() {
        assertTrue(authenticator.authenticate("admin", "admin"));
    }

    @Test
    void testFailedAuthentication() {
        assertFalse(authenticator.authenticate("admin", "wrongpassword"));
    }

    @Test
    void testAddUser() {
        authenticator.addUser("newUser", "password123");
        assertTrue(authenticator.authenticate("newUser", "password123"));
    }

    @Test
    void testDuplicateUser() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authenticator.addUser("admin", "newpassword");
        });
        assertEquals("Le nom d'utilisateur existe déjà !", exception.getMessage());
    }
}