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

import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.activity.TvShowsDetailActivity;
import com.dicoding.picodiploma.academy.db.FavoriteTvShowsHelper;
import com.dicoding.picodiploma.academy.entity.TvShows;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

import static com.dicoding.picodiploma.academy.db.DatabaseContractTvShows.MoviesColumn._ID;
import static com.dicoding.picodiploma.academy.db.DatabaseContractTvShows.MoviesColumn.DESKRIPSI_FILM;
import static com.dicoding.picodiploma.academy.db.DatabaseContractTvShows.MoviesColumn.GAMBAR_FILM;
import static com.dicoding.picodiploma.academy.db.DatabaseContractTvShows.MoviesColumn.JUDUL_FILM;
import static com.dicoding.picodiploma.academy.db.DatabaseContractTvShows.MoviesColumn.RILIS_FILM;

public class TvShowsAdapter extends RecyclerView.Adapter<TvShowsAdapter.GridViewHolder> {

    public final TvShows tvShows = new TvShows ();
    private ArrayList<TvShows> listTvShows = new ArrayList<> ();
    private String judulFilm, rilisFilm, deskripsiFilm, gambarFilm;
    private FavoriteTvShowsHelper favoriteTvshowsHelper;
    private int idFilm;

    public TvShowsAdapter() {

    }

    public void setData(ArrayList<TvShows> items) {
        listTvShows.clear ();
        listTvShows.addAll ( items );
        notifyDataSetChanged ();
    }

    @NonNull
    @Override
    public GridViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from ( viewGroup.getContext () ).inflate ( R.layout.grid_view_tv_shows, viewGroup, false );
        return new GridViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final GridViewHolder GridViewHolder, int position) {
        String temp = listTvShows.get ( position ).getGambarFilm ();
        favoriteTvshowsHelper = FavoriteTvShowsHelper.getInstance ( null );

        String url = "https://image.tmdb.org/t/p/w185" + temp;

        GridViewHolder.tvJudul.setText ( listTvShows.get ( position ).getNamaFilm () );
        Picasso.get ().load ( url ).into ( GridViewHolder.Gambar );

        GridViewHolder.itemView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Toast.makeText ( GridViewHolder.itemView.getContext (), "Kamu memilih " +
                        listTvShows.get ( GridViewHolder.getAdapterPosition () ).getNamaFilm (), Toast.LENGTH_SHORT ).show ();

                int cv = GridViewHolder.getAdapterPosition ();

                ambildata ( cv );

                Intent intent = new Intent ( v.getContext (), TvShowsDetailActivity.class );
                intent.putExtra ( "myData", tvShows );
                Log.e(TAG, "onClick: "+intent.getParcelableArrayExtra("myData") );
                v.getContext ().startActivity ( intent );

            }
        } );
        GridViewHolder.btnFav.setOnClickListener ( new View.OnClickListener () {

            @Override
            public void onClick(View v) {
                Toast.makeText ( GridViewHolder.btnFav.getContext (), "FAVORITE", Toast.LENGTH_SHORT ).show ();

                int cv = GridViewHolder.getPosition ();
                ambildata ( cv );

                ContentValues values = new ContentValues ();

                values.put ( _ID, idFilm );
                values.put ( JUDUL_FILM, judulFilm );
                values.put ( DESKRIPSI_FILM, deskripsiFilm );
                values.put ( RILIS_FILM, rilisFilm );
                values.put ( GAMBAR_FILM, gambarFilm );

                favoriteTvshowsHelper.insert ( values );
            }
        } );

    }

    @Override
    public int getItemCount() {
        return listTvShows.size ();
    }

    public void ambildata(int cv) {

        tvShows.setIdFilm ( listTvShows.get ( cv ).getIdFilm () );
        tvShows.setNamaFilm ( listTvShows.get ( cv ).getNamaFilm () );
        tvShows.setDeskripsiFilm ( listTvShows.get ( cv ).getDeskripsiFilm () );
        tvShows.setRilisFilm ( listTvShows.get ( cv ).getRilisFilm () );
        tvShows.setGambarFilm ( listTvShows.get ( cv ).getGambarFilm () );

        idFilm = tvShows.getIdFilm ();
        judulFilm = tvShows.getNamaFilm ();
        rilisFilm = tvShows.getRilisFilm ();
        deskripsiFilm = tvShows.getDeskripsiFilm ();
        gambarFilm = tvShows.getGambarFilm ();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        public ImageView Gambar;
        public Button btnFav;
        TextView tvJudul, tvRilis, tvDeskripsi;

        GridViewHolder(View itemView) {
            super ( itemView );
            Gambar = itemView.findViewById ( R.id.foto_film );
            tvJudul = itemView.findViewById ( R.id.judul_film );
            tvRilis = itemView.findViewById ( R.id.rilis_film );
            tvDeskripsi = itemView.findViewById ( R.id.deskripsi_film );
            btnFav = itemView.findViewById ( R.id.btn_fav_tv );
        }
    }
}
