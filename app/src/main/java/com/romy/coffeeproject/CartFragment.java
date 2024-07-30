package com.romy.coffeeproject;

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
            String userInfo = "User: CoffeeHackers"; // Replace with actual user info
            String orderSummary = generateOrderSummary(); // Replace with actual order summary
            double tax = calculateTax(totalPrice); // Replace with actual tax
            double total = totalPrice + tax;

            // Navigate to OrderFragment
            Bundle bundle = new Bundle();
            bundle.putString("USER_INFO", userInfo);
            bundle.putString("ORDER_SUMMARY", orderSummary);
            bundle.putDouble("SUBTOTAL", totalPrice);
            bundle.putDouble("TAX", tax);
            bundle.putDouble("TOTAL", total);

            OrderFragment orderFragment = new OrderFragment();
            orderFragment.setArguments(bundle);

            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, orderFragment) // Replace with your actual fragment container ID
                    .addToBackStack(null)
                    .commit();
        });

        Button goToMenuButton = view.findViewById(R.id.go_to_menu_button);
        goToMenuButton.setOnClickListener(v -> {
            // Navigate to MenuFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MenuFragment()) // Replace with your actual fragment container ID
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private String generateOrderSummary() {
        StringBuilder summary = new StringBuilder();
        Map<MenuItem, Integer> cartMap = CartManager.getCartItems();
        for (Map.Entry<MenuItem, Integer> entry : cartMap.entrySet()) {
            summary.append(entry.getKey().getName())
                    .append(" (x")
                    .append(entry.getValue())
                    .append("): $")
                    .append(entry.getKey().getPrice() * entry.getValue())
                    .append("\n");
        }
        return summary.toString();
    }

    private double calculateTax(double subtotal) {
        return subtotal * 0.15; // Example tax rate of 15%
    }
}
