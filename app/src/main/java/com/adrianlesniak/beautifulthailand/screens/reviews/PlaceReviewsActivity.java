package com.adrianlesniak.beautifulthailand.screens.reviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.maps.Review;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlaceReviewsActivity extends AppCompatActivity {

    public static final String BUNDLE_REVIEWS = "bundle_reviews";

    @BindView(R.id.reviews_list) RecyclerView mReviewsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_reviews);

        ButterKnife.bind(this);

        List<Review> reviewsList = getIntent().getParcelableArrayListExtra(BUNDLE_REVIEWS);

        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(LayoutInflater.from(this), reviewsList);
        this.mReviewsList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        this.mReviewsList.setAdapter(reviewsAdapter);
    }

    @OnClick(R.id.close_button)
    public void close(View view) {
        this.finish();
    }
}
