package com.shruthiraju.airqualityindia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MeaningActivity extends AppCompatActivity {

    private myCityFragment myCityFragment = new myCityFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meaning);

        TextView no2Title = (TextView) findViewById(R.id.no2_title);
        TextView so2Title = (TextView) findViewById(R.id.so2_title);
        TextView o3Title = (TextView) findViewById(R.id.o3_title);

        myCityFragment.setSubscript("NO2",no2Title,2,3);
        myCityFragment.setSubscript("O3",o3Title,1,2);
        myCityFragment.setSubscript("SO2",so2Title,2,3);


    }
}
