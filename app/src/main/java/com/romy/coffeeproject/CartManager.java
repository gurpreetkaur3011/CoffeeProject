package com.romy.coffeeproject;

import java.util.HashMap;
import java.util.Map;

public class CartManager {
    private static final Map<MenuItem, Integer> cartItems = new HashMap<>();

    public static void addItemToCart(MenuItem item, int quantity) {
        int currentQuantity = cartItems.getOrDefault(item, 0);
        int newQuantity = currentQuantity + quantity;

        if (newQuantity > 0) {
            cartItems.put(item, newQuantity);
        } else {
            cartItems.remove(item);
        }
    }

    public static Map<MenuItem, Integer> getCartItems() {
        return new HashMap<>(cartItems);
    }

    public static int getItemQuantity(MenuItem item) {
        return cartItems.getOrDefault(item, 0);
    }

    public static double getTotalPrice() {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : cartItems.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    public static void clearCart() {
        cartItems.clear();
    }
}
