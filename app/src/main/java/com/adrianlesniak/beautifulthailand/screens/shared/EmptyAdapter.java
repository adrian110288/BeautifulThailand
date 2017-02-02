package com.adrianlesniak.beautifulthailand.screens.shared;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;

/**
 * Created by adrian on 02/02/2017.
 */

public class EmptyAdapter extends RecyclerView.Adapter<EmptyAdapter.EmptyViewHolder> {

    private Context mContext;

    private String mMessage;

    public EmptyAdapter(Context context, String message) {
        this.mContext = context;
        this.mMessage = message;
    }

    @Override
    public EmptyAdapter.EmptyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(this.mContext).inflate(R.layout.list_item_empty, parent, false);
        return new EmptyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(EmptyAdapter.EmptyViewHolder holder, int position) {
        holder.messageView.setText(this.mMessage);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public static class EmptyViewHolder extends RecyclerView.ViewHolder {

        public TextView messageView;

        public EmptyViewHolder(View itemView) {
            super(itemView);
            this.messageView = (TextView) itemView.findViewById(R.id.message_view);
        }
    }
}
