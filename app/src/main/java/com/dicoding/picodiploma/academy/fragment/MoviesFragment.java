package com.dicoding.picodiploma.academy.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.adapter.MoviesAdapter;
import com.dicoding.picodiploma.academy.entity.Movies;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movies>> {

    MoviesAdapter adapter;
    private RecyclerView rv;
    private ProgressBar progressBar;


    public MoviesFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate ( R.layout.fragment_movies, container, false );
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view, savedInstanceState );

        adapter = new MoviesAdapter ();
        adapter.notifyDataSetChanged ();

        rv = view.findViewById ( R.id.card_view_list_item );
        progressBar = view.findViewById ( R.id.progressBar );

        rv.setLayoutManager ( new LinearLayoutManager ( getContext () ) );
        rv.setAdapter ( adapter );


        Bundle bundle = new Bundle ();
        getLoaderManager ().initLoader ( 0, bundle, this );

    }

    @Override
    public Loader<ArrayList<Movies>> onCreateLoader(int id, Bundle args) {
        showLoading ( true );
        return new MoviesAsyncTaskLoader ( getActivity (), "" );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Movies>> loader, ArrayList<Movies> data) {
        showLoading ( false );
        adapter.setData ( data );
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movies>> loader) {

//TODO optimize dirty trick
//                adapter.setData ( null );
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility ( View.VISIBLE );
        } else {
            progressBar.setVisibility ( View.GONE );
        }
    }

}

