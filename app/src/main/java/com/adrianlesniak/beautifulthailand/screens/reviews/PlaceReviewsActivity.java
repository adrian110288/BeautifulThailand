package com.adrianlesniak.beautifulthailand.screens.reviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.maps.Review;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceReviewsActivity extends AppCompatActivity {

    public static final String BUNDLE_REVIEWS = "bundle_reviews";

    @BindView(R.id.reviews_list) RecyclerView mReviewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_reviews);

        ButterKnife.bind(this);

        Review[] reviews = (Review[]) getIntent().getParcelableArrayExtra(BUNDLE_REVIEWS);

        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(LayoutInflater.from(this), reviews);
        this.mReviewsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.mReviewsList.setAdapter(reviewsAdapter);
    }
}
