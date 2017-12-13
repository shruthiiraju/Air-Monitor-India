package com.shruthiraju.airqualityindia;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;


// Created by Shruthi Raju on 9/29/2017.

public class QueryUtils {
    private static String LOG_TAG = QueryUtils.class.getSimpleName();

    public static List<cityAirQuality2> fetchAirQualityData(String requestUrl) {

        String jsonResponse = null;

        /*Check the Progress Bar
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        try {
            jsonResponse = makeHttpRequest(requestUrl);
        } catch (IOException e) {
        }
        List<cityAirQuality2> cities = extractData(jsonResponse);
        return cities;
    }

    public static String makeHttpRequest(String Requesturl) throws IOException{
        String jsonResponse = null;

        URL url = new URL(Requesturl);

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode()==200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else{
                Log.e(LOG_TAG, "Error" + urlConnection.getResponseCode());
            }
        } catch (IOException e){
        } finally {
            if (urlConnection!=null){
                urlConnection.disconnect();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    public static String readFromStream(InputStream input) throws IOException{
        StringBuilder output = new StringBuilder();
        if(input!=null){
            InputStreamReader inputStreamReader = new InputStreamReader(input, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while(line!=null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static List<cityAirQuality2> extractData(String output){
        List<cityAirQuality2> cities = new ArrayList<>();
        try {
            if (output != null) {
                JSONObject dataJSON = new JSONObject(output);
                JSONArray allCitiesArray = dataJSON.getJSONArray("records");
                cityAirQuality2.everything.clear();

                for (int i = 0; i < allCitiesArray.length(); i++) {

                    JSONObject currentCity = allCitiesArray.getJSONObject(i);
                    String city = currentCity.getString("city");
                    String state = currentCity.getString("state");
                    String station = currentCity.getString("station");
                    String pollution_id = currentCity.getString("pollutant_id");
                    String pollution_min = currentCity.getString("pollutant_min");
                    String pollution_max = currentCity.getString("pollutant_max");
                    String pollution_avg = Integer.toString((Integer.parseInt(pollution_max)+Integer.parseInt(pollution_min))/2);
                    cityAirQuality2 city1 = new cityAirQuality2(station,city,state,pollution_id, pollution_avg, pollution_max, pollution_min);
                    cities.add(city1);

                }
            }
        } catch (JSONException e){
        }

        return cities;
    }
}
