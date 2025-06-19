package com.tioprasetioa.www.mtsn3rohul.Model.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Parcel_Btn_Kategori implements Parcelable {
    private String kategori;
    public String getKategori() {
        return kategori;
    }

    public Parcel_Btn_Kategori(){

    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public static Creator<Parcel_Btn_Kategori> getCREATOR() {
        return CREATOR;
    }

    protected Parcel_Btn_Kategori(Parcel in) {
        kategori = in.readString();
    }

    public static final Creator<Parcel_Btn_Kategori> CREATOR = new Creator<Parcel_Btn_Kategori>() {
        @Override
        public Parcel_Btn_Kategori createFromParcel(Parcel in) {
            return new Parcel_Btn_Kategori(in);
        }

        @Override
        public Parcel_Btn_Kategori[] newArray(int size) {
            return new Parcel_Btn_Kategori[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kategori);
    }
}
