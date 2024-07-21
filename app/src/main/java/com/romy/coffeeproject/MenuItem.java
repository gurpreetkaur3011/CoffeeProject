package com.romy.coffeeproject;

public class MenuItem {
    private final String name;
    private final double price;
    private final int imageResource;

    public MenuItem(String name, double price, int imageResource) {
        this.name = name;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getImageResource() {
        return imageResource;
    }
}
