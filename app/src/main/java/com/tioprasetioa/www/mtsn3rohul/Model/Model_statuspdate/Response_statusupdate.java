package com.tioprasetioa.www.mtsn3rohul.Model.Model_statuspdate;

import java.util.List;

public class Response_statusupdate {
    private int kode;
    private String pesan;
    private List<Model_StatusUpdate> data;

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

    public List<Model_StatusUpdate> getData() {
        return data;
    }

    public void setData(List<Model_StatusUpdate> data) {
        this.data = data;
    }
}
