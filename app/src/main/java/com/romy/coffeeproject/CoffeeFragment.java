package com.romy.coffeeproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CoffeeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coffee, container, false);

        RecyclerView coffeeRecyclerView = view.findViewById(R.id.coffee_recycler_view);
        coffeeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MenuAdapter menuAdapter = new MenuAdapter(getCoffeeItems(), (item, quantity) -> {
            if (quantity > 0) {
                CartManager.addItemToCart(item, quantity);
            }
        });
        coffeeRecyclerView.setAdapter(menuAdapter);

        return view;
    }

    private ArrayList<MenuItem> getCoffeeItems() {
        ArrayList<MenuItem> coffeeItems = new ArrayList<>();
        coffeeItems.add(new MenuItem("Americano", 4.99, R.drawable.americano));
        coffeeItems.add(new MenuItem("Latte", 5.49, R.drawable.latte));
        coffeeItems.add(new MenuItem("Espresso", 3.99, R.drawable.espresso));
        return coffeeItems;
    }
}
