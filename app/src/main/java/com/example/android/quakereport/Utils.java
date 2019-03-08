package com.example.android.quakereport;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
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


        return null;
    }

    private static String makeHTTPSocket(URL url) {
        String jsonResponse = "";
        if ( url == null){
            return jsonResponse;
        }

        //Start setup by making and instance to HttpURLConnection
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream ;
        try{
            //give it some additional settings before we call on it
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(30000/*this is in milli's*/);
            httpURLConnection.setConnectTimeout(4500);
            httpURLConnection.setRequestMethod("GET");
            //starttRL socket
            httpURLConnection.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return jsonResponse;
    }



    private static URL createUrl(String requestedURL) {
        URL url = null;
        try{
            url = new URL(requestedURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG,"Error could not convert sting to URL for system");
        }
        return url;
    }


}
