package com.tioprasetioa.www.mtsn3rohul.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Model_data_Berita implements Parcelable {
    private int tulisan_id;
    private String tulisan_judul;
    private String tulisan_tanggal;
    private String tulisan_kategori_nama;
    private String tulisan_views;
    private String tulisan_gambar;
    private String url;

    public Model_data_Berita(Parcel in) {
        tulisan_id = in.readInt();
        tulisan_judul = in.readString();
        tulisan_tanggal = in.readString();
        tulisan_kategori_nama = in.readString();
        tulisan_views = in.readString();
        tulisan_gambar = in.readString();
        url = in.readString();
    }

    public static final Creator<Model_data_Berita> CREATOR = new Creator<Model_data_Berita>() {
        @Override
        public Model_data_Berita createFromParcel(Parcel in) {
            return new Model_data_Berita(in);
        }

        @Override
        public Model_data_Berita[] newArray(int size) {
            return new Model_data_Berita[size];
        }
    };

    public Model_data_Berita() {

    }

    public int getTulisan_id() {
        return tulisan_id;
    }

    public void setTulisan_id(int tulisan_id) {
        this.tulisan_id = tulisan_id;
    }

    public String getTulisan_judul() {
        return tulisan_judul;
    }

    public void setTulisan_judul(String tulisan_judul) {
        this.tulisan_judul = tulisan_judul;
    }

    public String getTulisan_tanggal() {
        return tulisan_tanggal;
    }

    public void setTulisan_tanggal(String tulisan_tanggal) {
        this.tulisan_tanggal = tulisan_tanggal;
    }

    public String getTulisan_kategori_nama() {
        return tulisan_kategori_nama;
    }

    public void setTulisan_kategori_nama(String tulisan_kategori_nama) {
        this.tulisan_kategori_nama = tulisan_kategori_nama;
    }

    public String getTulisan_views() {
        return tulisan_views;
    }

    public void setTulisan_views(String tulisan_views) {
        this.tulisan_views = tulisan_views;
    }

    public String getTulisan_gambar() {
        return tulisan_gambar;
    }

    public void setTulisan_gambar(String tulisan_gambar) {
        this.tulisan_gambar = tulisan_gambar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(tulisan_id);
        dest.writeString(tulisan_judul);
        dest.writeString(tulisan_tanggal);
        dest.writeString(tulisan_kategori_nama);
        dest.writeString(tulisan_views);
        dest.writeString(tulisan_gambar);
        dest.writeString(url);
    }
}
