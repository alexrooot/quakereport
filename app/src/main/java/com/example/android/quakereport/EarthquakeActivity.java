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
import android.widget.ListAdapter;
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
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2012-01-01&endtime=2012-12-01&minmagnitude=6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
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
       // Log.e(LOG_TAG,"We send the list array to the adapter");
       // List<EarthquakeConstructor> results = data;
        //Log.e(LOG_TAG,"made a local variable of data and will call on the UI method");
        UI(data);

    }
    public void UI(List<EarthquakeConstructor> data){
        Log.e(LOG_TAG,"started medthod UI");
        ListView listView = (ListView) findViewById(R.id.list);
        Log.e(LOG_TAG,"made list view avalibe to java");
        EarthquakeAdapter earthquakeAdapter = new EarthquakeAdapter(this,data);
        Log.e(LOG_TAG,"passed in the adapter the list of earthquakes");
        listView.setAdapter(earthquakeAdapter);
        Log.e(LOG_TAG,"set the list view with the data passaed onto the adapter");
    }
    @Override
    public void onLoaderReset(Loader<List<EarthquakeConstructor>> loader) {
        //EarthquakeAdapter mEarthquakeAdapter ;
       // mEarthquakeAdapter = new EarthquakeAdapter(this,new ArrayList<EarthquakeConstructor>());
        //mEarthquakeAdapter.clear();

    }

}
