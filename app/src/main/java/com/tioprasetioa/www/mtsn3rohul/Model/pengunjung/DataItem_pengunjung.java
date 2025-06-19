package com.tioprasetioa.www.mtsn3rohul.Model.pengunjung;

import com.google.gson.annotations.SerializedName;

public class DataItem_pengunjung {

	@SerializedName("pengunjung")
	private String pengunjung;

	@SerializedName("tahun")
	private String tahun;

	public String getTahun() {
		return tahun;
	}

	public void setTahun(String tahun) {
		this.tahun = tahun;
	}

	@SerializedName("id")
	private String id;

	public void setPengunjung(String pengunjung){
		this.pengunjung = pengunjung;
	}

	public String getPengunjung(){
		return pengunjung;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}
}