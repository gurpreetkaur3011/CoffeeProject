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

public class MuffinFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_muffin, container, false);

        RecyclerView muffinRecyclerView = view.findViewById(R.id.muffin_recycler_view);
        muffinRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        MenuAdapter menuAdapter = new MenuAdapter(getMuffinItems(), (item, quantity) -> {
            if (quantity > 0) {
                CartManager.addItemToCart(item, quantity);
            }
        });
        muffinRecyclerView.setAdapter(menuAdapter);

        return view;
    }

    private ArrayList<MenuItem> getMuffinItems() {
        ArrayList<MenuItem> muffinItems = new ArrayList<>();
        muffinItems.add(new MenuItem("Blueberry Muffin", 2.99, R.drawable.berrylicious));
        muffinItems.add(new MenuItem("Chocolate Muffin", 3.49, R.drawable.chocolatemuffin));
        muffinItems.add(new MenuItem("Savory Muffin", 4.49, R.drawable.savorymuffin));

        return muffinItems;
    }
}
