package com.tioprasetioa.www.mtsn3rohul.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model_DetailBuku {
    @Expose
    @SerializedName("biblio_id")
    private int biblio_id;

    @Expose
    @SerializedName("edition")
    private String edition;

    @Expose
    @SerializedName("isbn_issn")
    private String isbn_issn;

    @Expose
    @SerializedName("publish_year")
    private String publish_year;

    @Expose
    @SerializedName("collation")
    private String collation;

    @Expose
    @SerializedName("language_id")
    private String languange_id;

    @Expose
    @SerializedName("notes")
    private String notes;

    public int getBiblio_id() {
        return biblio_id;
    }

    public void setBiblio_id(int biblio_id) {
        this.biblio_id = biblio_id;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public String getIsbn_issn() {
        return isbn_issn;
    }

    public void setIsbn_issn(String isbn_issn) {
        this.isbn_issn = isbn_issn;
    }

    public String getPublish_year() {
        return publish_year;
    }

    public void setPublish_year(String publish_year) {
        this.publish_year = publish_year;
    }

    public String getCollation() {
        return collation;
    }

    public void setCollation(String collation) {
        this.collation = collation;
    }

    public String getLanguange_id() {
        return languange_id;
    }

    public void setLanguange_id(String languange_id) {
        this.languange_id = languange_id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
