package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_ProfilGuru;

import java.util.List;

public class Response_ProfilGuru {

    private int kode;
    private String pesan;
    private List<ModelData_ProfilGuru>data;

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

    public List<ModelData_ProfilGuru> getData() {
        return data;
    }

    public void setData(List<ModelData_ProfilGuru> data) {
        this.data = data;
    }
}
