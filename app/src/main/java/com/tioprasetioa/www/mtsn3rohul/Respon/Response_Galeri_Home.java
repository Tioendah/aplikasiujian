package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Galeri_Home;

import java.util.List;

public class Response_Galeri_Home {
    private int kode;
    private String pesan;
    private List<Model_data_Galeri_Home> data;

    public Response_Galeri_Home(int kode, String pesan, List<Model_data_Galeri_Home> data) {
        this.kode = kode;
        this.pesan = pesan;
        this.data = data;
    }

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<Model_data_Galeri_Home> getData() {
        return data;
    }

    public void setData(List<Model_data_Galeri_Home> data) {
        this.data = data;
    }
}
