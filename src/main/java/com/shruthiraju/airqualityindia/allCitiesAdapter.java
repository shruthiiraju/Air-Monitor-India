package com.shruthiraju.airqualityindia;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;


// Created by Shruthi Raju on 9/29/2017.


public class allCitiesAdapter extends ArrayAdapter<cityAirQuality2> {
    public allCitiesAdapter(@NonNull Context context, @NonNull List<cityAirQuality2> objects) {
        super(context, 0, objects);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        //Get current city, getItem is a func of the adapter
        cityAirQuality2 currentCity = getItem(position);
        TextView addressTextView = convertView.findViewById(R.id.city_text_view);
        TextView stateTextView = convertView.findViewById(R.id.state_text_view);

        String pm25 = currentCity.getmPollutantAvg();
        TextView descriptor = convertView.findViewById(R.id.descriptor_text_view);
        descriptor.setText(currentCity.getPM25Descriptive(pm25));


        descriptor.setTextColor(ContextCompat.getColor(getContext(),currentCity.getPM25Color(pm25)));

        addressTextView.setText(currentCity.getAddress());
        stateTextView.setText(currentCity.getmState());


        return convertView;
    }

}
