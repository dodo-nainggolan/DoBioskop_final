package com.dicoding.picodiploma.academy.fragment;


import android.content.Context;
import androidx.loader.content.AsyncTaskLoader;

import com.dicoding.picodiploma.academy.entity.Movies;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MoviesAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Movies>> {

    private static final String API_KEY = "3a92c0f2b44f88d27ffe2b2de5466cc6";
    private ArrayList<Movies> mData;
    private boolean mHasResult = false;
    private String movies;

    MoviesAsyncTaskLoader(final Context context, String movies) {
        super ( context );
        onContentChanged ();
        this.movies = movies;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged ())
            forceLoad ();
        else if (mHasResult)
            deliverResult ( mData );
    }

    @Override
    public void deliverResult(final ArrayList<Movies> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult ( data );
    }

    @Override
    protected void onReset() {
        super.onReset ();
        onStopLoading ();
        if (mHasResult) {
            mData = null;
            mHasResult = false;
        }
    }

    @Override
    public ArrayList<Movies> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient ();
        final ArrayList<Movies> moviesItemses = new ArrayList<> ();

        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + API_KEY  + "&language=en-US";

        client.get ( url, new AsyncHttpResponseHandler () {
            @Override
            public void onStart() {
                super.onStart ();
                setUseSynchronousMode ( true );
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String ( responseBody );
                    JSONObject responseObject = new JSONObject ( result );
                    JSONArray list = responseObject.getJSONArray ( "results" );

                    for (int i = 0; i < list.length (); i++) {
                        JSONObject movies = list.getJSONObject ( i );
                        Movies moviesItems = new Movies ( movies );
                        moviesItemses.add ( moviesItems );

                    }
                } catch (Exception e) {
                    e.printStackTrace ();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //Jika response gagal maka , do nothing
            }
        } );
        return moviesItemses;
    }
}

