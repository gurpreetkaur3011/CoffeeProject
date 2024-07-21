package com.romy.coffeeproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private final ArrayList<MenuItem> menuItems;
    private final OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(MenuItem item, int quantity);
    }

    public MenuAdapter(ArrayList<MenuItem> menuItems, OnItemClickListener onItemClickListener) {
        this.menuItems = menuItems;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        MenuItem item = menuItems.get(position);
        holder.nameTextView.setText(item.getName());
        holder.priceTextView.setText(String.format("$%.2f", item.getPrice()));
        holder.imageView.setImageResource(item.getImageResource());

        holder.quantityTextView.setText("0");

        holder.increaseButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantityTextView.getText().toString());
            quantity++;
            holder.quantityTextView.setText(String.valueOf(quantity));
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(item, quantity);
            }
        });

        holder.decreaseButton.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.quantityTextView.getText().toString());
            if (quantity > 0) {
                quantity--;
                holder.quantityTextView.setText(String.valueOf(quantity));
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(item, quantity);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTextView;
        TextView priceTextView;
        TextView quantityTextView;
        Button increaseButton;
        Button decreaseButton;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image);
            nameTextView = itemView.findViewById(R.id.item_name);
            priceTextView = itemView.findViewById(R.id.item_price);
            quantityTextView = itemView.findViewById(R.id.quantity_text);
            increaseButton = itemView.findViewById(R.id.increase_button);
            decreaseButton = itemView.findViewById(R.id.decrease_button);
        }
    }
}
