package org.siddardha;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {" ", "   "})
    void blankNameIsRejected(String name) {
        assertThrows(IllegalArgumentException.class, () -> new User(name, "a@example.com", "hash"));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"not-an-email"})
    void invalidEmailIsRejected(String email) {
        assertThrows(IllegalArgumentException.class, () -> new User("Asha", email, "hash"));
    }

    @Test
    void blankPasswordHashIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new User("Asha", "a@example.com", " "));
    }

    @Test
    void sameEmailIgnoringCaseIsSameUser() {
        User u1 = new User("Meena", "meena@example.com", "h1");
        User u2 = new User("Meena K", "MEENA@example.com", "h2");

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());   // equal objects MUST have equal hash codes

        Set<User> users = new HashSet<>(Set.of(u1));
        users.add(u2);
        assertEquals(1, users.size());
    }

    @Test
    void differentEmailIsDifferentUser() {
        assertNotEquals(new User("Asha", "asha@example.com", "h"), new User("Asha", "asha2@example.com", "h"));
    }

    @Test
    void toStringHidesPasswordHash() {
        User user = new User("Asha", "asha@example.com", "secret-hash");
        assertFalse(user.toString().contains("secret-hash"));
    }
}
