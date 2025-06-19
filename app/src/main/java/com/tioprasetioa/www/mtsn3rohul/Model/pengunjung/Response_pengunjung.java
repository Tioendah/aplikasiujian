package com.tioprasetioa.www.mtsn3rohul.Model.pengunjung;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Response_pengunjung {

	@SerializedName("pesan")
	private String pesan;

	@SerializedName("data")
	private List<DataItem_pengunjung> data;

	@SerializedName("kode")
	private int kode;

	public void setPesan(String pesan){
		this.pesan = pesan;
	}

	public String getPesan(){
		return pesan;
	}

	public void setData(List<DataItem_pengunjung> data){
		this.data = data;
	}

	public List<DataItem_pengunjung> getData(){
		return data;
	}

	public void setKode(int kode){
		this.kode = kode;
	}

	public int getKode(){
		return kode;
	}
}