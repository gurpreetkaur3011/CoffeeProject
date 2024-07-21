package com.romy.coffeeproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

public class CheckoutActivity extends AppCompatActivity {

    private TextView userInfoTextView;
    private TextView orderSummaryTextView;
    private TextView subtotalTextView;
    private TextView taxTextView;
    private TextView totalTextView;
    private Button confirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        userInfoTextView = findViewById(R.id.user_info);
        orderSummaryTextView = findViewById(R.id.order_summary);
        subtotalTextView = findViewById(R.id.subtotal);
        taxTextView = findViewById(R.id.tax);
        totalTextView = findViewById(R.id.total);
        confirmButton = findViewById(R.id.confirm_button);

        // Get data from intent
        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());
        String userInfo = intent.get().getStringExtra("USER_INFO");
        String orderSummary = intent.get().getStringExtra("ORDER_SUMMARY");
        double subtotal = intent.get().getDoubleExtra("SUBTOTAL", 0);
        double tax = intent.get().getDoubleExtra("TAX", 0);
        double total = intent.get().getDoubleExtra("TOTAL", 0);

        // Set data to views
        userInfoTextView.setText(userInfo);
        orderSummaryTextView.setText(orderSummary);
        subtotalTextView.setText("Subtotal: $" + String.format("%.2f", subtotal));
        taxTextView.setText("Tax: $" + String.format("%.2f", tax));
        totalTextView.setText("Total: $" + String.format("%.2f", total));

        // Handle button click
        confirmButton.setOnClickListener(v -> {

            Toast.makeText(this, "Your order has been placed!", Toast.LENGTH_SHORT).show();

            // Navigate back to the MenuActivity
            intent.set(new Intent(CheckoutActivity.this, MainActivity.class));

            startActivity(intent.get());

            // Optionally finish the current activity if you don't want it to stay in the back stack
            finish();

        });
    }
}
