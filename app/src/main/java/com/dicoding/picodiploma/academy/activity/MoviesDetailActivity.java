package com.dicoding.picodiploma.academy.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.entity.Movies;
import com.squareup.picasso.Picasso;

public class MoviesDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_movie_detail );
        setTitle ( "Detail Film" );

        TextView tvJudul = findViewById ( R.id.judul_film );
        TextView tvDeskripsi = findViewById ( R.id.deskripsi_film );
        TextView tvRilis = findViewById ( R.id.rilis_film );
        ImageView tvFotoDetail = findViewById ( R.id.foto_detail );

        Movies movies = getIntent ().getParcelableExtra ( "dataMovies" );
        Log.e("id", "onCreate: "+movies.getId() );
        Log.e("deskripsi", "onCreate: "+movies.getDeskripsiFilm() );
        Log.e("nama", "onCreate: "+movies.getNamaFilm() );
        Log.e("rilis", "onCreate: "+movies.getRilisFilm() );
        Log.e("gambar", "onCreate: "+movies.getGambarFilm() );

        if (movies != null) {

            String url = "https://image.tmdb.org/t/p/w500" + movies.getGambarFilm ();

            tvJudul.setText ( movies.getNamaFilm () );
            tvDeskripsi.setText ( movies.getDeskripsiFilm () );
            tvRilis.setText ( movies.getRilisFilm () );
            Picasso.get ().load ( url ).into ( tvFotoDetail );
        }
//        else if (tvShowsParcelable != null) {
//
//            String url = "https://image.tmdb.org/t/p/w500" + tvShowsParcelable.getGambarFilm ();
//
//            tvJudul.setText ( tvShowsParcelable.getNamaFilm () );
//            tvDeskripsi.setText ( tvShowsParcelable.getDeskripsiFilm () );
//            Picasso.get ().load ( url ).into ( tvFotoDetail );
//        }
    }
}
