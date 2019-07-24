package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<EarthQuake>> {
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private String mUrl;

    public EarthquakeLoader( Context context,String url) {
        super(context);
        mUrl=url;

    }


    @Nullable
    @Override
    public ArrayList<EarthQuake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        Log.i(LOG_TAG, "loadInBackground");
        // Perform the network request, parse the response, and extract a list of earthquakes.
        ArrayList<EarthQuake> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading");
        forceLoad();
    }
}

