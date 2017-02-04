package com.adrianlesniak.beautifulthailand.screens.details;

import android.content.Context;
import android.content.res.ColorStateList;
import android.util.AttributeSet;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.models.OpeningHours;

/**
 * Created by adrian on 31/01/2017.
 */

public class OpeningHoursCard extends DetailsCard {

    private TextView mOpenNowView;

    public OpeningHoursCard(Context context) {
        this(context, null);
    }

    public OpeningHoursCard(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mOpenNowView = (TextView) findViewById(R.id.open_now_view);
    }

    @Override
    protected int getCardLayoutResource() {
        return R.layout.card_opening_hours;
    }

    public void setOpeningHours(OpeningHours openingHours) {

        if(openingHours != null) {
            this.mOpenNowView.setText(openingHours.openNow? "OPEN NOW" : "CLOSE");

            ColorStateList stateList = getContext().getResources().getColorStateList(openingHours.openNow? R.color.state_list_open_now_icon : R.color.state_list_close_icon, null);

            this.mOpenNowView.setTextColor(stateList);
            this.mIcon.setImageTintList(stateList);
        }


    }

    public void setOpeningTimes(OpeningHours openingHours) {

    }
}
