package com.dicoding.picodiploma.academy.adapter;

import android.content.ContentValues;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.picodiploma.academy.MoviesDetailActivity;
import com.dicoding.picodiploma.academy.entity.Movies;
import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.db.FavoriteFilmHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.dicoding.picodiploma.academy.db.DatabaseContractFilm.MoviesColumn.DESKRIPSI_FILM;
import static com.dicoding.picodiploma.academy.db.DatabaseContractFilm.MoviesColumn.GAMBAR_FILM;
import static com.dicoding.picodiploma.academy.db.DatabaseContractFilm.MoviesColumn._ID;
import static com.dicoding.picodiploma.academy.db.DatabaseContractFilm.MoviesColumn.JUDUL_FILM;
import static com.dicoding.picodiploma.academy.db.DatabaseContractFilm.MoviesColumn.RILIS_FILM;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.CardViewViewHolder> {

    public final Movies movies = new Movies ();
    private ArrayList<Movies> listMovies = new ArrayList<> ();
    private String judulFilm, rilisFilm, deskripsiFilm, gambarFilm;
    private FavoriteFilmHelper favoriteFilmHelper;
    private int idFilm;

    public MoviesAdapter() {

    }

    public void setData(ArrayList<Movies> items) {
        listMovies.clear ();
        listMovies.addAll ( items );
        notifyDataSetChanged ();
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from ( viewGroup.getContext () ).inflate ( R.layout.card_view_movies_layout, viewGroup, false );
        return new CardViewViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder CardViewViewHolder, int position) {

        String temp = listMovies.get ( position ).getGambarFilm ();
        String url = "https://image.tmdb.org/t/p/w185" + temp;

        favoriteFilmHelper = FavoriteFilmHelper.getInstance ( null );

        CardViewViewHolder.tvJudul.setText ( listMovies.get ( position ).getNamaFilm () );
        CardViewViewHolder.tvRilis.setText ( listMovies.get ( position ).getRilisFilm () );
        CardViewViewHolder.tvDeskripsi.setText ( listMovies.get ( position ).getDeskripsiFilm () );

        Picasso.get ().load ( url ).into ( CardViewViewHolder.Gambar );

        CardViewViewHolder.itemView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( CardViewViewHolder.itemView.getContext (), "Kamu memilih " +
                        listMovies.get ( CardViewViewHolder.getAdapterPosition () ).getNamaFilm (), Toast.LENGTH_SHORT ).show ();

                int cv = CardViewViewHolder.getPosition ();
                ambildata ( cv );

                Intent intent = new Intent ( v.getContext (), MoviesDetailActivity.class );
                intent.putExtra ( "dataMovies", movies );
                Log.e(TAG, "onClick: "+movies.getDeskripsiFilm() );
                v.getContext ().startActivity ( intent );

            }
        } );

        CardViewViewHolder.btnFav.setOnClickListener ( new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Toast.makeText ( CardViewViewHolder.btnFav.getContext (), "FAVORITE", Toast.LENGTH_SHORT ).show ();
                int cv = CardViewViewHolder.getPosition ();
                ambildata ( cv );

                ContentValues values = new ContentValues ();

                values.put ( _ID, idFilm );
                values.put ( JUDUL_FILM, judulFilm );
                values.put ( DESKRIPSI_FILM, deskripsiFilm );
                values.put ( RILIS_FILM, rilisFilm );
                values.put ( GAMBAR_FILM, gambarFilm );

                favoriteFilmHelper.insert ( values );

            }
        } );
    }

    @Override
    public int getItemCount() {
        return listMovies.size ();
    }

    public void ambildata(int cv) {

        movies.setId ( listMovies.get ( cv ).getId () );
        movies.setNamaFilm ( listMovies.get ( cv ).getNamaFilm () );
        movies.setDeskripsiFilm ( listMovies.get ( cv ).getDeskripsiFilm () );
        movies.setRilisFilm ( listMovies.get ( cv ).getRilisFilm () );
        movies.setGambarFilm ( listMovies.get ( cv ).getGambarFilm () );

        Log.e(TAG, "id: "+movies.getId() );
        Log.e(TAG, "nama "+movies.getNamaFilm() );
        Log.e(TAG, "desk: "+movies.getDeskripsiFilm() );
        Log.e(TAG, "rilis: "+movies.getRilisFilm() );
        Log.e(TAG, "gambar: "+movies.getGambarFilm() );

        idFilm = movies.getId ();
        judulFilm = movies.getNamaFilm ();
        rilisFilm = movies.getRilisFilm ();
        deskripsiFilm = movies.getDeskripsiFilm ();
        gambarFilm = movies.getGambarFilm ();

        Log.e(TAG, "id: "+idFilm );
        Log.e(TAG, "nama "+judulFilm );
        Log.e(TAG, "desk: "+rilisFilm );
        Log.e(TAG, "rilis: "+deskripsiFilm );
        Log.e(TAG, "gambar: "+gambarFilm );


    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        public ImageView Gambar;
        public Button btnFav;
        TextView tvJudul, tvRilis, tvDeskripsi;

        CardViewViewHolder(View itemView) {
            super ( itemView );
            Gambar = itemView.findViewById ( R.id.foto_film );
            tvJudul = itemView.findViewById ( R.id.judul_film );
            tvRilis = itemView.findViewById ( R.id.rilis_film );
            tvDeskripsi = itemView.findViewById ( R.id.deskripsi_film );
            btnFav = itemView.findViewById ( R.id.btn_fav );
        }
    }

}
