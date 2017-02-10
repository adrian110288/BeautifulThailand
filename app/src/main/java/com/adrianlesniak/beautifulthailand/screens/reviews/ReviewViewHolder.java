package com.adrianlesniak.beautifulthailand.screens.reviews;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.BTApplication;
import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.maps.Review;
import com.adrianlesniak.beautifulthailand.utilities.CalendarHelper;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by adrian on 08/02/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.review_author_text_view) TextView mReviewAuthor;
    @BindView(R.id.review_date_text_view) TextView mReviewDate;
    @BindView(R.id.review_text_view) TextView mReviewText;
    @BindView(R.id.review_rating_text_view) TextView mReviewRating;

    public ReviewViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bind(Review review) {
        this.mReviewAuthor.setText(review.authorName);
        this.mReviewDate.setText(CalendarHelper.getHumanReadableDate(review.time));
        this.mReviewText.setText(review.text);
        this.mReviewRating.setText(String.valueOf(review.rating * 1.0));
    }
}
