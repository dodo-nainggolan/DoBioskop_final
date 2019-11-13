package com.example.favoritecatalog;

import android.net.Uri;
import android.provider.BaseColumns;

public final class DatabaseContractFilm {

    public static final String AUTHORITY = "com.dicoding.picodiploma.academy";

    private static final String SCHEME = "content";

    public static final String TABLE_NAME = "domovies";

    public static class MoviesColumn implements BaseColumns {

        public static final String JUDUL_FILM = "judulFilm";
        public static final String DESKRIPSI_FILM = "deksripsiFilm";
        public static final String RILIS_FILM = "rilisFilm";
        public static final String GAMBAR_FILM = "gambarFIlm";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();
    }
}
