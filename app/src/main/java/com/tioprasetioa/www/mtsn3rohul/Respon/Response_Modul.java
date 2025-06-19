package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_Modul;

import java.util.List;

public class Response_Modul {
    private int kode;
    private List<Model_Modul>data;

    public int getKode() {
        return kode;
    }

    public void setKode(int kode) {
        this.kode = kode;
    }

    public List<Model_Modul> getData() {
        return data;
    }

    public void setData(List<Model_Modul> data) {
        this.data = data;
    }
}
