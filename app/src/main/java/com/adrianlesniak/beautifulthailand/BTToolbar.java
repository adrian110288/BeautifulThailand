package com.adrianlesniak.beautifulthailand;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by adrian on 20/01/2017.
 */

public class BTToolbar extends Toolbar {

    public static interface OnToolbarActionListener {
        void onPrimaryButtonClicked();

        void onSecondaryButtonClicked();
    }

    private static final int LAYOUT_REF = R.layout.bt_toolbar;

    private OnToolbarActionListener mOnToolbarActionListener;

    private ImageButton mPrimaryButton;

    private int mPrimaryIcon;

    private ImageButton mSecondaryButton;

    private int mSecondaryIcon;

    public BTToolbar(Context context) {
        this(context, null);
    }

    public BTToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BTToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BTToolbar_attrs);
        try {

            mPrimaryIcon = a.getResourceId(R.styleable.BTToolbar_attrs_primary_icon, -1);
            mSecondaryIcon = a.getResourceId(R.styleable.BTToolbar_attrs_secondary_icon, -1);

        } finally {
            a.recycle();
        }

        setup();
    }

    protected void setup() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(LAYOUT_REF, this);

        mPrimaryButton = (ImageButton) view.findViewById(R.id.primary_button);

        mPrimaryButton.setImageResource(mPrimaryIcon);

        mPrimaryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnToolbarActionListener != null) {
                    mOnToolbarActionListener.onPrimaryButtonClicked();
                }
            }
        });

        mSecondaryButton = (ImageButton) view.findViewById(R.id.secondary_button);

        mSecondaryButton.setImageResource(mSecondaryIcon);

        mSecondaryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnToolbarActionListener != null) {
                    mOnToolbarActionListener.onSecondaryButtonClicked();
                }
            }
        });

    }

    public void setOnToolbarActionListener(OnToolbarActionListener listener) {
        mOnToolbarActionListener = listener;
    }

}
