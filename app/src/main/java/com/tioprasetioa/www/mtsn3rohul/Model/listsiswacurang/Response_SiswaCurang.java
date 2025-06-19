package com.tioprasetioa.www.mtsn3rohul.Model.listsiswacurang;

import java.util.List;

public class Response_SiswaCurang {
    private int kode;
    private String pesan;
    private List<Model_SiswaCurang> data;

    public List<Model_SiswaCurang> getData() {
        return data;
    }

    public void setData(List<Model_SiswaCurang> data) {
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

}
