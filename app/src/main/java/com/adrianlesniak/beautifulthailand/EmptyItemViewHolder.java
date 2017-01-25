package com.adrianlesniak.beautifulthailand;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.models.EmptyListItem;

/**
 * Created by adrian on 25/01/2017.
 */

public class EmptyItemViewHolder extends RecyclerView.ViewHolder {

    private ImageView mIconView;

    private TextView mTitleView;

    public EmptyItemViewHolder(View itemView) {
        super(itemView);

        this.mIconView = (ImageView) itemView.findViewById(R.id.empty_list_item_icon);
        this.mTitleView = (TextView) itemView.findViewById(R.id.empty_list_item_title);
    }

    public void bindData(EmptyListItem data) {
        this.mIconView.setImageResource(data.getIconRes());
        this.mTitleView.setText(data.getTitle());
    }
}
