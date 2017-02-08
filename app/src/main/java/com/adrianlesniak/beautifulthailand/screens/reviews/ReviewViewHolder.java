package com.adrianlesniak.beautifulthailand.screens.reviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.maps.Review;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by adrian on 08/02/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.review_author_text_view) TextView mReviewAuthor;
    @BindView(R.id.review_text_view) TextView mReviewText;
    @BindView(R.id.review_rating_text_view) TextView mReviewRating;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(itemView);
    }

    public void bind(Review review) {
        this.mReviewAuthor.setText(review.authorName);
        this.mReviewText.setText(review.text);
        this.mReviewRating.setText(review.rating);
    }
}
