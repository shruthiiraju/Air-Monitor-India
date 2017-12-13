package com.shruthiraju.airqualityindia;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.text.style.SubscriptSpan;
import android.widget.TextView.BufferType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link myCityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link myCityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class myCityFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    private TextView stationTextView;
    private TextView addressTextView;


    private TextView pm25circle;
    private TextView no2Circle;
    private TextView pm10circle;
    private TextView so2Circle;
    private TextView coCircle;
    private TextView o3Circle;

    private GradientDrawable no2Drawable;
    private GradientDrawable pm25Drawable;
    private GradientDrawable pm10Drawable;
    private GradientDrawable coDrawable;
    private GradientDrawable o3Drawable;
    private GradientDrawable so2Drawable;

    private TextView emptyState;
    private View rootView;
    private View frameLayout;
    private TextView whatTextView;


    private Bundle bundle;

    public myCityFragment() {
        // Required empty public constructor
    }

    public static myCityFragment newInstance() {
        myCityFragment fragment = new myCityFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        this.setRetainInstance(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        rootView =  inflater.inflate(R.layout.fragment_my_city, container, false);
        bundle = getArguments();
        frameLayout = rootView.findViewById(R.id.what);
        stationTextView = rootView.findViewById(R.id.station_text_view);
        addressTextView = rootView.findViewById(R.id.address_text_view);

        whatTextView = rootView.findViewById(R.id.what_text_view);
        whatTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MeaningActivity.class);
                startActivity(intent);

            }
        });

        no2Circle = rootView.findViewById(R.id.no2_circle);
        pm25circle = rootView.findViewById(R.id.pm25_circle);
        pm10circle = rootView.findViewById(R.id.pm10_circle);
        so2Circle = rootView.findViewById(R.id.so2_circle);
        coCircle = rootView.findViewById(R.id.co_circle);
        o3Circle = rootView.findViewById(R.id.o3_circle);

        no2Drawable = (GradientDrawable) no2Circle.getBackground();
        pm25Drawable = (GradientDrawable) pm25circle.getBackground();
        pm10Drawable = (GradientDrawable) pm10circle.getBackground();
        o3Drawable = (GradientDrawable) o3Circle.getBackground();
        so2Drawable = (GradientDrawable) so2Circle.getBackground();
        coDrawable = (GradientDrawable) coCircle.getBackground();

        emptyState = rootView.findViewById(R.id.empty_state);

        updateArticleView(bundle);

        TextView pm25TextView = rootView.findViewById(R.id.pm25_tv);
        TextView coTextView = rootView.findViewById(R.id.co_tv);
        TextView no2TextView = rootView.findViewById(R.id.no2_tv);
        TextView o3TextView = rootView.findViewById(R.id.o3_tv);
        TextView so2TextView = rootView.findViewById(R.id.so2_tv);



        pm25TextView.setText(R.string.PM2_5);
        coTextView.setText(R.string.CO);
        setSubscript("NO2",no2TextView,2,3);
        setSubscript("O3",o3TextView,1,2);
        setSubscript("SO2",so2TextView,2,3);


        return rootView;

    }

    public void updateArticleView(Bundle bundle){
        Boolean isConnected = allCitiesFragment.bundle.getBoolean("NetworkStatus");
        if(!isConnected) {
            frameLayout.setVisibility(View.GONE);
            emptyState.setText("No internet connection.");
        } else {
        frameLayout.setVisibility(View.VISIBLE);
        if(bundle!=null) {
            String station = bundle.getString("STATION");
            String state = bundle.getString("STATE");
            String address = bundle.getString("CITY") + ", " + state;
            String meaning = bundle.getString("WHATVALUE");

            String no2 = bundle.getString("NO2");
            String pm25 = bundle.getString("PM25");
            String pm10 = bundle.getString("PM10");
            String so2 = bundle.getString("SO2");
            String co = bundle.getString("CO");
            String o3 = bundle.getString("O3");

            if (address.length() > 12) {
                stationTextView.setTextSize(28);
            } else
                stationTextView.setTextSize(36);

            stationTextView.setText(station);
            addressTextView.setText(address);
            whatTextView.setText(meaning);

            no2Circle.setText(no2);
            pm25circle.setText(pm25);
            pm10circle.setText(pm10);
            so2Circle.setText(so2);
            coCircle.setText(co);
            o3Circle.setText(o3);

            int no2Color = ContextCompat.getColor(getContext(), cityAirQuality2.getNO2color(no2));
            no2Drawable.setColor(no2Color);

            int pm25color = ContextCompat.getColor(getContext(), cityAirQuality2.getPM25Color(pm25));
            pm25Drawable.setColor(pm25color);

            int pm10color = ContextCompat.getColor(getContext(), cityAirQuality2.getPM10color(pm10));
            pm10Drawable.setColor(pm10color);

            int so2Color = ContextCompat.getColor(getContext(), cityAirQuality2.getSO2color(so2));
            so2Drawable.setColor(so2Color);

            int coColor = ContextCompat.getColor(getContext(), cityAirQuality2.getCOcolor(co));
            coDrawable.setColor(coColor);

            int o3Color = ContextCompat.getColor(getContext(), cityAirQuality2.getO3color(o3));
            o3Drawable.setColor(o3Color);
        }
        }
        //cityTextView.setText("HEYWASSA");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public void setSubscript(String pollutant,TextView textView, int pos1,int pos2){
        SpannableStringBuilder sb = new SpannableStringBuilder(pollutant);
        sb.setSpan(new SubscriptSpan(), pos1, pos2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        sb.setSpan(new RelativeSizeSpan(0.75f), pos1, pos2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(sb, BufferType.SPANNABLE);
    }
}
