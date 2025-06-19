package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_DetailListLoan;

import java.util.List;

public class Response_DetailListLoans {
    @Expose
    @SerializedName("loans")//
    private List<ModelData_DetailListLoan>loans;

    public List<ModelData_DetailListLoan> getLoans() {
        return loans;
    }

    public void setLoans(List<ModelData_DetailListLoan> loans) {
        this.loans = loans;
    }
}
