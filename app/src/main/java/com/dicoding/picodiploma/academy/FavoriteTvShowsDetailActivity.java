package com.dicoding.picodiploma.academy;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dicoding.picodiploma.academy.entity.FavoriteFilm;
import com.dicoding.picodiploma.academy.entity.FavoriteTvShows;
import com.squareup.picasso.Picasso;

public class FavoriteTvShowsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_favorite_tvshows_detail );
        setTitle ( "Detail Film" );

        TextView tvJudul = findViewById ( R.id.judul_film );
        TextView tvDeskripsi = findViewById ( R.id.deskripsi_film );
        TextView tvRilis = findViewById ( R.id.rilis_film );
        ImageView tvFotoDetail = findViewById ( R.id.foto_detail );

        FavoriteTvShows favoriteTvShows = getIntent ().getParcelableExtra ( "myData" );

        if (favoriteTvShows != null) {

            String url = "https://image.tmdb.org/t/p/w500" + favoriteTvShows.getGambarFilm ();

            tvJudul.setText ( favoriteTvShows.getNamaFilm () );
            tvDeskripsi.setText ( favoriteTvShows.getDeskripsiFilm () );
            tvRilis.setText ( favoriteTvShows.getRilisFilm () );
            Picasso.get ().load ( url ).into ( tvFotoDetail );
        }
    }
}
