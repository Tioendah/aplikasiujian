package com.tioprasetioa.www.mtsn3rohul.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Model_data_Galeri_Home implements Parcelable {
    private int id;
    private String Image;
    private String Kategori;
    private String Judul;
    private String Url;

    public Model_data_Galeri_Home(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getKategori() {
        return Kategori;
    }

    public void setKategori(String kategori) {
        Kategori = kategori;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    protected Model_data_Galeri_Home(Parcel in) {
        id = in.readInt();
        Image = in.readString();
        Kategori = in.readString();
        Judul = in.readString();
        Url = in.readString();
    }

    public static final Creator<Model_data_Galeri_Home> CREATOR = new Creator<Model_data_Galeri_Home>() {
        @Override
        public Model_data_Galeri_Home createFromParcel(Parcel in) {
            return new Model_data_Galeri_Home(in);
        }

        @Override
        public Model_data_Galeri_Home[] newArray(int size) {
            return new Model_data_Galeri_Home[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(Image);
        parcel.writeString(Kategori);
        parcel.writeString(Judul);
        parcel.writeString(Url);
    }
}
