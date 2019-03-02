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

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthquakeConstructor>> {
    private static final String goTo = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        //EarthquakeAsync task = new EarthquakeAsync();
        //task.execute(goTo);

    }

    private void updateUI(ArrayList<EarthquakeConstructor> earthquakeConstructorsData) {
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes

        final EarthquakeAdapter customeObjectAdapter = new EarthquakeAdapter(this, earthquakeConstructorsData);
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
    }

    @NonNull
    @Override
    public Loader<List<EarthquakeConstructor>> onCreateLoader(int i, @Nullable Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<EarthquakeConstructor>> loader, List<EarthquakeConstructor> earthquakeConstructors) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<EarthquakeConstructor>> loader) {

    }


    // this class returns background treads and needs to be extended
// from AsyncTask<input, status update type, return>
    private class EarthquakeAsync extends AsyncTask<String,Void,ArrayList<EarthquakeConstructor>>{
// you need to high light the red underline or control+o to make the this overwrite work
        @Override
        //this is what we are going to take in
        protected ArrayList doInBackground(String... strings) {
            ArrayList<EarthquakeConstructor> results = Utils.feachEarthquakeDate(goTo);
            return results;
        }

        //You need to click control+o and do the overwrite
        @Override//             this what we are returning to the method update and
        //                      just pass along the same parameter
        protected void onPostExecute(ArrayList<EarthquakeConstructor> earthquakeConstructors) {
            updateUI(earthquakeConstructors);
        }
    }


}


