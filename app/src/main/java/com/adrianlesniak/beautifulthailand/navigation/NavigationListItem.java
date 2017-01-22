package com.adrianlesniak.beautifulthailand.navigation;

import android.support.annotation.DrawableRes;

/**
 * Created by adrian on 22/01/2017.
 */

public class NavigationListItem {

    @DrawableRes
    private int mIconRes;

    private String mTitle;

    private Class mDestination;

    private boolean mSelected;

    public NavigationListItem(int iconRes, String title, Class destination, boolean selected) {
        this.mIconRes = iconRes;
        this.mTitle = title;
        this.mDestination = destination;
        this.mSelected = selected;
    }

    public int getIcon() {
        return this.mIconRes;
    }

    public String getTitle() {
        return this.mTitle;
    }

    public Class getDestination() {
        return this.mDestination;
    }

    public void setSelected(boolean selected) {
        this.mSelected = selected;
    }

    public boolean isSelected() {
        return this.mSelected;
    }
}
