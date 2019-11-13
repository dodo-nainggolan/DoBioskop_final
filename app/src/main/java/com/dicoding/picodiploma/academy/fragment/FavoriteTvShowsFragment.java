package com.dicoding.picodiploma.academy.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.adapter.FavoriteTvShowsAdapter;
import com.dicoding.picodiploma.academy.db.FavoriteTvShowsHelper;
import com.dicoding.picodiploma.academy.entity.FavoriteTvShows;
import com.dicoding.picodiploma.academy.entity.TvShows;
import com.dicoding.picodiploma.academy.helper.MappingHelperTvShows;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

interface LoadTvShowsCallback {
    void preExecute();

    void postExecute(ArrayList<FavoriteTvShows> favoriteFilms);
}

public class FavoriteTvShowsFragment extends Fragment implements LoadTvShowsCallback, LoaderManager.LoaderCallbacks<ArrayList<TvShows>> {

    private static final String EXTRA_STATE = "EXTRA_STATE";
    private FavoriteTvShowsAdapter adapter;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private FavoriteTvShowsHelper favoriteTvShowsHelper;

    public FavoriteTvShowsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate ( R.layout.fragment_favorite_tvshows, container, false );
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );

        adapter = new FavoriteTvShowsAdapter ();
        adapter.notifyDataSetChanged ();

        rv = view.findViewById ( R.id.card_view_list_item_fav );
        progressBar = view.findViewById ( R.id.progressBar );

        showLoading ( true );

        rv.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
        rv.setAdapter ( adapter );

        favoriteTvShowsHelper = FavoriteTvShowsHelper.getInstance ( getContext () );
        favoriteTvShowsHelper.open ();

        new LoadTvShowsAsync ( favoriteTvShowsHelper, this ).execute ();


    }

    @Override
    public void preExecute() {
    }

    @Override
    public void postExecute(ArrayList<FavoriteTvShows> favoriteFilm) {
        adapter.setListNotes ( favoriteFilm );
        showLoading ( false );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState ( outState );
        outState.putParcelableArrayList ( EXTRA_STATE, adapter.getListNotes () );
    }

    @Override
    public Loader<ArrayList<TvShows>> onCreateLoader(int id, Bundle args) {
        return new TvShowsAsyncTaskLoader ( getActivity (), "" );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<TvShows>> loader, ArrayList<TvShows> data) {
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<TvShows>> loader) {

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility ( View.VISIBLE );
        } else {
            progressBar.setVisibility ( View.GONE );
        }
    }

    private static class LoadTvShowsAsync extends AsyncTask<Void, Void, ArrayList<FavoriteTvShows>> {

        private final WeakReference<FavoriteTvShowsHelper> weakFavHelper;
        private final WeakReference<LoadTvShowsCallback> weakCallback;

        private LoadTvShowsAsync(FavoriteTvShowsHelper noteHelper, LoadTvShowsCallback callback) {
            weakFavHelper = new WeakReference<> ( noteHelper );
            weakCallback = new WeakReference<> ( callback );
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute ();
            weakCallback.get ().preExecute ();
        }

        @Override
        protected ArrayList<FavoriteTvShows> doInBackground(Void... voids) {
            Cursor dataCursor = weakFavHelper.get ().queryAll ();
            return MappingHelperTvShows.mapCursorToArrayList ( dataCursor );
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteTvShows> favoriteFilms) {
            super.onPostExecute ( favoriteFilms );
            weakCallback.get ().postExecute ( favoriteFilms );

        }
    }
}
