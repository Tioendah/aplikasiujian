package com.tioprasetioa.www.mtsn3rohul.Model.ujian;

import com.google.gson.annotations.SerializedName;

public class Data_MapelAdmin {

	@SerializedName("id")
	private String id;

	@SerializedName("mapel")
	private String mapel;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setMapel(String mapel){
		this.mapel = mapel;
	}

	public String getMapel(){
		return mapel;
	}
}