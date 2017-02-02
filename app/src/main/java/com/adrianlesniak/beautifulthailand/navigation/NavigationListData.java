package com.adrianlesniak.beautifulthailand.navigation;

import android.content.Context;

import com.adrianlesniak.beautifulthailand.R;
import com.adrianlesniak.beautifulthailand.screens.FavouritesFragment;
import com.adrianlesniak.beautifulthailand.screens.home.HomeFragment;

import java.util.ArrayList;

/**
 * Created by adrian on 22/01/2017.
 */

public class NavigationListData extends ArrayList<NavigationListItemModel> {

    private static NavigationListData sInstance;

    private NavigationListItemModel mSelectedItem;

    public static NavigationListData getInstance(Context context) {
        if(sInstance == null) {
            sInstance = new NavigationListData(context);
        }

        return sInstance;
    }

    private NavigationListData(Context context) {

        NavigationListItemModel itemHome = new NavigationListItemModel(
                R.drawable.ic_local,
                context.getString(R.string.navigation_item_home),
                HomeFragment.class
        );

        NavigationListItemModel itemFavourite = new NavigationListItemModel(
                R.drawable.ic_heart_off,
                context.getString(R.string.navigation_item_favourite),
                FavouritesFragment.class
        );

        NavigationListItemModel itemToVisit = new NavigationListItemModel(
                R.drawable.ic_bookmark_off,
                context.getString(R.string.navigation_item_to_visit),
                null
        );

        NavigationListItemModel itemWeather = new NavigationListItemModel(
                R.drawable.ic_weather,
                context.getString(R.string.navigation_item_weather),
                null
//                WeatherActivity.class
        );

        NavigationListItemModel itemCurrency = new NavigationListItemModel(
                R.drawable.ic_currency,
                context.getString(R.string.navigation_item_currency),
                null
//                CurrencyActivity.class
        );

        NavigationListItemModel itemFood = new NavigationListItemModel(
                R.drawable.ic_food,
                context.getString(R.string.navigation_item_food),
                null
//                FoodActivity.class
        );

        NavigationListItemModel itemSettings = new NavigationListItemModel(
                R.drawable.ic_settings,
                context.getString(R.string.navigation_item_settings),
                null
//                SettingsActivity.class
        );

        this.add(itemHome);
        this.add(itemFavourite);
        this.add(itemToVisit);
        this.add(itemWeather);
        this.add(itemCurrency);
        this.add(itemFood);
        this.add(itemSettings);
    }

    public NavigationListData setSelectedItem(NavigationListItemModel selectedItem) {

        if(this.mSelectedItem != null) {
            this.mSelectedItem.setSelected(false);
        }

        this.mSelectedItem = selectedItem;
        this.mSelectedItem.setSelected(true);

        return this;
    }

    public NavigationListItemModel getSelectedItem() {
        return this.mSelectedItem;
    }
}
