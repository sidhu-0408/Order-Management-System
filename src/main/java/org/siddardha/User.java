package org.siddardha;

import java.util.Objects;

public class User {

    private int id;
    private String name;
    private String email;
    private String passwordHash;

    public User(String name, String email, String passwordHash) {
        if(email == null ||!email.contains("@")) {
            throw new IllegalArgumentException("Invalid Email");
        }
        if (passwordHash == null || passwordHash.isBlank()) {
            throw new IllegalArgumentException("Password hash should not be blank");
        }
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
