package org.siddardha;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // 1. Valid objects
        System.out.println("=== 1. Valid objects ===");
        Product pen = new Product("Pen", new BigDecimal("10.50"));
        pen.setCategory("Stationery");
        Product notebook = new Product("Notebook", new BigDecimal("45.00"));
        notebook.setCategory("Stationery");
        Product laptop = new Product("Laptop", new BigDecimal("55999.99"));
        laptop.setCategory("Electronics");

        System.out.println(pen);
        System.out.println(notebook);
        System.out.println(laptop);

        User asha = new User("Asha", "asha@example.com", "hash123");
        User ravi = new User("Ravi", "ravi@example.com", "hash456");
        System.out.println(asha);   // must NOT show the password hash
        System.out.println(ravi);

        // 2. Validation
        System.out.println("\n=== 2. Validation ===");
        expectRejected("blank product name", () -> new Product("", new BigDecimal("10")));
        expectRejected("null product name", () -> new Product(null, new BigDecimal("10")));
        expectRejected("negative price", () -> new Product("Pen", new BigDecimal("-1")));
        expectRejected("null price", () -> new Product("Pen", null));
        expectRejected("setPrice negative", () -> pen.setPrice(new BigDecimal("-5")));
        expectRejected("invalid email", () -> new User("Bad", "not-an-email", "hash"));
        expectRejected("blank user name", () -> new User(" ", "x@example.com", "hash"));

        // 3. equals / hashCode
        System.out.println("\n=== 3. equals / hashCode ===");
        User u1 = new User("Meena", "meena@example.com", "h1");
        User u2 = new User("Meena K", "MEENA@example.com", "h2");   // same email, different case
        System.out.println("u1 == u2      : " + (u1 == u2));
        System.out.println("u1.equals(u2) : " + u1.equals(u2));

        Set<User> users = new HashSet<>();
        users.add(u1);
        users.add(u2);
        System.out.println("Set size after adding u1, u2 : " + users.size());   // expect 1
        users.add(asha);
        System.out.println("Set size after adding asha   : " + users.size());   // expect 2

        // 4. id and deactivate()
        System.out.println("\n=== 4. id and deactivate ===");
        System.out.println("pen id = " + pen.getId() + ", notebook id = " + notebook.getId());
        System.out.println("pen active before: " + pen.isActive());
        pen.deactivate();
        System.out.println("pen active after : " + pen.isActive());
        // pen.setId(99);        // uncomment: it should NOT compile
    }

    // Runs the given action and reports whether it was rejected with IllegalArgumentException.
    private static void expectRejected(String label, Runnable action) {
        try {
            action.run();
            System.out.println("FAIL: " + label + " was accepted");
        } catch (IllegalArgumentException e) {
            System.out.println("OK, rejected (" + label + "): " + e.getMessage());
        }
    }
}