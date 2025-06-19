package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_Data_Kategori;

import java.util.List;

public class Response_Kategori {
    private List<Model_Data_Kategori>data;

    public List<Model_Data_Kategori> getData() {
        return data;
    }

    public void setData(List<Model_Data_Kategori> data) {
        this.data = data;
    }
}
