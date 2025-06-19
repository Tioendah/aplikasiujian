package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_DetailListLoan2;

import java.util.List;

public class Response_DetailListLoanss {
    @Expose
    @SerializedName("loans")
    private List<ModelData_DetailListLoan2>loans;

    public List<ModelData_DetailListLoan2> getLoans() {
        return loans;
    }

    public void setLoans(List<ModelData_DetailListLoan2> loans) {
        this.loans = loans;
    }
}
