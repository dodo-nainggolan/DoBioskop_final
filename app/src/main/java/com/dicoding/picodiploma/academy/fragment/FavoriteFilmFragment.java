package com.dicoding.picodiploma.academy.fragment;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.adapter.FavoriteFilmAdapter;
import com.dicoding.picodiploma.academy.db.FavoriteFilmHelper;
import com.dicoding.picodiploma.academy.entity.FavoriteFilm;
import com.dicoding.picodiploma.academy.entity.Movies;
import com.dicoding.picodiploma.academy.helper.MappingHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

interface LoadNotesCallback {
    void preExecute();

    void postExecute(ArrayList<FavoriteFilm> favoriteFilms);
}

public class FavoriteFilmFragment extends Fragment implements LoadNotesCallback, LoaderManager.LoaderCallbacks<ArrayList<Movies>> {

    private static final String EXTRA_STATE = "EXTRA_STATE";
    private FavoriteFilmAdapter adapter;
    private RecyclerView rv;
    private ProgressBar progressBar;
    private FavoriteFilmHelper favoriteFilmHelper;

    public FavoriteFilmFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate ( R.layout.fragment_favorite_film, container, false );
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );

        adapter = new FavoriteFilmAdapter ();
        adapter.notifyDataSetChanged ();

        rv = view.findViewById ( R.id.card_view_list_item_fav );
        progressBar = view.findViewById ( R.id.progressBar );

        rv.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
        rv.setAdapter ( adapter );

        favoriteFilmHelper = FavoriteFilmHelper.getInstance ( getContext () );
        favoriteFilmHelper.open ();

        new LoadNotesAsync ( favoriteFilmHelper, this ).execute ();
        showLoading ( true );

    }

    @Override
    public void preExecute() {
    }

    @Override
    public void postExecute(ArrayList<FavoriteFilm> favoriteFilm) {
        adapter.setListNotes ( favoriteFilm );
        showLoading ( false );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState ( outState );
        outState.putParcelableArrayList ( EXTRA_STATE, adapter.getListNotes () );
    }

    @Override
    public Loader<ArrayList<Movies>> onCreateLoader(int id, Bundle args) {
        return new MoviesAsyncTaskLoader ( getActivity (), "" );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movies>> loader, ArrayList<Movies> data) {
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Movies>> loader) {

    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility ( View.VISIBLE );
        } else {
            progressBar.setVisibility ( View.GONE );
        }
    }

    private static class LoadNotesAsync extends AsyncTask<Void, Void, ArrayList<FavoriteFilm>> {

        private final WeakReference<FavoriteFilmHelper> weakFavHelper;
        private final WeakReference<LoadNotesCallback> weakCallback;

        private LoadNotesAsync(FavoriteFilmHelper noteHelper, LoadNotesCallback callback) {
            weakFavHelper = new WeakReference<> ( noteHelper );
            weakCallback = new WeakReference<> ( callback );
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute ();
            weakCallback.get ().preExecute ();
        }

        @Override
        protected ArrayList<FavoriteFilm> doInBackground(Void... voids) {
            Cursor dataCursor = weakFavHelper.get ().queryAll ();
            return MappingHelper.mapCursorToArrayList ( dataCursor );
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteFilm> favoriteFilms) {
            super.onPostExecute ( favoriteFilms );
            weakCallback.get ().postExecute ( favoriteFilms );

        }
    }
}
