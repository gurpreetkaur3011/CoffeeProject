package com.romy.coffeeproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Map;

public class CartFragment extends Fragment {
    private RecyclerView recyclerView;
    private CartAdapter adapter;
    private double totalPrice;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = view.findViewById(R.id.cart_recycler_view);

        // Convert the Map to a List of CartItem objects
        ArrayList<CartItem> cartItems = new ArrayList<>();
        Map<MenuItem, Integer> cartMap = CartManager.getCartItems();
        for (Map.Entry<MenuItem, Integer> entry : cartMap.entrySet()) {
            cartItems.add(new CartItem(entry.getKey(), entry.getValue()));
        }

        adapter = new CartAdapter(cartItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        // Update total price
        totalPrice = CartManager.getTotalPrice();
        TextView totalPriceTextView = view.findViewById(R.id.total_price);
        totalPriceTextView.setText(String.format("Total Price: $%.2f", totalPrice));

        Button checkoutButton = view.findViewById(R.id.checkout_button);
        checkoutButton.setOnClickListener(v -> {
            // Example data to pass
            String userInfo = "User: CoffeeHackers"; // Replace with actual user info
            String orderSummary = generateOrderSummary(); // Replace with actual order summary
            double tax = calculateTax(totalPrice); // Replace with actual tax
            double total = totalPrice + tax; // Calculate total

            Intent intent = new Intent(getActivity(), CheckoutActivity.class);
            intent.putExtra("USER_INFO", userInfo);
            intent.putExtra("ORDER_SUMMARY", orderSummary);
            intent.putExtra("SUBTOTAL", totalPrice);
            intent.putExtra("TAX", tax);
            intent.putExtra("TOTAL", total);

            startActivity(intent);
        });

        return view;
    }

    private String generateOrderSummary() {
        // Generate a summary of items in the cart
        StringBuilder summary = new StringBuilder();
        Map<MenuItem, Integer> cartMap = CartManager.getCartItems();
        for (Map.Entry<MenuItem, Integer> entry : cartMap.entrySet()) {
            summary.append(entry.getKey().getName())
                    .append(" x")
                    .append(entry.getValue())
                    .append("\n");
        }
        return summary.toString();
    }

    private double calculateTax(double subtotal) {
        // Calculate tax (example: 15%)
        return subtotal * 0.15;
    }
}
