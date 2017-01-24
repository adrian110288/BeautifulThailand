package com.adrianlesniak.beautifulthailand.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.ToolbarActivity;

/**
 * Created by adrian on 22/01/2017.
 */

public class NavigationFragment extends Fragment implements NavigationListAdapter.OnNavigationListItemClicked {

    private final int LAYOUT_RES = R.layout.fragment_navigation;

    public interface OnNavigationItemClickListener {
        void onNavigationItemClicked();
    }

    private RecyclerView mNavigationItemList;

    private NavigationListAdapter mAdapter;

    private NavigationListData mListData;

    private NavigationListItemModel mCurrentListItem;

    private OnNavigationItemClickListener mNavigationItemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mListData = new NavigationListData(getContext());

        this.mCurrentListItem = this.mListData.get(0);

        this.mAdapter = new NavigationListAdapter(this.mListData, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(LAYOUT_RES, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mNavigationItemList = (RecyclerView) view.findViewById(R.id.navigation_item_list);
        this.mNavigationItemList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

        this.mNavigationItemList.setAdapter(this.mAdapter);
    }

    public void setOnNavigationItemClickedListener(OnNavigationItemClickListener listener) {
        this.mNavigationItemClickListener = listener;
    }

    @Override
    public void onNavigationListItemClicked(NavigationListItemModel item) {

        if(item != null && item.getDestination() != null)  {

            if(item != this.mCurrentListItem) {

                this.mCurrentListItem = item;

                Intent navigationIntent = new Intent(getContext(), item.getDestination());

                Bundle activityBundle = new Bundle();
                activityBundle.putString(ToolbarActivity.BUNDLE_TITLE, item.getTitle());

                navigationIntent.putExtras(activityBundle);

                this.getActivity().startActivity(navigationIntent);
            }

            if(this.mNavigationItemClickListener != null) {
                this.mNavigationItemClickListener.onNavigationItemClicked();
            }
        }
    }
}
