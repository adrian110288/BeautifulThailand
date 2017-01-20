package com.adrianlesniak.beautifulthailand;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private BTToolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mDrawerLayout = (DrawerLayout) this.findViewById(R.id.drawer_layout);
        mToolbar = (BTToolbar) this.findViewById(R.id.toolbar);
        mToolbar.setOnToolbarActionListener(new BTToolbar.OnToolbarActionListener() {
            @Override
            public void onPrimaryButtonClicked() {
                mDrawerLayout.openDrawer(Gravity.LEFT);

            }

            @Override
            public void onSecondaryButtonClicked() {

            }
        });
    }

}
