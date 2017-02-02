package com.adrianlesniak.beautifulthailand.utilities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.adrianlesniak.beautifulthailand.R;

/**
 * Created by adrian on 01/02/2017.
 */

public class FragmentHelper {

    public static void pushFragment(FragmentManager fragmentManager, Fragment fragment) {
        fragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }
}
