package com.adrianlesniak.beautifulthailand.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by adrian on 29/01/2017.
 */

public abstract class DetailCardView extends CardView {

    public DetailCardView(Context context) {
        this(context, null);
    }

    public DetailCardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        View.inflate(getContext(), this.getCardLayoutResource(), this);
    }

    protected abstract int getCardLayoutResource();
}
