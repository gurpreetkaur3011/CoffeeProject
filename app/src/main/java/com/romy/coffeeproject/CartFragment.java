package com.romy.coffeeproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        double totalPrice = CartManager.getTotalPrice();
        TextView totalPriceTextView = view.findViewById(R.id.total_price);
        totalPriceTextView.setText(String.format("Total Price: $%.2f", totalPrice));

        return view;
    }
}
