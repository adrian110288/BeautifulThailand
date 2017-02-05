package com.adrianlesniak.beautifulthailand.screens.shared;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.adrianlesniak.beautifulthailand.navigation.NavigationActivity;

/**
 * Created by adrian on 04/02/2017.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onResume() {
        super.onResume();

        ((NavigationActivity) getContext()).onFragmentAttached(getClass());
    }
}
