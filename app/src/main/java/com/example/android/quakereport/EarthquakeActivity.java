/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Loader;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
        //need to import this liberies first or you will be lost in errors
        //import android.content.Loader;
        //import android.app.LoaderManager;
        //import android.app.LoaderManager.LoaderCallbacks;
        implements LoaderCallbacks<List<EarthquakeConstructor>>{
    // Also implement this methods onCreateLoader, onLoadFinished, onLoaderReset

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final String USGS_REQUESTED_URL =
            "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        /*
        // Create a fake list of earthquake locations.
        ArrayList<EarthquakeConstructor> earthquakes = new ArrayList<>();
        earthquakes.add(new EarthquakeConstructor("7.2","San Francisco", "Feb 2, 2016"));
        earthquakes.add(new EarthquakeConstructor("6.1", "London","July 20, 20015"));
        earthquakes.add(new EarthquakeConstructor("3.9","Tokyo","Nov 10, 2014"));
        earthquakes.add(new EarthquakeConstructor("5.4", "Mexico City","May 3, 2014"));
        earthquakes.add(new EarthquakeConstructor("2.8", "Moscow", "Jan 31, 2013"));
        earthquakes.add(new EarthquakeConstructor("4.9", "rio de Janeiro","Aug 19, 2012"));
        earthquakes.add(new EarthquakeConstructor("1.6", "Paris", "Oct 30, 2011"));
        int r = earthquakes.lastIndexOf(earthquakes);
        */


        TextView emptylist = (TextView) findViewById(R.id.EmptyTestView);


        ArrayList<EarthquakeConstructor> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);;

        // Create a new {@link ArrayAdapter} of earthquakes

        final EarthquakeAdapter customeObjectAdapter = new EarthquakeAdapter(this, earthquakes);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(customeObjectAdapter);


        // to make ListView/Recycler clickable use this
        //needs instance call object/refrence so make it before by calling the customAdapter in this case "EarthquakeAdapter"
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // onclick function is in emulation mode from the adapter class/a frees moment
                //make a temp object of the type element that you are going to pull down from adapter class
                EarthquakeConstructor currentEartquake;
                //call the object that has the all the custom data for the adapter in this case its save as "customObjectAdapter"
                currentEartquake = customeObjectAdapter.getItem(position);
                //shortcut is EarthquakeConstructor currentEartquake = customeObjectAdapter.getItem(position);

                try{
                    //To go to browser
                    //Use a new Intent instance of "Intent.ACTION_VIEW"
                    //if you need to convert sting to Uri AKA Uniform Resource Locator
                    Intent goToUSGL = new Intent (Intent.ACTION_VIEW, Uri.parse(currentEartquake.getmUrl()));
                    //must call the start to this specific Intent
                    startActivity(goToUSGL);
                }catch (ActivityNotFoundException e ){
                    Toast.makeText(getApplicationContext(),"no application can handle this request",Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });


        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,this);
        Log.e(LOG_TAG,"Loader manager finished");


    }

    @Override
    public Loader<List<EarthquakeConstructor>> onCreateLoader(int id, Bundle args) {
        Log.e(LOG_TAG, "going call class EarquakeLoader");
        /*
        //you need to copy this import classes as they are exclusive to this Loader version
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;
import java.util.List;
         */
        return new EarthquakeLoader(this,USGS_REQUESTED_URL);

    }


    @Override
    public void onLoadFinished(Loader<List<EarthquakeConstructor>> loader, List<EarthquakeConstructor> data) {
        Log.e(LOG_TAG,"On Load finish is starting");
        //set up an Adapter so we can use the data return from onCreateLoader
        //EarthquakeAdapter mEarthquakeAdapter ;
        //mEarthquakeAdapter = new EarthquakeAdapter(this,new ArrayList<EarthquakeConstructor>());
       // mEarthquakeAdapter.clear();
       // mEarthquakeAdapter.addAll(data);
        Log.e(LOG_TAG,"We send the list array to the adapter");

    }

    @Override
    public void onLoaderReset(Loader<List<EarthquakeConstructor>> loader) {
        //EarthquakeAdapter mEarthquakeAdapter ;
       // mEarthquakeAdapter = new EarthquakeAdapter(this,new ArrayList<EarthquakeConstructor>());
        //mEarthquakeAdapter.clear();

    }
}
