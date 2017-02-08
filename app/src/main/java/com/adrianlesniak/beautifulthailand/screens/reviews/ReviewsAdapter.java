package com.adrianlesniak.beautifulthailand.screens.reviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.maps.Review;

import java.util.List;

/**
 * Created by adrian on 08/02/2017.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    private LayoutInflater mLayoutInflater;

    private List<Review> mReviews;

    public ReviewsAdapter(LayoutInflater layoutInflater, List<Review> reviews) {
        this.mLayoutInflater = layoutInflater;
        this.mReviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = this.mLayoutInflater.inflate(R.layout.list_item_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        holder.bind(this.mReviews.get(position));
    }

    @Override
    public int getItemCount() {
        return this.mReviews != null? this.mReviews.size() : 0;
    }
}
