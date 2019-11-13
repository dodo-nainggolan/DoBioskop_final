package com.example.favoritecatalog;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class FavoriteDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_favorite_film_detail );
        setTitle ( "Detail Film" );

        TextView tvJudul = findViewById ( R.id.judul_film );
        TextView tvDeskripsi = findViewById ( R.id.deskripsi_film );
        TextView tvRilis = findViewById ( R.id.rilis_film );
        ImageView tvFotoDetail = findViewById ( R.id.foto_detail );

        FavoriteFilm favoriteFilm = getIntent ().getParcelableExtra ( "myData" );

        if (favoriteFilm != null) {

            String url = "https://image.tmdb.org/t/p/w500" + favoriteFilm.getGambarFilm ();

            tvJudul.setText ( favoriteFilm.getNamaFilm () );
            tvDeskripsi.setText ( favoriteFilm.getDeskripsiFilm () );
            tvRilis.setText ( favoriteFilm.getRilisFilm () );
            Picasso.get ().load ( url ).into ( tvFotoDetail );
        }
    }
}
