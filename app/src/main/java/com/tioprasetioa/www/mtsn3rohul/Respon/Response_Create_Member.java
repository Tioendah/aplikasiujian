package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_Data_Create_Member;

import java.util.List;

public class Response_Create_Member {
    private List<Model_Data_Create_Member>data;
    private String pesan;

    public List<Model_Data_Create_Member> getData() {
        return data;
    }

    public void setData(List<Model_Data_Create_Member> data) {
        this.data = data;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }
}
