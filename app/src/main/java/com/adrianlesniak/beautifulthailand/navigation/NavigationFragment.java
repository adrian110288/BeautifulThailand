package com.adrianlesniak.beautifulthailand.navigation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adrianlesniak.beautifulthailand.R;

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

    private OnNavigationItemClickListener mNavigationItemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mListData = new NavigationListData(getContext());

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
    }
}
