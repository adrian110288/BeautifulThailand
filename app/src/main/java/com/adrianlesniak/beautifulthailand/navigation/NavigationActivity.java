package com.adrianlesniak.beautifulthailand.navigation;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.screens.home.HomeFragment;
import com.adrianlesniak.beautifulthailand.utilities.FragmentHelper;

public class NavigationActivity extends AppCompatActivity implements NavigationFragment.OnNavigationItemClickListener {

    private DrawerLayout mDrawerLayout;

    private NavigationFragment mNavigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentHelper.pushFragment(getSupportFragmentManager(), new HomeFragment());
    }

    @Override
    public void onNavigationItemClicked() {
        this.mDrawerLayout.closeDrawer(Gravity.LEFT);
    }


}
