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


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity implements LoaderCallbacks<ArrayList<EarthQuake>> {

    private static final int EARTHQUAKE_LOADER_ID = 1;

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private EarthQuakeAdapter earthQuakeAdapter;

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private TextView mEmptyStateTextView ;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //Adapter and Set Adapter
         earthQuakeAdapter=new EarthQuakeAdapter(EarthquakeActivity.this,0,new ArrayList<EarthQuake>());
        ListView earthquakeListView = findViewById(R.id.list);
        earthquakeListView.setAdapter(earthQuakeAdapter);

        // onItemClick ListViw
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                EarthQuake currentEarthquake = earthQuakeAdapter.getItem(i);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentEarthquake.getURL()));
                startActivity(intent);
            }
        });


        mEmptyStateTextView =findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        progressBar=findViewById(R.id.loading_spinner);

        ConnectivityManager connectivityManager=(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();

        Log.i(LOG_TAG, "Internet Connection");
        // check Internet Connection
        if(networkInfo!=null&& networkInfo.isConnected()) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle.
            Log.i(LOG_TAG, "initLoader");
            getSupportLoaderManager().initLoader(EARTHQUAKE_LOADER_ID, null, this);
        }else {

            progressBar.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }


    }


    @Override
    public Loader<ArrayList<EarthQuake>> onCreateLoader(int i,  Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader");
        // Create a new loader for the given URL
        return new EarthquakeLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished( Loader<ArrayList<EarthQuake>> loader, ArrayList<EarthQuake> earthQuakes) {

        progressBar.setVisibility(View.GONE);
        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_earthquakes);


        Log.i(LOG_TAG, "onLoadFinished");

        // Clear the adapter of previous earthquake data
        earthQuakeAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthQuakes != null && !earthQuakes.isEmpty()) {

            earthQuakeAdapter.addAll(earthQuakes);

        }
    }

    @Override
    public void onLoaderReset( Loader<ArrayList<EarthQuake>> loader) {
        Log.i(LOG_TAG, "onLoaderReset");
        // Loader reset, so we can clear out our existing data.
        earthQuakeAdapter.clear();

    }
}
