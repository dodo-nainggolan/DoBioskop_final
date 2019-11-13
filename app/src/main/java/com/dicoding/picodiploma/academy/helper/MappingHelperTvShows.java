package com.dicoding.picodiploma.academy.helper;

import android.database.Cursor;

import com.dicoding.picodiploma.academy.db.DatabaseContractFilm;
import com.dicoding.picodiploma.academy.db.DatabaseContractTvShows;
import com.dicoding.picodiploma.academy.entity.FavoriteTvShows;

import java.util.ArrayList;

public class MappingHelperTvShows {

    public static ArrayList<FavoriteTvShows> mapCursorToArrayList(Cursor moviesCursor) {
        ArrayList<FavoriteTvShows> favTvList = new ArrayList<> ();

        while (moviesCursor.moveToNext ()) {
            int id = moviesCursor.getInt ( moviesCursor.getColumnIndexOrThrow ( DatabaseContractTvShows.MoviesColumn._ID ) );
            String judulFilm = moviesCursor.getString ( moviesCursor.getColumnIndexOrThrow ( DatabaseContractTvShows.MoviesColumn.JUDUL_FILM ) );
            String deskripsiFilm = moviesCursor.getString ( moviesCursor.getColumnIndexOrThrow ( DatabaseContractTvShows.MoviesColumn.DESKRIPSI_FILM ) );
            String rilisFilm = moviesCursor.getString ( moviesCursor.getColumnIndexOrThrow ( DatabaseContractTvShows.MoviesColumn.RILIS_FILM ) );
            String gambarFilm = moviesCursor.getString ( moviesCursor.getColumnIndexOrThrow ( DatabaseContractTvShows.MoviesColumn.GAMBAR_FILM ) );

            favTvList.add ( new FavoriteTvShows ( id, judulFilm, rilisFilm, deskripsiFilm, gambarFilm ) );
        }
        return favTvList;
    }

}
