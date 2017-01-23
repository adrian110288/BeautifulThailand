package com.adrianlesniak.beautifulthailand.navigation;

import android.content.Context;

import com.adrianlesniak.beautifulthailand.FavouritesActivity;
import com.adrianlesniak.beautifulthailand.HomeActivity;
import com.adrianlesniak.beautifulthailand.R;

import java.util.ArrayList;

/**
 * Created by adrian on 22/01/2017.
 */

public class NavigationListData extends ArrayList<NavigationListItem> {

    private NavigationListItem mSelectedItem;

    public NavigationListData(Context context) {

        NavigationListItem itemHome = new NavigationListItem(
                R.drawable.ic_local,
                context.getString(R.string.navigation_item_home),
                HomeActivity.class,
                true
        );

        NavigationListItem itemFavourite = new NavigationListItem(
                R.drawable.ic_heart_off,
                context.getString(R.string.navigation_item_favourite),
                FavouritesActivity.class,
                false
        );

        NavigationListItem itemToVisit = new NavigationListItem(
                R.drawable.ic_bookmark,
                context.getString(R.string.navigation_item_to_visit),
                null,
                false
//                FavouritesActivity.class
        );

        NavigationListItem itemWeather = new NavigationListItem(
                R.drawable.ic_weather,
                context.getString(R.string.navigation_item_weather),
                null,
                false
//                WeatherActivity.class
        );

        NavigationListItem itemCurrency = new NavigationListItem(
                R.drawable.ic_currency,
                context.getString(R.string.navigation_item_currency),
                null,
                false
//                CurrencyActivity.class
        );

        NavigationListItem itemFood = new NavigationListItem(
                R.drawable.ic_food,
                context.getString(R.string.navigation_item_food),
                null,
                false
//                FoodActivity.class
        );

        NavigationListItem itemSettings = new NavigationListItem(
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

    public void setSelectedItem(NavigationListItem selectedItem) {

        this.mSelectedItem = selectedItem;

        for (int index = 0; index < this.size(); index++) {
            this.get(index).setSelected(this.get(index) == selectedItem);
        }
    }
}
