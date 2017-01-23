package com.adrianlesniak.beautifulthailand;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * Created by adrian on 20/01/2017.
 */

public class BTToolbar extends Toolbar {

    public interface OnToolbarActionListener {
        void onPrimaryToolbarButtonClicked();

        void onSecondaryToolbarButtonClicked();
    }

    private static final int LAYOUT_REF = R.layout.bt_toolbar;

    private OnToolbarActionListener mOnToolbarActionListener;

    private ImageButton mPrimaryButton;

    private int mPrimaryIcon;

    private ImageButton mSecondaryButton;

    private int mSecondaryIcon;

    private TextView mTitle;

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

        this.mPrimaryButton = (ImageButton) view.findViewById(R.id.toolbar_primary_button);
        this.mSecondaryButton = (ImageButton) view.findViewById(R.id.toolbar_secondary_button);
        this.setupToolbarButtons();

        this.mTitle = (BTTextView) view.findViewById(R.id.toolbar_title);
    }

    private void setupToolbarButtons() {

        if(this.mPrimaryIcon == -1) {
            this.mPrimaryButton.setVisibility(View.GONE);

        } else {
            mPrimaryButton.setImageResource(mPrimaryIcon);

            mPrimaryButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnToolbarActionListener != null) {
                        mOnToolbarActionListener.onPrimaryToolbarButtonClicked();
                    }
                }
            });
        }

        if(this.mSecondaryIcon == -1) {
            this.mSecondaryButton.setVisibility(View.GONE);

        } else {
            mSecondaryButton.setImageResource(mSecondaryIcon);

            mSecondaryButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnToolbarActionListener != null) {
                        mOnToolbarActionListener.onSecondaryToolbarButtonClicked();
                    }
                }
            });
        }
    }

    public void setOnToolbarActionListener(OnToolbarActionListener listener) {
        mOnToolbarActionListener = listener;
    }

    public void setTitle(String title) {
        this.mTitle.setText(title);
    }

}
