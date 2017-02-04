package com.adrianlesniak.beautifulthailand.navigation;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.utilities.FragmentHelper;
import com.adrianlesniak.beautifulthailand.views.BTToolbar;

public class NavigationActivity extends AppCompatActivity implements NavigationFragment.OnNavigationItemClickListener {

    private BTToolbar mToolbar;

    private DrawerLayout mDrawerLayout;

    private NavigationFragment mNavigationFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        this.mToolbar = (BTToolbar) findViewById(R.id.toolbar);
        this.mToolbar.setOnToolbarActionListener(new BTToolbar.OnToolbarActionListener() {
            @Override
            public void onPrimaryToolbarButtonClicked() {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }

            @Override
            public void onSecondaryToolbarButtonClicked() {

            }
        });
        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mNavigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_fragment);
        this.mNavigationFragment.setOnNavigationItemClickedListener(this);

        NavigationListItemModel defaultFragment = NavigationListData.getInstance(this).get(0);
        NavigationListData.getInstance(this).setSelectedItem(defaultFragment);
        this.pushFragment(defaultFragment);
    }

    @Override
    public void onNavigationItemClicked(NavigationListItemModel item) {
        mDrawerLayout.closeDrawer(Gravity.LEFT);
        this.pushFragment(item);
    }

    private void pushFragment(NavigationListItemModel item) {
        this.mToolbar.setTitle(item.getTitle());
        FragmentHelper.pushFragment(this, getSupportFragmentManager(), item);
    }
}
