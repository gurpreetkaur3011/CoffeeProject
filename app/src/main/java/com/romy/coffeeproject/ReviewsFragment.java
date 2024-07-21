package com.romy.coffeeproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ReviewsAdapter reviewsAdapter;
    private ArrayList<Review> reviews;
    private EditText editTextUsername;
    private EditText editTextReview;
    private RatingBar ratingBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reviews, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_reviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the list of reviews and adapter
        reviews = new ArrayList<>();
        reviews.add(new Review("John Doe", "Great coffee!", 4.5f));
        reviews.add(new Review("Jane Smith", "Loved the muffins!", 5.0f));
        reviews.add(new Review("Sam Wilson", "Good service.", 3.5f));

        reviewsAdapter = new ReviewsAdapter(reviews);
        recyclerView.setAdapter(reviewsAdapter);

        // Initialize review submission form elements
        editTextUsername = view.findViewById(R.id.edit_text_username);
        editTextReview = view.findViewById(R.id.edit_text_review);
        ratingBar = view.findViewById(R.id.rating_bar);
        Button buttonSubmitReview = view.findViewById(R.id.button_submit_review);

        // Set up button click listener
        buttonSubmitReview.setOnClickListener(v -> {
            String userName = editTextUsername.getText().toString();
            String reviewText = editTextReview.getText().toString();
            float rating = ratingBar.getRating();

            if (!userName.isEmpty() && !reviewText.isEmpty() && rating > 0) {
                Review newReview = new Review(userName, reviewText, rating);
                reviews.add(newReview);
                reviewsAdapter.notifyDataSetChanged();

                // Clear the input fields
                editTextUsername.setText("");
                editTextReview.setText("");
                ratingBar.setRating(0);

                Toast.makeText(getContext(), "Review submitted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please fill in all fields and provide a rating.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
