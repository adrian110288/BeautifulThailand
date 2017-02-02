package com.adrianlesniak.beautifulthailand.navigation;

import android.content.Context;

import com.adrianlesniak.beautifulthailand.R;

import java.util.ArrayList;

/**
 * Created by adrian on 22/01/2017.
 */

public class NavigationListData extends ArrayList<NavigationListItemModel> {

    private NavigationListItemModel mSelectedItem;

    public NavigationListData(Context context) {

        NavigationListItemModel itemHome = new NavigationListItemModel(
                R.drawable.ic_local,
                context.getString(R.string.navigation_item_home),
                NavigationActivity.class,
                true
        );

        NavigationListItemModel itemFavourite = new NavigationListItemModel(
                R.drawable.ic_heart_off,
                context.getString(R.string.navigation_item_favourite),
                null,
                false
        );

        NavigationListItemModel itemToVisit = new NavigationListItemModel(
                R.drawable.ic_bookmark_off,
                context.getString(R.string.navigation_item_to_visit),
                null,
                false
        );

        NavigationListItemModel itemWeather = new NavigationListItemModel(
                R.drawable.ic_weather,
                context.getString(R.string.navigation_item_weather),
                null,
                false
//                WeatherActivity.class
        );

        NavigationListItemModel itemCurrency = new NavigationListItemModel(
                R.drawable.ic_currency,
                context.getString(R.string.navigation_item_currency),
                null,
                false
//                CurrencyActivity.class
        );

        NavigationListItemModel itemFood = new NavigationListItemModel(
                R.drawable.ic_food,
                context.getString(R.string.navigation_item_food),
                null,
                false
//                FoodActivity.class
        );

        NavigationListItemModel itemSettings = new NavigationListItemModel(
                R.drawable.ic_settings,
                context.getString(R.string.navigation_item_settings),
                null,
                false
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

    public void setSelectedItem(NavigationListItemModel selectedItem) {

        this.mSelectedItem = selectedItem;

        for (int index = 0; index < this.size(); index++) {
            this.get(index).setSelected(this.get(index) == selectedItem);
        }
    }
}
