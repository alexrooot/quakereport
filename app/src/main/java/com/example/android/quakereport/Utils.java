package com.example.android.quakereport;

import android.text.TextUtils;
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
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Utils {

    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static ArrayList<EarthquakeConstructor> feachEarthquakeDate(String requestURL){
        //we are going to be getting a string and converting it to URL type with the little function
        URL url = createURL(requestURL);

        //Perform a http request to the URL and receive as JSON response
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        }catch ( IOException e){
            Log.e(LOG_TAG, "error closing input stream", e);
        }

        //Extract relevant information from the JSON response and create an event/EarthqualeConstructor object
        ArrayList<EarthquakeConstructor> earthquake = extractFeatureFromJson(jsonResponse);

        //return the event/EarthqualeConstructor
        return earthquake;

    }

    //you will need to say that it returns a type of file in this case the array of type EarthquakeConstructor
    public static ArrayList<EarthquakeConstructor> extractFeatureFromJson(String jsonResponse) {
        //if the JSon String is empty or null return early
        if (TextUtils.isEmpty(jsonResponse)){
            return null;
        }

        //Create an array to add data about the each earthquake make sure to say it will be of type
        //EarthquakeConstructor
        ArrayList<EarthquakeConstructor> earthquakeslist = new ArrayList<>();
        try{
            //convert the string file into JSON type
            //making and instance call for file type JSON
            JSONObject baseJasonResponse = new JSONObject(jsonResponse);
            //Extract where it starts with the word "features" into and array
            JSONArray earthquakeArray = baseJasonResponse.getJSONArray("features");



                for ( int i =0 ; i < earthquakeArray.length(); i++){
                    JSONObject currecntEarthquake  = earthquakeArray.getJSONObject(i);
                    JSONObject properties = currecntEarthquake.getJSONObject("properties");
                    String magnitude =properties.getString("mag");
                    String place = properties.getString("place");
                    String time = properties.getString("time");
                    String urlString = properties.getString("url");


                    // Set up time
                    //you need to convert the string time to long type
                    long timeinml = Long.parseLong(time);
                    //make and instance for data format and setup up date structure
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    SimpleDateFormat formatTime = new SimpleDateFormat("hh:mm a");
                    //save date as string by calling the function"format" . use the format you made "format"
                    //                                  and put the long time"timeinml"
                    String timeformated = format.format(timeinml);
                    String timeformatedTime = formatTime.format(timeinml);

                    //save the individual elements into and object like and array
                    earthquakeslist.add(new EarthquakeConstructor(magnitude, place, timeformated,timeformatedTime,urlString));


                }



        } catch (JSONException e) {
            Log.e(LOG_TAG,"Problem parsing the earth earthquake JSON results",e);
        }
        return earthquakeslist;
    }

    //  this method will make the socket connection via URL type
    //                                  Add the throws IOException to get error messages functions
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    // setup the reading method of the http json type input to string
    // add the throws IOExceptions for errors from the functions
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null){

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            //bufferreader is just larger chunks of strings for faster reading on CPU
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            //loop on the file type line that has all the text and keep switching to next line
            while (line != null){
                //add to the end of output what you found in current line of object/file line
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createURL(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e){
            Log.e(LOG_TAG,"Error with creating URL :",e);
        }return url;
    }
}
