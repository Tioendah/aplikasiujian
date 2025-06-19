package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_ListLoan;

import java.util.List;

public class Response_ListLoan {
    private List<ModelData_ListLoan> data ;
    private String pesan;

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<ModelData_ListLoan> getData() {
        return data;
    }
    public void setData(List<ModelData_ListLoan> data) {
        this.data = data;
    }
}
