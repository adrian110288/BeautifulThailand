package com.adrianlesniak.beautifulthailand.models;

/**
 * Created by adrian on 23/01/2017.
 */

public class EmptyItemModel implements ListModel {

    private int mIconRes;

    private String mTitle;

    public EmptyItemModel(int iconRes, String title) {
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
