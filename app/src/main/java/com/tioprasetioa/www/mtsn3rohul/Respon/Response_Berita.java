package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Berita;

import java.util.List;

public class Response_Berita {
    private int kode;
    private String pesan;
    private List<Model_data_Berita>data;

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

    public List<Model_data_Berita> getData() {
        return data;
    }

    public void setData(List<Model_data_Berita> data) {
        this.data = data;
    }
}
