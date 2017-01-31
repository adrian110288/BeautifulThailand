package com.adrianlesniak.beautifulthailand.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;

/**
 * Created by adrian on 29/01/2017.
 */

public class TelephoneCard extends DetailsCard {

    private TextView mTelephoneNoTextView;

    public TelephoneCard(Context context) {
        this(context, null);
    }

    public TelephoneCard(Context context, AttributeSet attrs) {
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
