package com.shruthiraju.airqualityindia;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

public class MainActivity extends AppCompatActivity implements allCitiesFragment.OnCitySelectedListener, myCityFragment.OnFragmentInteractionListener{

    private airQualityPagerAdapter aqAdapter;
    private ViewPager viewPager;


    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem() == 1){
            viewPager.setCurrentItem(0);
        } else {
            MainActivity.this.finish();
        }

    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }
    public void onCitySelected(Bundle bundle){
        // The user selected a city from allCitiesFragment
        myCityFragment myCityFrag = (myCityFragment)
                getSupportFragmentManager().findFragmentById(R.id.my_city_container);

        if (myCityFrag != null) {
            // If myCityFrag is available, we're in two-pane layout...

            // Call a method in the myCityFragment to update its content
            myCityFrag.updateArticleView(bundle);
        } else {
            // Otherwise, we're in the one-pane layout and must swap frags...
            // Create fragment and give it an argument for the selected city
            myCityFragment newFragment = new myCityFragment();
            newFragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack so the user can navigate back
            transaction.replace(R.id.my_city_container, newFragment);

            // Commit the transaction
            transaction.commit();

        }
        viewPager.setCurrentItem(1);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setElevation(0);

        myCityFragment.newInstance();

        //Initialize aqAdapter PAGER ADAPTER
        aqAdapter = new airQualityPagerAdapter(this,getSupportFragmentManager());

        //Declaring ViewPager, Setting up with TabLayout
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(aqAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);;
        tabLayout.setupWithViewPager(viewPager);

    }

}
