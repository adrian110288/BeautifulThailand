package com.adrianlesniak.beautifulthailand.utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.navigation.NavigationListItemModel;

/**
 * Created by adrian on 01/02/2017.
 */

public class FragmentHelper {

    public static final String BUNDLE_TITLE = "bundle_title";

    public static void pushFragment(Context context, FragmentManager fragmentManager, NavigationListItemModel item) {

        Bundle fragmentBundle = new Bundle();
        fragmentBundle.putString(BUNDLE_TITLE, item.getTitle());

        try {
            Fragment f = (Fragment) item.getDestination().newInstance();
            f.setArguments(fragmentBundle);

            fragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container, f)
                    .commit();


        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
