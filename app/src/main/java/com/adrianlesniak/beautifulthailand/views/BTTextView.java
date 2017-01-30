package com.adrianlesniak.beautifulthailand.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.utilities.FontCache;

/**
 * Created by adrian on 22/01/2017.
 */

public class BTTextView extends AppCompatTextView {

    private int mFontIndex;

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
            this.mFontIndex = a.getInt(R.styleable.BTTextView_attrs_font_type, 12);

        } finally {
            a.recycle();
        }

        this.setTypeface(this.mFontIndex);
    }

    public void setTypeface(int fontIndex) {

        this.mFontIndex = fontIndex;

        if (!isInEditMode()) {
            super.setTypeface(FontCache.getFont(getContext(), fontIndex));
        }
    }
}
