package com.adrianlesniak.beautifulthailand.models;

/**
 * Created by adrian on 23/01/2017.
 */

public class EmptyListItem implements ListItem {

    private int mIconRes;

    private String mTitle;

    public EmptyListItem(int iconRes, String title) {
        this.mIconRes = iconRes;
        this.mTitle = title;
    }

    public int getIconRes() {
        return this.mIconRes;
    }

    public String getTitle() {
        return this.mTitle;
    }
}
