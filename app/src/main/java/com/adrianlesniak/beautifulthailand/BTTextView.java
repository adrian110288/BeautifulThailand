package com.adrianlesniak.beautifulthailand;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by adrian on 22/01/2017.
 */

public class BTTextView extends AppCompatTextView {

    public BTTextView(Context context) {
        this(context, null);
    }

    public BTTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BTTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BTTextView_attrs);

        try {

            if (a.hasValue(R.styleable.BTTextView_attrs_font_type)) {
                int fontIndex = a.getInt(R.styleable.BTTextView_attrs_font_type, 12);
                this.setTypeface(fontIndex);
            }

        } finally {
            a.recycle();
        }
    }

    public void setTypeface(int fontIndex) {

        if (!isInEditMode()) {
            super.setTypeface(FontCache.getFont(getContext(), fontIndex));
        }
    }
}
