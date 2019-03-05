package org.hyperskill.hstest.examples.orders;

public class Order {

    private final String description;
    private final long cost;

    public Order(String description, long cost) {
        this.description = description;
        this.cost = cost;
    }

    public String getDescription() {
        return description;
    }

    public long getCost() {
        return cost;
    }
}
