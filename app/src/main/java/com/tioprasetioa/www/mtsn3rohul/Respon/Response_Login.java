package com.tioprasetioa.www.mtsn3rohul.Respon;

import com.google.gson.annotations.SerializedName;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Login_Data;

public class Response_Login {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private Model_Login_Data modelLoginData;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setData(Model_Login_Data modelLoginData){
		this.modelLoginData = modelLoginData;
	}

	public Model_Login_Data getData(){
		return modelLoginData;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}