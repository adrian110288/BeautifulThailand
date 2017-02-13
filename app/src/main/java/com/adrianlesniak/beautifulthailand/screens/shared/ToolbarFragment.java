package com.adrianlesniak.beautifulthailand.screens.shared;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.navigation.NavigationActivity;
import com.adrianlesniak.beautifulthailand.views.BTToolbar;

/**
 * Created by adrian on 13/02/2017.
 */

public class ToolbarFragment extends BaseFragment implements BTToolbar.OnToolbarButtonClickListener {

    public static final String BUNDLE_TITLE = "bundle_title";

    protected BTToolbar mToolbar;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mToolbar = (BTToolbar) view.findViewById(R.id.toolbar);
        this.mToolbar.setOnToolbarButtonClickListener(this);
        this.mToolbar.setTitle(getArguments().getString(BUNDLE_TITLE));
    }

    @Override
    public void onToolbarNavigationButtonClick() {
        if(getActivity() instanceof NavigationActivity) {
            ((NavigationActivity) getActivity()).openDrawer();
        }
    }

    @Override
    public void onToolbarActionButtonClick() {

    }
}
