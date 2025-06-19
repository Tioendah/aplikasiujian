package com.tioprasetioa.www.mtsn3rohul.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Model_Data_Kategori implements Parcelable {
    private int biblio_id;
    private String title;
    private String author;
    private String image;
    private String publisher;


    public Model_Data_Kategori(){

    }

    public int getBiblio_id() {
        return biblio_id;
    }

    public void setBiblio_id(int biblio_id) {
        this.biblio_id = biblio_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public static Creator<Model_Data_Kategori> getCREATOR() {
        return CREATOR;
    }

    protected Model_Data_Kategori(Parcel in) {
        biblio_id = in.readInt();
        title = in.readString();
        author = in.readString();
        image = in.readString();
        publisher = in.readString();
    }

    public static final Creator<Model_Data_Kategori> CREATOR = new Creator<Model_Data_Kategori>() {
        @Override
        public Model_Data_Kategori createFromParcel(Parcel in) {
            return new Model_Data_Kategori(in);
        }

        @Override
        public Model_Data_Kategori[] newArray(int size) {
            return new Model_Data_Kategori[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(biblio_id);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(image);
        parcel.writeString(publisher);
    }
}
