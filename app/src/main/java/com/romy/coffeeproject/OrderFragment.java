package com.romy.coffeeproject;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OrderFragment extends Fragment {

    private static final int SMS_PERMISSION_CODE = 123;
    private double totalPrice;
    private EditText contactInfoEditText;
    private RadioGroup contactMethodGroup;
    private RadioButton emailRadio;
    private RadioButton smsRadio;
    private String contactInfo;
    private String userInfo;
    private String orderSummary;
    private double subtotal;
    private double tax;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_checkout, container, false);

        // Get the order details from the arguments
        Bundle arguments = getArguments();
        userInfo = arguments.getString("USER_INFO");
        orderSummary = arguments.getString("ORDER_SUMMARY");
        subtotal = arguments.getDouble("SUBTOTAL", 0);
        tax = arguments.getDouble("TAX", 0);
        totalPrice = arguments.getDouble("TOTAL", 0);

        // Display the order details on the screen
        TextView userInfoTextView = view.findViewById(R.id.user_info);
        userInfoTextView.setText(userInfo);

        TextView orderSummaryTextView = view.findViewById(R.id.order_summary);
        orderSummaryTextView.setText(orderSummary);

        TextView subtotalTextView = view.findViewById(R.id.subtotal);
        subtotalTextView.setText(String.format("Subtotal: $%.2f", subtotal));

        TextView taxTextView = view.findViewById(R.id.tax);
        taxTextView.setText(String.format("Tax: $%.2f", tax));

        TextView totalTextView = view.findViewById(R.id.total);
        totalTextView.setText(String.format("Total: $%.2f", totalPrice));

        // Initialize contact info and method
        contactInfoEditText = view.findViewById(R.id.contact_info);
        contactMethodGroup = view.findViewById(R.id.contact_method_group);
        emailRadio = view.findViewById(R.id.email_radio);
        smsRadio = view.findViewById(R.id.sms_radio);

        // Set a listener on the radio group to update contactInfoEditText hint
        contactMethodGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.email_radio) {
                contactInfoEditText.setHint("Enter your email");
            } else if (checkedId == R.id.sms_radio) {
                contactInfoEditText.setHint("Enter your phone number");
            }
        });

        // Set up the confirm button
        Button confirmButton = view.findViewById(R.id.confirm_button);
        confirmButton.setOnClickListener(v -> {
            contactInfo = contactInfoEditText.getText().toString().trim();
            if (contactInfo.isEmpty()) {
                Toast.makeText(getContext(), "Please enter your email or phone number.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (orderSummary.isEmpty()) {
                Toast.makeText(getContext(), "Your cart is empty. Please add items to your cart before ordering.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (emailRadio.isChecked()) {
                if (!Patterns.EMAIL_ADDRESS.matcher(contactInfo).matches()) {
                    Toast.makeText(getContext(), "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendEmail(contactInfo, userInfo, orderSummary, subtotal, tax, totalPrice);
            } else if (smsRadio.isChecked()) {
                if (!Patterns.PHONE.matcher(contactInfo).matches()) {
                    Toast.makeText(getContext(), "Please enter a valid phone number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendSMS(contactInfo, createSmsBody());
            }
        });

        // Set up the "Go to Cart" button
        Button goToCartButton = view.findViewById(R.id.go_to_cart_button);
        goToCartButton.setOnClickListener(v -> {
            // Navigate to CartFragment
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new CartFragment()) // Replace with your actual fragment container ID
                    .addToBackStack(null)
                    .commit();
        });

        return view;
    }

    private void sendEmail(String emailAddress, String userInfo, String orderSummary, double subtotal, double tax, double total) {
        String emailSubject = "Order Confirmation";
        String emailBody = "Thank you for your order!\n\n" +
                "User Info: " + userInfo + "\n\n" +
                "Order Summary:\n" + orderSummary + "\n" +
                "Subtotal: $" + String.format("%.2f", subtotal) + "\n" +
                "Tax: $" + String.format("%.2f", tax) + "\n" +
                "Total: $" + String.format("%.2f", total) + "\n\n" +
                "We hope you enjoy your coffee!";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
            clearCartAndContactInfo();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private void sendSMS(String phoneNumber, String smsBody) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        smsIntent.putExtra("sms_body", smsBody);
        try {
            startActivity(smsIntent);
            clearCartAndContactInfo();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getContext(), "No SMS clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    private String createSmsBody() {
        return "Thank you for your order!\n\n" +
                "User Info: " + userInfo + "\n\n" +
                "Order Summary:\n" + orderSummary + "\n" +
                "Subtotal: $" + String.format("%.2f", subtotal) + "\n" +
                "Tax: $" + String.format("%.2f", tax) + "\n" +
                "Total: $" + String.format("%.2f", totalPrice) + "\n\n" +
                "We hope you enjoy your coffee!";
    }

    private void clearCartAndContactInfo() {
        // Clear contact info
        contactInfoEditText.setText("");

        // Clear order details
        userInfo = "";
        orderSummary = "";
        subtotal = 0;
        tax = 0;
        totalPrice = 0;

        // Update the UI
        TextView userInfoTextView = getView().findViewById(R.id.user_info);
        userInfoTextView.setText("");

        TextView orderSummaryTextView = getView().findViewById(R.id.order_summary);
        orderSummaryTextView.setText("");

        TextView subtotalTextView = getView().findViewById(R.id.subtotal);
        subtotalTextView.setText("Subtotal: $0.00");

        TextView taxTextView = getView().findViewById(R.id.tax);
        taxTextView.setText("Tax: $0.00");

        TextView totalTextView = getView().findViewById(R.id.total);
        totalTextView.setText("Total: $0.00");

        Toast.makeText(getContext(), "Order sent Successfully.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS(contactInfo, createSmsBody());
            } else {
                Toast.makeText(getContext(), "SMS permission denied.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
