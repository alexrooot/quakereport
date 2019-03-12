package com.example.android.quakereport;

import android.text.TextUtils;
import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    public  static final String LOG_TAG = Utils.class.getSimpleName();

    public static List<EarthquakeConstructor> feachEarthquakeData (String requestedURL){
        Log.e(LOG_TAG,"inside the Utils class")
                ;
        //variable used in cases for no responses
        String jsonResponse = null;
        //make a URL type from the string
        URL url = createUrl(requestedURL);
        //next make connection

        jsonResponse = makeHTTPSocket(url);

        ArrayList<EarthquakeConstructor> earthquake = extractFeatureFromJson(jsonResponse);



        return earthquake;
    }

    private static ArrayList<EarthquakeConstructor> extractFeatureFromJson(String jsonResponse) {
        Log.e(LOG_TAG,"Entering method ExtractFeaturesFromJson");
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        //Convert the string file into JSON type
        JSONObject baseJasonResponse = null;

        ArrayList<EarthquakeConstructor> earthquakelistfromPrase = new ArrayList<>();
        try {
            Log.e(LOG_TAG,"going to try praseing in method extractFeatureFromJson");
            //Convert the string file into JSON type
            baseJasonResponse = new JSONObject((jsonResponse));
            //indicate where to start and cut for the array object in this case features
            JSONArray earthquakeArray = baseJasonResponse.getJSONArray("features");

            //loop to all the JSON array objects now
            for ( int i = 0 ; i < earthquakeArray.length(); i++){
                Log.e(LOG_TAG, "going into for loop in extractFeatureFromJson");

                JSONObject currentEartquake = earthquakeArray.getJSONObject(i);
                JSONObject properties = currentEartquake.getJSONObject("properties");//tire two of JSON
                String mangnitude = ((JSONObject) properties).getString("mag");//leaf
                String place = properties.getString("place");
                String time = properties.getString("time");
                String urlString = properties.getString("url");

                //setup time-convert format
                 long timeInMin = Long.parseLong(time);

                SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat formattime = new SimpleDateFormat("hh:mm a");

                String formateOfDate = formatDate.format(timeInMin);
                String formatofTime = formattime.format(timeInMin);

                earthquakelistfromPrase.add(new EarthquakeConstructor(mangnitude,place,formateOfDate,formatofTime,urlString));

                Log.e(LOG_TAG,"finish for loop in method extractFeatureFromJson");

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG,"Could phrase string into JSON");
            e.printStackTrace();
        }
        return  earthquakelistfromPrase;


    }

    private static String makeHTTPSocket(URL url) {
        Log.e(LOG_TAG,"Starting method makeHTTpSocket");
        String jsonResponse = "";
        if ( url == null){
            Log.e(LOG_TAG, "Returing early from makeHttpSocket");
            return jsonResponse;
        }

        //Start setup by making and instance to HttpURLConnection
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null ;
        try{
            Log.e(LOG_TAG, "trying to setup socket in method makeHttpSocket");
            //give it some additional settings before we call on it
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(30000/*this is in milli's*/);
            httpURLConnection.setConnectTimeout(45000);
            httpURLConnection.setRequestMethod("GET");

            /*
            need to add permission
            <manifest xmlns:android="http://schemas.android.com/apk/res/android"
            package="com.example.android.quakereport">
            <uses-permission android:name="android.permission.INTERNET" />
             */
            httpURLConnection.connect();//starteURL socket

            if (httpURLConnection.getResponseCode() == 200){
                Log.e(LOG_TAG, "Socket was good and will save download file as input Stream");
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);// crete your own method on how to setup and
                                                            // read the string input
            }else {
                Log.e(LOG_TAG, "Error response code: "+ httpURLConnection.getResponseCode());
                return null;
            }
        } catch (IOException e) {
            Log.e(LOG_TAG,"Problem retreving the earthquake JSON results",e);
            e.printStackTrace();// another from the log errors but dont how it works yet
            return null;
        } finally {
            if (httpURLConnection != null ){
                Log.e(LOG_TAG,"closing socket connetion in method makeHttpSocket");
                httpURLConnection.disconnect();
            }
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG,"Problem closing inputStream = httpURLConnection.getInputStream();",e);
                    e.printStackTrace();// another from the log errors but dont how it works yet
                    return null;
                }
            }
        }


        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) {
        // start by setting up the variables type and text protocols
        StringBuilder output = new StringBuilder();// this type of string is faster in reading by the system
        if(inputStream != null){
            // make the next variable read the input parameter and tell it what format/protocal the string/InputStream input is
            InputStreamReader inputStreamReaderNewData = new InputStreamReader(inputStream, Charset.forName("UTF-8"));

            //save all the inputStream as a bufferedReader
            BufferedReader bufferedReader = new BufferedReader(inputStreamReaderNewData);

            try {
                //make bufferedReader data into a string then we can read entire lines at a time by using readLine()
                String line = bufferedReader.readLine();
                while (line != null){
                    //indicate that will be adding text to variable output
                    output.append(line);
                    line = bufferedReader.readLine();
                }
            } catch (IOException e) {
                Log.e(LOG_TAG,"Un-able to read bufferedReader strings of data");
                e.printStackTrace();
                return null;
            }
        }
        return output.toString();
    }


    private static URL createUrl(String requestedURL) {
        URL url = null;
        try{
            url = new URL(requestedURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"Error could not convert sting to URL for system");
            return null;
        }
        return url;
    }


}
