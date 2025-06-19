package com.tioprasetioa.www.mtsn3rohul.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Model_data_Image_Slider implements Parcelable {
    private int id;
    private String Judul;
    private String Hari;
    private String Tanggal;
    private String Gambar;
    private String Url;
    private String Sumber;

    public Model_data_Image_Slider() {
    }

    public Model_data_Image_Slider(int id, String judul, String hari, String tanggal, String gambar, String url, String sumber) {
        this.id = id;
        Judul = judul;
        Hari = hari;
        Tanggal = tanggal;
        Gambar = gambar;
        Url = url;
        Sumber = sumber;
    }

    protected Model_data_Image_Slider(Parcel in) {
        id = in.readInt();
        Judul = in.readString();
        Hari = in.readString();
        Tanggal = in.readString();
        Gambar = in.readString();
        Url = in.readString();
        Sumber = in.readString();
    }

    public static final Creator<Model_data_Image_Slider> CREATOR = new Creator<Model_data_Image_Slider>() {
        @Override
        public Model_data_Image_Slider createFromParcel(Parcel in) {
            return new Model_data_Image_Slider(in);
        }

        @Override
        public Model_data_Image_Slider[] newArray(int size) {
            return new Model_data_Image_Slider[size];
        }
    };

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

    public String getHari() {
        return Hari;
    }

    public void setHari(String hari) {
        Hari = hari;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getGambar() {
        return Gambar;
    }

    public void setGambar(String gambar) {
        Gambar = gambar;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getSumber() {
        return Sumber;
    }

    public void setSumber(String sumber) {
        Sumber = sumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Judul);
        dest.writeString(Hari);
        dest.writeString(Tanggal);
        dest.writeString(Gambar);
        dest.writeString(Url);
        dest.writeString(Sumber);
    }
}
