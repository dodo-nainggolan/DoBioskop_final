package com.dicoding.picodiploma.academy.helper;

import android.database.Cursor;
import android.util.Log;

import com.dicoding.picodiploma.academy.entity.FavoriteFilm;
import com.dicoding.picodiploma.academy.db.DatabaseContractFilm;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MappingHelper {

    public static ArrayList<FavoriteFilm> mapCursorToArrayList(Cursor moviesCursor) {
        ArrayList<FavoriteFilm> favList = new ArrayList<> ();

        while (moviesCursor.moveToNext ()) {
            int id = moviesCursor.getInt ( moviesCursor.getColumnIndexOrThrow ( DatabaseContractFilm.MoviesColumn._ID ) );
            String judulFilm = moviesCursor.getString ( moviesCursor.getColumnIndexOrThrow ( DatabaseContractFilm.MoviesColumn.JUDUL_FILM ) );
            String deskripsiFilm = moviesCursor.getString ( moviesCursor.getColumnIndexOrThrow ( DatabaseContractFilm.MoviesColumn.DESKRIPSI_FILM ) );
            String rilisFilm = moviesCursor.getString ( moviesCursor.getColumnIndexOrThrow ( DatabaseContractFilm.MoviesColumn.RILIS_FILM ) );
            String gambarFilm = moviesCursor.getString ( moviesCursor.getColumnIndexOrThrow ( DatabaseContractFilm.MoviesColumn.GAMBAR_FILM ) );

            favList.add ( new FavoriteFilm ( id, judulFilm, rilisFilm, deskripsiFilm, gambarFilm ) );
        }
        return favList;
    }

}
