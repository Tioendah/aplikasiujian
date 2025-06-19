package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_TampilData;

import java.util.List;

public class Response_TampilData {

	@Expose
	@SerializedName("loans")
	private List<ModelData_TampilData> loans;

	public List<ModelData_TampilData> getLoans() {
		return loans;
	}

	public void setLoans(List<ModelData_TampilData> loans) {
		this.loans = loans;
	}
}