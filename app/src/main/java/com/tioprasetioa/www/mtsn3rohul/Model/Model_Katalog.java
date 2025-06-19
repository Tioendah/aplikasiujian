package com.tioprasetioa.www.mtsn3rohul.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Model_Katalog implements Parcelable {

    @SerializedName("biblio_id") private int biblio_id;
    @SerializedName("title") private String title;
    @SerializedName("publisher") private String publisher;
    @SerializedName("image") private String image;
    @SerializedName("author") private String author;

    protected Model_Katalog(Parcel in) {
        biblio_id = in.readInt();
        title = in.readString();
        publisher = in.readString();
        image = in.readString();
        author = in.readString();
    }

    public static final Creator<Model_Katalog> CREATOR = new Creator<Model_Katalog>() {
        @Override
        public Model_Katalog createFromParcel(Parcel in) {
            return new Model_Katalog(in);
        }

        @Override
        public Model_Katalog[] newArray(int size) {
            return new Model_Katalog[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(biblio_id);
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeString(image);
        dest.writeString(author);
    }
}
