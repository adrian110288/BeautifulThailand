package com.adrianlesniak.beautifulthailand.screens.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.adrianlesniak.beautifulthailand.R;

/**
 * Created by adrian on 13/02/2017.
 */

public class BTPreferenceFragment extends PreferenceFragment {

    public static BTPreferenceFragment getInstance() {
        return new BTPreferenceFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
