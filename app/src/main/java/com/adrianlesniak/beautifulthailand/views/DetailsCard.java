package com.adrianlesniak.beautifulthailand.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.adrianlesniak.beautifulthailand.R;

/**
 * Created by adrian on 29/01/2017.
 */

public abstract class DetailsCard extends CardView {

    protected ImageView mIcon;

    public DetailsCard(Context context) {
        this(context, null);
    }

    public DetailsCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(getContext(), this.getCardLayoutResource(), this);

        this.mIcon = (ImageView) findViewById(R.id.card_icon);
    }

    protected abstract int getCardLayoutResource();
}
