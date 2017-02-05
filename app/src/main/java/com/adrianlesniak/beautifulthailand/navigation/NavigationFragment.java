package com.adrianlesniak.beautifulthailand.navigation;

import android.content.Context;
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
        void onNavigationItemClicked(NavigationListItemModel item);
    }

    private RecyclerView mNavigationItemList;

    private NavigationListAdapter mAdapter;

    private OnNavigationItemClickListener mNavigationItemClickListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            this.mNavigationItemClickListener = (OnNavigationItemClickListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException("NavigationActivity need to implement OnNavigationItemClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.mAdapter = new NavigationListAdapter(NavigationListData.getInstance(getContext()), this);
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

    @Override
    public void onNavigationListItemClicked(NavigationListItemModel item) {

        if(this.mNavigationItemClickListener != null) {
            this.mNavigationItemClickListener.onNavigationItemClicked(item);
        }
    }

    public void selectItemAtPosition(int positionToSelect) {
        this.mAdapter.selectItemAtPosition(positionToSelect);
    }
}
