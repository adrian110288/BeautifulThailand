package com.adrianlesniak.beautifulthailand.views;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;

/**
 * Created by adrian on 29/01/2017.
 */

public class TelephoneCardView extends DetailCardView {

    private TextView mTelephoneNoTextView;

    public TelephoneCardView(Context context) {
        this(context, null);
    }

    public TelephoneCardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mTelephoneNoTextView = (TextView) findViewById(R.id.card_telephone_view);
    }

    @Override
    protected int getCardLayoutResource() {
        return R.layout.card_telephone;
    }

    public void setTelephoneNo(String telNo) {
        this.mTelephoneNoTextView.setText(telNo);
    }
}
