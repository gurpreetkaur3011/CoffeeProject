package com.romy.coffeeproject;

import java.util.HashMap;
import java.util.Map;

public class CartManager {
    private static final Map<MenuItem, Integer> cartItems = new HashMap<>();

    /**
     * Adds an item to the cart with the specified quantity.
     * If the item already exists in the cart, the quantity is updated.
     *
     * @param item     The menu item to add.
     * @param quantity The quantity to add.
     */
    public static void addItemToCart(MenuItem item, int quantity) {
        if (quantity <= 0) return; // No negative or zero quantity allowed

        // Update the quantity if the item already exists in the cart
        if (cartItems.containsKey(item)) {
            cartItems.put(item, cartItems.get(item) + quantity);
        } else {
            cartItems.put(item, quantity);
        }
    }

    /**
     * Returns a copy of the cart items with their quantities.
     *
     * @return A map of items to their quantities in the cart.
     */
    public static Map<MenuItem, Integer> getCartItems() {
        return new HashMap<>(cartItems);
    }

    /**
     * Calculates the total price of the items in the cart.
     *
     * @return The total price of the cart items.
     */
    public static double getTotalPrice() {
        double total = 0;
        for (Map.Entry<MenuItem, Integer> entry : cartItems.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }

    /**
     * Clears all items from the cart.
     */
    public static void clearCart() {
        cartItems.clear();
    }
}
