package com.example.android.quakereport;

public class EarthquakeConstructor {

    private String mMagnitude;

    private String mLocation;

    private String mDate;

    private String mTime;

    private String mUrl;

    public EarthquakeConstructor(String magnitude, String location, String date, String time, String urlstring){

        mMagnitude = magnitude;
        mLocation = location;
        mDate = date;
        mTime = time;
        mUrl = urlstring;

    }

    public String getmMagnitude() { return mMagnitude;}

    public String getmLocation() { return mLocation;}

    public String getmDate() { return mDate;}

    public String getmTime() { return mTime;}

    public String getmUrl() { return mUrl;}



}
