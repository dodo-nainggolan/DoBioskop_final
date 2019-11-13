package com.dicoding.picodiploma.academy.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_NOTE = String.format ( "CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContractFilm.TABLE_NAME,
            DatabaseContractFilm.MoviesColumn._ID,
            DatabaseContractFilm.MoviesColumn.JUDUL_FILM,
            DatabaseContractFilm.MoviesColumn.DESKRIPSI_FILM,
            DatabaseContractFilm.MoviesColumn.RILIS_FILM,
            DatabaseContractFilm.MoviesColumn.GAMBAR_FILM
    );
    private static final String SQL_CREATE_TABLE_TVSHOW = String.format ( "CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContractTvShows.TABLE_NAME,
            DatabaseContractTvShows.MoviesColumn._ID,
            DatabaseContractTvShows.MoviesColumn.JUDUL_FILM,
            DatabaseContractTvShows.MoviesColumn.DESKRIPSI_FILM,
            DatabaseContractTvShows.MoviesColumn.RILIS_FILM,
            DatabaseContractTvShows.MoviesColumn.GAMBAR_FILM
    );
    public static String DATABASE_NAME = "dbmoviesapp";

    public DatabaseHelper(Context context) {
        super ( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL ( SQL_CREATE_TABLE_NOTE );
        db.execSQL ( SQL_CREATE_TABLE_TVSHOW );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL ( "DROP TABLE IF EXISTS " + DatabaseContractFilm.TABLE_NAME );
        db.execSQL ( "DROP TABLE IF EXISTS " + DatabaseContractTvShows.TABLE_NAME );
        onCreate ( db );
    }
}
