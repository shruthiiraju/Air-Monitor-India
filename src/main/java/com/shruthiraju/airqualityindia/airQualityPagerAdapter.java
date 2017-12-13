package com.shruthiraju.airqualityindia;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


// Created by Shruthi Raju on 9/25/2017

public class airQualityPagerAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;
    private Context context;


    public airQualityPagerAdapter(Context conText, FragmentManager fm) {
        super(fm);
        context = conText;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){

            return new allCitiesFragment();
        } else {
            return new myCityFragment();
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return context.getString(R.string.all_cities_string);
        }
        else {
            return context.getString(R.string.my_city_string);
        }
    }
}
