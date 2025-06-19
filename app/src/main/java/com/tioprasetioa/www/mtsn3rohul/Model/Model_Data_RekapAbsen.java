package com.tioprasetioa.www.mtsn3rohul.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Model_Data_RekapAbsen implements Parcelable {
    private String Id;
    private String Masuk;
    private String Pulang;
    private String DeleteAll;
    private String Hari;

    public String getHari() {
        return Hari;
    }

    public void setHari(String hari) {
        Hari = hari;
    }

    public Model_Data_RekapAbsen(){

    }
    public String getDeleteAll() {
        return DeleteAll;
    }

    public void setDeleteAll(String deleteAll) {
        DeleteAll = deleteAll;
    }

    public Model_Data_RekapAbsen(String id, String masuk, String pulang, String deletall, String hari) {
        Id = id;
        Masuk = masuk;
        Pulang = pulang;
        DeleteAll = deletall;
        Hari = hari;
    }

    protected Model_Data_RekapAbsen(Parcel in) {
        Id = in.readString();
        Masuk = in.readString();
        Pulang = in.readString();
        DeleteAll = in.readString();
        Hari = in.readString();
    }

    public static final Creator<Model_Data_RekapAbsen> CREATOR = new Creator<Model_Data_RekapAbsen>() {
        @Override
        public Model_Data_RekapAbsen createFromParcel(Parcel in) {
            return new Model_Data_RekapAbsen(in);
        }

        @Override
        public Model_Data_RekapAbsen[] newArray(int size) {
            return new Model_Data_RekapAbsen[size];
        }
    };

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getMasuk() {
        return Masuk;
    }

    public void setMasuk(String masuk) {
        Masuk = masuk;
    }

    public String getPulang() {
        return Pulang;
    }

    public void setPulang(String pulang) {
        Pulang = pulang;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Id);
        dest.writeString(Masuk);
        dest.writeString(Pulang);
        dest.writeString(DeleteAll);
        dest.writeString(Hari);
    }
}
