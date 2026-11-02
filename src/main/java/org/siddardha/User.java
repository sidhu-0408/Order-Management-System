package org.siddardha;

import java.util.Objects;

public class User {
    private static int nextId = 1;

    private int id;
    private String name;
    private String email;
    private String passwordHash;

    public User(String name, String email, String passwordHash) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name should not be blank");
        }
        if(email == null ||!email.contains("@")) {
            throw new IllegalArgumentException("Invalid Email");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash should not be blank");
        }
        this.id = nextId++;
        this.name = name.trim();
        this.email = email.trim().toLowerCase();   // normalised, so "MEENA@x.com" and "meena@x.com" are the same user
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        // Email is the user's identity; the name can change and doesn't make it a different user.
        return Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        // Must use the same fields as equals(), otherwise HashSet/HashMap break.
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
