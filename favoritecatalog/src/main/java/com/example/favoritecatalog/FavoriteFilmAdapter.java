package com.example.favoritecatalog;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.favoritecatalog.DatabaseContractFilm.MoviesColumn.CONTENT_URI;

public class FavoriteFilmAdapter extends RecyclerView.Adapter<FavoriteFilmAdapter.CardViewViewHolder> {

    public final FavoriteFilm fav = new FavoriteFilm();
    private ArrayList<FavoriteFilm> listFav = new ArrayList<>();

//    private String judulFilm, rilisFilm, deskripsiFilm, gambarFilm;
//    private int idFilm;

    public FavoriteFilmAdapter() {

    }

    public FavoriteFilmAdapter(ArrayList<FavoriteFilm> listFav) {
        this.listFav = listFav;
        Log.e("UKURAN", "" + listFav.size());
    }

    public ArrayList<FavoriteFilm> getListNotes() {
        return listFav;
    }

    public void setListNotes(ArrayList<FavoriteFilm> listNotes) {

        if (listNotes.size() > 0) {
            this.listFav.clear();
        }
        this.listFav.addAll(listNotes);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_favorite_film_layout, viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int position) {
        String temp = listFav.get(position).getGambarFilm();
        String url = "https://image.tmdb.org/t/p/w185" + temp;

        holder.tvJudul.setText(listFav.get(position).getNamaFilm());
        holder.tvRilis.setText(listFav.get(position).getRilisFilm());
        holder.tvDeskripsi.setText(listFav.get(position).getDeskripsiFilm());

        Picasso.get().load(url).into(holder.gambarFilm);

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                favoriteFilmHelper.deleteById ( String.valueOf ( listFav.get ( position ).getId () ) );

                Uri uri = Uri.parse( DatabaseContractFilm.MoviesColumn.CONTENT_URI + "/" + listFav.get(position).getId() );

                holder.itemView.getContext().getContentResolver().delete(uri, null, null);

                Toast.makeText(holder.itemView.getContext(), "Berhasil menghapus data", Toast.LENGTH_SHORT).show();
                listFav.remove(position);
                notifyDataSetChanged();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(holder.itemView.getContext(), "Kamu memilih " +
                        listFav.get(holder.getAdapterPosition()).getNamaFilm(), Toast.LENGTH_SHORT).show();

                int cv = holder.getPosition();
                ambildata(cv);

                Intent intent = new Intent(v.getContext(), FavoriteDetailActivity.class);
                intent.putExtra("myData", fav);
                v.getContext().startActivity(intent);

            }
        });
    }

    public void ambildata(int cv) {

        fav.setId(listFav.get(cv).getId());
        fav.setNamaFilm(listFav.get(cv).getNamaFilm());
        fav.setDeskripsiFilm(listFav.get(cv).getDeskripsiFilm());
        fav.setRilisFilm(listFav.get(cv).getRilisFilm());
        fav.setGambarFilm(listFav.get(cv).getGambarFilm());

//        int idFilm = fav.getId();
//        String judulFilm = fav.getNamaFilm();
//        String rilisFilm = fav.getRilisFilm();
//        String deskripsiFilm = fav.getDeskripsiFilm();
//        String gambarFilm = fav.getGambarFilm();

    }

    @Override
    public int getItemCount() {
        return listFav.size();
    }


    public class CardViewViewHolder extends RecyclerView.ViewHolder {
        final ImageView gambarFilm;
        TextView tvJudul, tvRilis, tvDeskripsi;
        Button btnRemove;

        CardViewViewHolder(View itemView) {
            super(itemView);
            gambarFilm = itemView.findViewById(R.id.foto_film_fav);
            tvJudul = itemView.findViewById(R.id.judul_film_fav);
            tvRilis = itemView.findViewById(R.id.rilis_film_fav);
            tvDeskripsi = itemView.findViewById(R.id.deskripsi_film_fav);
            btnRemove = itemView.findViewById(R.id.btn_remove_fav);
        }
    }
}