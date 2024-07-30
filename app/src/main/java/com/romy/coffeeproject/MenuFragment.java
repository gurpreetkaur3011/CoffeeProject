package com.romy.coffeeproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.HashMap;

public class MenuFragment extends Fragment {

    private HashMap<MenuItem, Integer> cartItems; // Map to store cart items and their quantities
    private ViewPager2 viewPager;
    private MenuPagerAdapter pagerAdapter;
    private Button viewCartButton;
    private RecyclerView recyclerView;
    private MenuAdapter menuAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        // Initialize cart items
        cartItems = new HashMap<>();

        // Set up TabLayout and ViewPager2
        TabLayout tabLayout = view.findViewById(R.id.tab_layout);
        viewPager = view.findViewById(R.id.view_pager);

        pagerAdapter = new MenuPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Coffee");
                    break;
                case 1:
                    tab.setText("Muffins");
                    break;
                case 2:
                    tab.setText("Donuts");
                    break;
            }
        }).attach();

        // Set up the button to view the cart
        viewCartButton = view.findViewById(R.id.view_cart_button);
        viewCartButton.setOnClickListener(v -> {
            BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation);
            bottomNavigationView.setSelectedItemId(R.id.nav_cart);
        });

        return view;
    }

    private void handleItemClick(MenuItem item, int quantity) {
        if (quantity > 0) {
            // Add or update item in the cart
            cartItems.put(item, quantity);
            Toast.makeText(getContext(), item.getName() + " added to cart with quantity " + quantity, Toast.LENGTH_SHORT).show();
        } else {
            // Remove item from the cart if quantity is zero
            cartItems.remove(item);
            Toast.makeText(getContext(), item.getName() + " removed from cart", Toast.LENGTH_SHORT).show();
        }
    }

    public void resetMenuQuantities() {
        for (MenuItem item : cartItems.keySet()) {
            cartItems.put(item, 0); // Reset quantity to zero
        }
        if (menuAdapter != null) {
            menuAdapter.notifyDataSetChanged(); // Refresh the RecyclerView or UI component
        }
    }
}
