package com.adrianlesniak.beautifulthailand.navigation;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.screens.nearby.NearbyFragment;
import com.adrianlesniak.beautifulthailand.utilities.FragmentHelper;

public class NavigationActivity extends AppCompatActivity implements NavigationFragment.OnNavigationItemClickListener {

//    private BTToolbar mToolbar;

    private DrawerLayout mDrawerLayout;

    private NavigationFragment mNavigationFragment;

    private NavigationListItemModel mCurrentSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        this.mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.mNavigationFragment = (NavigationFragment) getSupportFragmentManager().findFragmentById(R.id.navigation_fragment);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.mCurrentSelectedItem = NavigationListData.getInstance(this).getItemByClass(NearbyFragment.class);
        FragmentHelper.pushFragment(this, getSupportFragmentManager(), this.mCurrentSelectedItem);


        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            return;
        }
    }

    @Override
    public void onNavigationItemClicked(NavigationListItemModel item) {
        this.mDrawerLayout.closeDrawer(Gravity.LEFT);

        if(this.mCurrentSelectedItem != item) {
            this.mCurrentSelectedItem = item;
            FragmentHelper.pushFragment(this, getSupportFragmentManager(), item);
        }
    }

    public void onFragmentAttached(Class fragmentClass) {

        this.mCurrentSelectedItem = NavigationListData.getInstance(this).getItemByClass(fragmentClass);

        int itemIndex = NavigationListData.getInstance(this).indexOf(this.mCurrentSelectedItem);
        this.mNavigationFragment.selectItemAtPosition(itemIndex);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }
}
