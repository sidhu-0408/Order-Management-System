package org.siddardha;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String full_name;
    private String email;
    private String passwordHash;

    public User() {
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
        return "User{id=" + id + ", name='" + full_name + "', email='" + email + "'}";
    }
}
