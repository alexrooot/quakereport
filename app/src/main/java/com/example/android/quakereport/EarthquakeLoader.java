package com.example.android.quakereport;

//you need to copy this import classes as they are exclusive to this Loader version
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader {


    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();




    private String mainActivityStringUrl;



    public EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        mainActivityStringUrl = url;
        Log.e(LOG_TAG,"inside EathquakeLoader we got the string url");
}

    @Nullable
    @Override
    public Object loadInBackground() {
        if (mainActivityStringUrl == null){
            Log.e(LOG_TAG,"we got a null url");
            return null;
        }
        Log.e(LOG_TAG,"calling on the class Utils from EarthquakeLoader");
        ArrayList<EarthquakeConstructor> results = (ArrayList<EarthquakeConstructor>) Utils.feachEarthquakeData(mainActivityStringUrl);
        //Toast.makeText(getContext(),"Your Message", Toast.LENGTH_LONG).show();
        return results;
    }


    //This overwrite will flag the system to start on the loadInBackGround
    @Override
    protected void onStartLoading() {
        forceLoad();
    }


}
