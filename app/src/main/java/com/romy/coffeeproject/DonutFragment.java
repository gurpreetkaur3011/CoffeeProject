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

public class DonutFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donut, container, false);

        RecyclerView donutRecyclerView = view.findViewById(R.id.donut_recycler_view);
        donutRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MenuAdapter menuAdapter = new MenuAdapter(getDonutItems(), (item, quantity) -> {
            if (quantity > 0) {
                CartManager.addItemToCart(item, quantity);
            }
        });
        donutRecyclerView.setAdapter(menuAdapter);

        return view;
    }

    private ArrayList<MenuItem> getDonutItems() {
        ArrayList<MenuItem> donutItems = new ArrayList<>();
        donutItems.add(new MenuItem("Glazed Donut", 1.99, R.drawable.glazeddonut));
        donutItems.add(new MenuItem("Chocolate Donut", 2.49, R.drawable.chocolatedonut));
        donutItems.add(new MenuItem("Red Velvet Donut", 3.49, R.drawable.redvelvetdonut));

        return donutItems;
    }
}
