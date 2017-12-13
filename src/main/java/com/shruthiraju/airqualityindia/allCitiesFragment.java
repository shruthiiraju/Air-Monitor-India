package com.shruthiraju.airqualityindia;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.support.v4.app.ListFragment;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;
import java.util.List;
import static com.shruthiraju.airqualityindia.QueryUtils.fetchAirQualityData;
import static com.shruthiraju.airqualityindia.cityAirQuality2.getSpecPollutant;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link allCitiesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class allCitiesFragment extends ListFragment implements LoaderManager.LoaderCallbacks<List<cityAirQuality2>>{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private OnCitySelectedListener mCallback;
    private View rootView;
    private TextView emptyState;
    private ListView listView;
    private SwipeRefreshLayout mySwipeRefreshLayout;
    public static Bundle bundle;
    public static int flag = 0;


    public allCitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        this.setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getLoaderManager().initLoader(0,null,allCitiesFragment.this).forceLoad();

        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_all_cities, container, false);
        bundle = new Bundle();
        bundle.putBoolean("NetworkStatus",getConnection(getContext()));

        mySwipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);
        mySwipeRefreshLayout.setColorSchemeResources(
                R.color.refresh_progress_1,
                R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        getLoaderManager().initLoader(0,null,allCitiesFragment.this).forceLoad();
                    }
                }
        );
        emptyState = rootView.findViewById(R.id.empty_state);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnCitySelectedListener {
        void onCitySelected(Bundle bundle);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // Send the event to the host activity

        cityAirQuality2 currentCity = (cityAirQuality2) l.getItemAtPosition(position);
        bundle.putString("CITY", currentCity.getmCity());
        bundle.putString("STATE", currentCity.getmState());
        bundle.putString("STATION", currentCity.getmStation());
        bundle.putString("WHATVALUE","What do these values mean?");
        String no2 = getSpecPollutant((currentCity.getmStation()),"NO2");
        String pm25 = getSpecPollutant((currentCity.getmStation()),"PM2.5");
        String pm10 = getSpecPollutant((currentCity.getmStation()),"PM10");
        String so2 = getSpecPollutant((currentCity.getmStation()),"SO2");
        String co = currentCity.getSpecPollutant((currentCity.getmStation()),"CO");
        String o3 = currentCity.getSpecPollutant((currentCity.getmStation()),"O3");

        bundle.putString("NO2", no2);
        bundle.putString("PM25", pm25);
        bundle.putString("PM10", pm10);
        bundle.putString("SO2", so2);
        bundle.putString("CO", co);
        bundle.putString("O3", o3);

        mCallback.onCitySelected(bundle);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCitySelectedListener) {
            mCallback = (OnCitySelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCitySelectedListener");
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *
     */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public Loader<List<cityAirQuality2>> onCreateLoader(int id, Bundle args) {
        return new airQualityLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<List<cityAirQuality2>> loader, List<cityAirQuality2> data) {
        mySwipeRefreshLayout.setRefreshing(false);
        updateAllCitiesUI(getContext(),data);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.options_menu, menu);
        MenuItem item = menu.findItem(R.id.search);
        MenuItem refresh = menu.findItem(R.id.menu_refresh);
        refresh.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                updateAllCitiesUI(getContext(), cityAirQuality2.getPM25Array(cityAirQuality2.everything));
                return true;
            }
        });
        SearchView sv = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        sv.setQueryHint("Enter state...");
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, sv);
        sv.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                updateAllCitiesUI(getContext(),cityAirQuality2.getSearchResult(query));
                flag = 1;
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        sv.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(getContext(), "HEY", Toast.LENGTH_LONG).show();
                return false;
            }
        });


    }


    public void updateAllCitiesUI(Context context, List<cityAirQuality2> data){

        List<cityAirQuality2> Uniquecities = cityAirQuality2.getPM25Array(data);

        listView = getListView();
        listView.setEmptyView(emptyState);

        if(getConnection(context)|| data.size() == 0) {
            listView.setVisibility(View.GONE);
            emptyState.setText("Data from Central Pollution Control Board India is unavailable right now. Sorry for the inconvenience.");
        } else {
            emptyState.setText("No internet connection.");
        }

        //Making Progress Bar Disappear
        ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

       /* //Removing Duplicates
        Set<cityAirQuality2> hs = new LinkedHashSet<>(data);  //Set doesn't allow duplicates
        Uniquecities.clear();
        Uniquecities.addAll(hs);*/


        allCitiesAdapter citiesAdapter = new allCitiesAdapter(context, Uniquecities);

        listView.setAdapter(citiesAdapter);


    }

    public static boolean getConnection(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

            // This method performs the actual data-refresh operation.
            // The method calls setRefreshing(false) when it's finished.


    @Override
    public void onLoaderReset(Loader<List<cityAirQuality2>> loader) {

    }


    public static class airQualityLoader extends AsyncTaskLoader<List<cityAirQuality2>> {

        private String requestURL = "https://api.data.gov.in/resource/3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69?format=json&api-key=579b464db66ec23bdd0000011776e3d489df457b79860a24f0fe6bba&filters[country]=India&fields=city,state,pollutant_id,pollutant_avg,pollutant_min,pollutant_max,station&sort[city]=asc&limit=415";

        @Override
        public List<cityAirQuality2> loadInBackground() {
            List<cityAirQuality2> cities = null;
            try{
                cities = fetchAirQualityData(requestURL);
            } catch (Error e){}
            return cities;
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }

        public airQualityLoader(Context context) {
            super(context);
        }
    }
}
