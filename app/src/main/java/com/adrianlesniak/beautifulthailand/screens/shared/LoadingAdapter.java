package com.adrianlesniak.beautifulthailand.screens.shared;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.R;

/**
 * Created by adrian on 02/02/2017.
 */

public class LoadingAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public LoadingAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(this.mContext).inflate(R.layout.list_item_loading, parent, false);
        return new LoadingViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) { }

    @Override
    public int getItemCount() {
        return 1;
    }

    private static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

}
