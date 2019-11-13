package com.example.favoritecatalog;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FavoriteFilmCallback {

    private FavoriteFilmAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_favorite_film);

        RecyclerView rvView = findViewById(R.id.card_view_list_item_fav);
        adapter = new FavoriteFilmAdapter();

        HandlerThread handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        DataObserver myObserver = new DataObserver(handler, this);
        getContentResolver().registerContentObserver(DatabaseContractFilm.MoviesColumn.CONTENT_URI, true, myObserver);

        new FavoriteFilmAsync(this, this).execute();

        rvView.setAdapter(adapter);
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(ArrayList<FavoriteFilm> favoriteFilms) {
        if(favoriteFilms.size() > 0) {
            adapter.setListNotes(favoriteFilms);
        }
        else {
            adapter.setListNotes(new ArrayList<FavoriteFilm>());
            Toast.makeText(getApplicationContext(), "GA ADA TULISAN!", Toast.LENGTH_SHORT).show();
        }
    }


    public static class DataObserver extends ContentObserver {
        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new FavoriteFilmAsync(context, (FavoriteFilmCallback) context).execute();
        }
    }

    private static class FavoriteFilmAsync extends AsyncTask<Void, Void, ArrayList<FavoriteFilm>> {

        private WeakReference<Context> weakContext;
        private WeakReference<FavoriteFilmCallback> weakCallback;


        public FavoriteFilmAsync(Context context, FavoriteFilmCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);

            Log.e("ASYNC", "START !");
        }

        public FavoriteFilmAsync() {}

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<FavoriteFilm> doInBackground(Void... voids) {
            Context context = weakContext.get();
            Cursor dataFavoriteFilm = context.getContentResolver().query(DatabaseContractFilm.MoviesColumn.CONTENT_URI, null,null,null,null);
            return MappingHelper.mapCursorToArrayList(dataFavoriteFilm);
        }

        @Override
        protected void onPostExecute(ArrayList<FavoriteFilm> favoriteFilms) {
            super.onPostExecute(favoriteFilms);
            weakCallback.get().postExecute(favoriteFilms);
        }
    }
}

interface FavoriteFilmCallback {
    void preExecute();
    void postExecute(ArrayList<FavoriteFilm> favoriteFilms);
}