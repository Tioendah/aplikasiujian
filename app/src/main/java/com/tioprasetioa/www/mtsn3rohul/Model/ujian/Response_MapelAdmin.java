package com.tioprasetioa.www.mtsn3rohul.Model.ujian;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response_MapelAdmin{

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private List<Data_MapelAdmin> data;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setData(List<Data_MapelAdmin> data){
		this.data = data;
	}

	public List<Data_MapelAdmin> getData(){
		return data;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}