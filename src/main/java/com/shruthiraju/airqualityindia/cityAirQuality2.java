package com.shruthiraju.airqualityindia;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;

//Created by Shruthi Raju  on 10/5/2017.

public class cityAirQuality2 {


    private String mCity;
    private String mState;
    private String mStation;
    private String mAddress;
    private String mPollutantID;
    private String mPollutantAvg;
    private String mPollutantMin;
    private String mPollutantMax;
    public static List<cityAirQuality2> everything = new ArrayList<>();

    public cityAirQuality2(String station, String city, String state, String pollutantid, String pollutantavg,String PollutantMax, String PollutantMin) {
        mStation = station;
        mCity = city;
        mState= state;
        mPollutantAvg = pollutantavg;
        mPollutantID = pollutantid;
        mPollutantMax = PollutantMax;
        mPollutantMin = PollutantMin;
        addtoList();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(mState)
                .append(mCity)
                .append(mStation)
                .toHashCode();
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof cityAirQuality2)) {
            return false;
        }

        cityAirQuality2 user = (cityAirQuality2) o;

        return new EqualsBuilder()
                .append(mState, user.mState)
                .append(mCity, user.mCity)
                .append(mStation, user.mStation)
                .isEquals();
    }

    public void addtoList(){
        everything.add(this);
    }

    public String getmState(){
        return mState;
    }

    public String getmCity(){
        mCity = mCity.substring(0,1).toUpperCase() + mCity.substring(1).toLowerCase();
        return mCity;
    }

    public String getmStation(){
        return mStation;
    }

    public String getAddress(){
        mAddress = mStation+", "+mCity;
        return mAddress;
    }

    public String getmPollutantID(){
        return mPollutantID;
    }

    public String getmPollutantAvg(){
        return mPollutantAvg;
    }

    public int getmPollutantMin(){
        return Integer.parseInt(mPollutantMin);

    }

    public int getmPollutantMax(){
        return Integer.parseInt(mPollutantMax);
    }

    public static String getSpecPollutant(String station, String pollutantCaps){
        List<cityAirQuality2> specStation = getSpecificStationList(station);
        String pollutant = new String();
        for(int i=0; i < specStation.size(); ++i){
            if(specStation.get(i).getmPollutantID().equals(pollutantCaps)){
                pollutant = specStation.get(i).getmPollutantAvg();
                if (Integer.parseInt(pollutant) == -1){
                    pollutant= Float.toString((specStation.get(i).getmPollutantMax()+specStation.get(i).getmPollutantMin())/2);
                }
                break;
            } else {
                pollutant = "N/A";
            }
        }
        return pollutant;
    }


    public static List<cityAirQuality2> getSpecificStationList(String station){
        List<cityAirQuality2> specificStation = new ArrayList<>();
        for (int i=0; i < everything.size(); ++i){
            if(everything.get(i).getmStation().equals(station)){
                specificStation.add(everything.get(i));
            }
        }
        return specificStation;
    }

    public String getPM25(String station){
        List<cityAirQuality2> specStation = getSpecificStationList(station);
        String pm25 = new String();
        for(int i=0; i<specStation.size();++i){
            if (specStation.get(i).getmPollutantID().equals("PM2.5")){
                pm25 = specStation.get(i).getmPollutantAvg();
                break;
            } else {
                pm25 = "N/A";
            }
        }
        return pm25;
    }

    public static List<cityAirQuality2> getPM25Array(List<cityAirQuality2> data){
        List<cityAirQuality2> PM25Array = new ArrayList<>();
        for(int i=0; i < data.size(); ++i){
            if(data.get(i).getmPollutantID().equals("PM2.5")){
                PM25Array.add(data.get(i));
            }
        }
        return PM25Array;

    }

    public static String getPM25Descriptive(String pm25String){
        int pm25 = Integer.parseInt(pm25String);
        String desc;
        if (pm25 < 12.0){
            desc = "good";
        } else if (12.0 < pm25 && pm25 < 35.4){
            desc = "satisfactory";
        } else if (35.4 < pm25 && pm25 < 55.4){
            desc = "moderate";
        } else if (55.4 < pm25 && pm25 < 150.4){
            desc = "poor";
        } else if (150.4 < pm25 && pm25 < 250.4) {
            desc = "very poor";
        } else {
            desc = "hazardous";
        }
        return desc;
    }


    public static int getPM25Color(String pm25String){
        int color;
        if(StringUtils.isNumeric(pm25String)) {
            int pm25 = Integer.parseInt(pm25String);
            if (pm25 < 12.0) {
                color = R.color.good;
            } else if (12.0 < pm25 && pm25 < 35.4) {
                color = R.color.satisfactory;
            } else if (35.4 < pm25 && pm25 < 55.4) {
                color = R.color.moderate;
            } else if (55.4 < pm25 && pm25 < 150.4) {
                color = R.color.poor;
            } else if (150.4 < pm25 && pm25 < 250.4) {
                color = R.color.very_poor;
            } else {
                color = R.color.hazardous;
            }
        } else {
            if (pm25String.equals("N/A")) {
                color = R.color.notAvailable;
            } else
                color = R.color.good;
        }

        return color;
    }


    public static int getNO2color(String no2String) {
        int color;
        if (StringUtils.isNumeric(no2String)) {
            int no2 = Integer.parseInt(no2String);
            if (no2 <= 40) {
                color = R.color.good;
            } else if (41 <= no2 && no2 <= 80) {
                color = R.color.satisfactory;
            } else if (81 <= no2 && no2 <= 180) {
                color = R.color.moderate;
            } else if (181 <= no2 && no2 <= 280) {
                color = R.color.poor;
            } else if (281 <= no2 && no2 <= 400) {
                color = R.color.very_poor;
            } else {
                color = R.color.hazardous;
            }
        } else {
            if (no2String.equals("N/A")) {
                color = R.color.notAvailable;
            } else
                color = R.color.good;
        }
        return color;
    }

    public static int getSO2color(String so2String) {
        int color;
        if (StringUtils.isNumeric(so2String)) {
            int no2 = Integer.parseInt(so2String);
            if (no2 <= 40) {
                color = R.color.good;
            } else if (41 <= no2 && no2 <= 80) {
                color = R.color.satisfactory;
            } else if (81 <= no2 && no2 <= 380) {
                color = R.color.moderate;
            } else if (381 <= no2 && no2 <= 800) {
                color = R.color.poor;
            } else if (801 <= no2 && no2 <= 1600) {
                color = R.color.very_poor;
            } else {
                color = R.color.hazardous;
            }
        } else {
            if (so2String.equals("N/A")) {
                color = R.color.notAvailable;
            } else
                color = R.color.good;
        }
        return color;
    }

    public static int getPM10color(String pm10String) {
        int color;
        if (StringUtils.isNumeric(pm10String)) {
            int no2 = Integer.parseInt(pm10String);
            if (no2 <= 50) {
                color = R.color.good;
            } else if (51 <= no2 && no2 <= 100) {
                color = R.color.satisfactory;
            } else if (101 <= no2 && no2 <= 200) {
                color = R.color.moderate;
            } else if (201 <= no2 && no2 <= 300) {
                color = R.color.poor;
            } else if (301 <= no2 && no2 <= 400) {
                color = R.color.very_poor;
            } else {
                color = R.color.hazardous;
            }
        } else {
            if (pm10String.equals("N/A")) {
                color = R.color.notAvailable;
            } else
                color = R.color.good;
        }
        return color;
    }

    public static int getO3color(String o3String) {
        int color;
        if (StringUtils.isNumeric(o3String)) {
            int no2 = Integer.parseInt(o3String);
            if (no2 <= 50) {
                color = R.color.good;
            } else if (51 <= no2 && no2 <= 100) {
                color = R.color.satisfactory;
            } else if (101 <= no2 && no2 <= 168) {
                color = R.color.moderate;
            } else if (169 <= no2 && no2 <= 208) {
                color = R.color.poor;
            } else if (209 <= no2 && no2 <= 748) {
                color = R.color.very_poor;
            } else {
                color = R.color.hazardous;
            }
        } else {
            if (o3String.equals("N/A")) {
                color = R.color.notAvailable;
            } else
                color = R.color.good;
        }
        return color;
    }

     static int getCOcolor(String coString) {
        int color;
        if (StringUtils.isNumeric(coString)) {
            int no2 = Integer.parseInt(coString);
            if (no2 <= 1) {
                color = R.color.good;
            } else if (1 < no2 && no2 <= 2) {
                color = R.color.satisfactory;
            } else if (2 < no2 && no2 <= 10) {
                color = R.color.moderate;
            } else if (10 < no2 && no2 <= 17) {
                color = R.color.poor;
            } else if (17 < no2 && no2 <= 34) {
                color = R.color.very_poor;
            } else {
                color = R.color.hazardous;
            }
        } else {
            if (coString.equals("N/A")) {
                color = R.color.notAvailable;
            } else
                color = R.color.good;
        }
        return color;
    }

    public static List<cityAirQuality2> getSearchResult(String queryString){
        List<cityAirQuality2> result = new ArrayList<>();
        String query = queryString.trim();
        List<cityAirQuality2> uniqueArray = getPM25Array(everything);

        for(int i=0; i<uniqueArray.size(); ++i){
            if(uniqueArray.get(i).getmCity().toLowerCase().contains(query.toLowerCase())){
                result.add(uniqueArray.get(i));
            } else if(uniqueArray.get(i).getmStation().toLowerCase().contains(query.toLowerCase())){
                result.add(uniqueArray.get(i));
            } else if(uniqueArray.get(i).getmState().toLowerCase().contains(query.toLowerCase())){
                result.add(uniqueArray.get(i));
            }
        }
        return result;
    }
}


