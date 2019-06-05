package com.example.android.quakereport;

//you need to copy this import classes as they are exclusive to this Loader version
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.AsyncTaskLoader;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeConstructor>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();

    private String mUrl;

    public EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }


    @Nullable
    @Override
    public List<EarthquakeConstructor> loadInBackground() {
        if (mUrl == null){
            return null;
        }
        List<EarthquakeConstructor> results = Utils.feachEarthquakeDate(mUrl);
        return results;
    }
    //You need the next Override for loadInBackground to start.
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

}
