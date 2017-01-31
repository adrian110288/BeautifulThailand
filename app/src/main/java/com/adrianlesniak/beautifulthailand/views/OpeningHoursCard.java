package com.adrianlesniak.beautifulthailand.views;

import android.content.Context;
import android.content.res.ColorStateList;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;
import com.google.maps.model.OpeningHours;

/**
 * Created by adrian on 31/01/2017.
 */

public class OpeningHoursCard extends DetailsCard {

    private TextView mOpenNowView;

    public OpeningHoursCard(Context context) {
        super(context);

        this.mOpenNowView = (TextView) findViewById(R.id.open_now_view);
    }

    @Override
    protected int getCardLayoutResource() {
        return R.layout.card_opening_hours;
    }

    public void setOpenNow(boolean openNow) {
        this.mOpenNowView.setText(openNow? "OPEN NOW" : "CLOSE");

        ColorStateList stateList = getContext().getResources().getColorStateList(openNow? R.color.state_list_open_now_icon : R.color.state_list_close_icon, null);

        this.mOpenNowView.setTextColor(stateList);
        this.mIcon.setImageTintList(stateList);
    }

    public void setOpeningTimes(OpeningHours openingHours) {

    }
}
