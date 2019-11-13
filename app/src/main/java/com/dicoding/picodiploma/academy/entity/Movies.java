package com.dicoding.picodiploma.academy.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class Movies implements Parcelable {

    public static final Creator<Movies> CREATOR = new Creator<Movies> () {
        @Override
        public Movies createFromParcel(Parcel in) {
            return new Movies ( in );
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
    private int id;
    private String namaFilm;
    private String rilisFilm;
    private String deskripsiFilm;
    private String gambarFilm;

    public Movies(int id, String namaFilm, String rilisFilm, String deskripsiFilm, String gambarFilm) {
        this.id = id;
        this.namaFilm = namaFilm;
        this.rilisFilm = rilisFilm;
        this.deskripsiFilm = deskripsiFilm;
        this.gambarFilm = gambarFilm;
    }

    protected Movies(Parcel in) {
        id = in.readInt ();
        namaFilm = in.readString ();
        rilisFilm = in.readString ();
        deskripsiFilm = in.readString ();
        gambarFilm = in.readString ();

    }

    public Movies(JSONObject object) {
        try {
            int id = object.getInt ( "id" );
            String namaFilm = object.getString ( "title" );
            String rilisFilm = object.getString ( "release_date" );
            String deskripsiFilm = object.getString ( "overview" );
            String gambarFilm = object.getString ( "poster_path" );

            this.id = id;
            this.namaFilm = namaFilm;
            this.gambarFilm = gambarFilm;
            this.deskripsiFilm = deskripsiFilm;
            this.rilisFilm = rilisFilm;
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public Movies() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaFilm() {
        return namaFilm;
    }

    public void setNamaFilm(String namaFilm) {
        this.namaFilm = namaFilm;
    }

    public String getRilisFilm() {
        return rilisFilm;
    }

    public void setRilisFilm(String rilisFilm) {
        this.rilisFilm = rilisFilm;
    }

    public String getDeskripsiFilm() {
        return deskripsiFilm;
    }

    public void setDeskripsiFilm(String deskripsiFilm) {
        this.deskripsiFilm = deskripsiFilm;
    }

    public String getGambarFilm() {
        return gambarFilm;
    }

    public void setGambarFilm(String gambarFilm) {
        this.gambarFilm = gambarFilm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt ( id );
        dest.writeString ( namaFilm );
        dest.writeString ( rilisFilm );
        dest.writeString ( deskripsiFilm );
        dest.writeString ( gambarFilm );
    }


}
