package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_PerangkatPBM;

import java.util.List;

public class Response_PerangkatPBM {
    private int kode;
    private String pesan;
    private List<Model_PerangkatPBM>data;

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

    public List<Model_PerangkatPBM> getData() {
        return data;
    }

    public void setData(List<Model_PerangkatPBM> data) {
        this.data = data;
    }
}
