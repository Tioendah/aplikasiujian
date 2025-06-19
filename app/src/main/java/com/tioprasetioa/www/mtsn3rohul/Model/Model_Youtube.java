package com.tioprasetioa.www.mtsn3rohul.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Model_Youtube implements Parcelable {
    private int id;
    private String Judul;
    private String Gambar;
    private String Tanggal;
    private String Url;


    public Model_Youtube(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public static Creator<Model_Youtube> getCREATOR() {
        return CREATOR;
    }

    protected Model_Youtube(Parcel in) {
        id = in.readInt();
        Judul = in.readString();
        Gambar = in.readString();
        Tanggal = in.readString();
        Url = in.readString();
    }

    public static final Creator<Model_Youtube> CREATOR = new Creator<Model_Youtube>() {
        @Override
        public Model_Youtube createFromParcel(Parcel in) {
            return new Model_Youtube(in);
        }

        @Override
        public Model_Youtube[] newArray(int size) {
            return new Model_Youtube[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(Judul);
        parcel.writeString(Gambar);
        parcel.writeString(Tanggal);
        parcel.writeString(Url);
    }
}
