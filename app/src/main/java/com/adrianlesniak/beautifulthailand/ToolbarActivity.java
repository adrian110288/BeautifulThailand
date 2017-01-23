package com.adrianlesniak.beautifulthailand;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.adrianlesniak.beautifulthailand.views.BTToolbar;

/**
 * Created by adrian on 23/01/2017.
 */

public abstract class ToolbarActivity extends AppCompatActivity implements BTToolbar.OnToolbarActionListener {

    public static final String BUNDLE_TITLE = "title";

    protected BTToolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void setup() {
        this.mToolbar = (BTToolbar) this.findViewById(R.id.toolbar);

        String toolbarTitle = getIntent().getStringExtra(BUNDLE_TITLE);
        this.mToolbar.setTitle(toolbarTitle);

        this.mToolbar.setOnToolbarActionListener(this);
    }

    @Override
    public void onPrimaryToolbarButtonClicked() { }

    @Override
    public void onSecondaryToolbarButtonClicked() { }

}
