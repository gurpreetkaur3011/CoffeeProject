package com.romy.coffeeproject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MenuPagerAdapter extends FragmentStateAdapter {

    public MenuPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CoffeeFragment();
            case 1:
                return new MuffinFragment();
            case 2:
                return new DonutFragment();
            default:
                return new CoffeeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
