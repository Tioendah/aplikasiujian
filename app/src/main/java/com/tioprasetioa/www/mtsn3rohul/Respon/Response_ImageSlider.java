package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Image_Slider;

import java.util.List;

public class Response_ImageSlider {
    private int kode;
    private String pesan;
    private List<Model_data_Image_Slider>data;

    public Response_ImageSlider(int kode, String pesan, List<Model_data_Image_Slider> data) {
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

    public List<Model_data_Image_Slider> getData() {
        return data;
    }

    public void setData(List<Model_data_Image_Slider> data) {
        this.data = data;
    }
}
