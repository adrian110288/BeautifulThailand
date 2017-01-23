package com.adrianlesniak.beautifulthailand;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by adrian on 23/01/2017.
 */

public class CloseableActivity extends ToolbarActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPrimaryToolbarButtonClicked() {
        super.onPrimaryToolbarButtonClicked();

        finish();
    }
}
