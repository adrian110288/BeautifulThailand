package com.adrianlesniak.beautifulthailand.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;

/**
 * Created by adrian on 20/01/2017.
 */

public class BTToolbar extends Toolbar {

    public interface OnToolbarButtonClickListener {
        void onToolbarNavigationButtonClick();

        void onToolbarActionButtonClick();
    }

    private static final int LAYOUT_REF = R.layout.view_toolbar;

    private OnToolbarButtonClickListener mOnToolbarButtonClickListener;

    private ImageButton mNavigationButton;

    private int mNavigationIcon;

    private ImageButton mActionButton;

    private int mActionIcon;

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

            mNavigationIcon = a.getResourceId(R.styleable.BTToolbar_attrs_navigation_icon, -1);
            mActionIcon = a.getResourceId(R.styleable.BTToolbar_attrs_action_icon, -1);

        } finally {
            a.recycle();
        }

        setup();
    }

    protected void setup() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(LAYOUT_REF, this);

        this.mNavigationButton = (ImageButton) view.findViewById(R.id.toolbar_navigation_button);
        this.mActionButton = (ImageButton) view.findViewById(R.id.toolbar_action_button);
        this.setupToolbarButtons();

        this.mTitle = (BTTextView) view.findViewById(R.id.toolbar_title);
    }

    private void setupToolbarButtons() {

        if(this.mNavigationIcon == -1) {
            this.mNavigationButton.setVisibility(View.GONE);

        } else {
            mNavigationButton.setImageResource(mNavigationIcon);

            mNavigationButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnToolbarButtonClickListener != null) {
                        mOnToolbarButtonClickListener.onToolbarNavigationButtonClick();
                    }
                }
            });
        }

        if(this.mActionIcon == -1) {
            this.mActionButton.setVisibility(View.GONE);

        } else {
            mActionButton.setImageResource(mActionIcon);

            mActionButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnToolbarButtonClickListener != null) {
                        mOnToolbarButtonClickListener.onToolbarActionButtonClick();
                    }
                }
            });
        }
    }

    public void setOnToolbarButtonClickListener(OnToolbarButtonClickListener listener) {
        mOnToolbarButtonClickListener = listener;
    }

    public void setTitle(String title) {
        this.mTitle.setText(title);
    }

}
