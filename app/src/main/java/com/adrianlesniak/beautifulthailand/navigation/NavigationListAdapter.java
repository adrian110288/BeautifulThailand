package com.adrianlesniak.beautifulthailand.navigation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adrianlesniak.beautifulthailand.R;

/**
 * Created by adrian on 22/01/2017.
 */

public class NavigationListAdapter extends RecyclerView.Adapter<NavigationListAdapter.NavigationItemViewHolder> {

    public interface OnNavigationListItemClicked {
        void onNavigationListItemClicked(NavigationListItemModel item);
    }

    private NavigationListData mListData;

    private OnNavigationListItemClicked mListener;

    public NavigationListAdapter(NavigationListData listData, OnNavigationListItemClicked listener) {

        this.mListData = listData;
        this.mListener = listener;
    }

    @Override
    public NavigationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_list_item, parent, false);

        NavigationItemViewHolder vh = new NavigationItemViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(NavigationItemViewHolder holder, int position) {

        final NavigationListItemModel itemData = this.mListData.get(position);

        holder.bindData(itemData);
        holder.getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mListener != null) {

                    mListData.setSelectedItem(itemData);
                    notifyDataSetChanged();

                    mListener.onNavigationListItemClicked(itemData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.mListData == null? 0 : this.mListData.size();
    }

    public static class NavigationItemViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private TextView mNavigationItemTitleView;

        private ImageView mNavigationItemIcon;

        public NavigationItemViewHolder(View itemView) {
            super(itemView);

            this.mView = itemView;

            this.mNavigationItemTitleView = (TextView) itemView.findViewById(R.id.navigation_item_title);

            this.mNavigationItemIcon = (ImageView) itemView.findViewById(R.id.navigation_item_icon);
        }

        public void bindData(NavigationListItemModel data) {

            this.mView.setSelected(data.isSelected());

            this.mNavigationItemTitleView.setText(data.getTitle());

            this.mNavigationItemIcon.setImageResource(data.getIcon());
        }

        public View getView() {
            return this.mView;
        }

    }
}
