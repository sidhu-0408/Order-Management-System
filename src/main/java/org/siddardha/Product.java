package org.siddardha;

import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

public class Product {

    private static int nextId = 1;

    private final int id;
    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private boolean active = true;

    public Product (String name, BigDecimal price) {
        this.id = nextId++;
        this.name = validateName(name);
        this.price = validatePrice(price);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validateName(name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = validatePrice(price);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate () {
        this.active = false;
    }

    private static String validateName(String name) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name should not be blank");
        }
        return name.trim();
    }

    private static BigDecimal validatePrice(BigDecimal price) {
        if(price == null || price.signum() < 0){
            throw new IllegalArgumentException("Price should not be negative");
        }
        return price;
    }
}
