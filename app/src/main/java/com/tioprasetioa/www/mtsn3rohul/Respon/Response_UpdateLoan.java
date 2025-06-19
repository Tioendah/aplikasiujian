package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_UpdateLoan;

import java.util.List;
public class Response_UpdateLoan {
    private List<Model_UpdateLoan>loans;
    private String pesan;

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public List<Model_UpdateLoan> getLoans() {
        return loans;
    }

    public void setLoans(List<Model_UpdateLoan> loans) {
        this.loans = loans;
    }
}
