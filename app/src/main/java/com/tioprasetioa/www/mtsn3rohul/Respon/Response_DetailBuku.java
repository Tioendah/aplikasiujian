package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_DetailBuku;

import java.util.List;

public class Response_DetailBuku {
    @Expose
    @SerializedName("biblio")
    private List<Model_DetailBuku>biblio;

    public List<Model_DetailBuku> getBiblio() {
        return biblio;
    }

    public void setBiblio(List<Model_DetailBuku> biblio) {
        this.biblio = biblio;
    }
}
