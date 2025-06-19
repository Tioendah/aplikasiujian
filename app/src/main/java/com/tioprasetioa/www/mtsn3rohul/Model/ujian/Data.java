package com.tioprasetioa.www.mtsn3rohul.Model.ujian;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

	@SerializedName("kelas")
	private List<Model_MapelUjian> kelas;

	public void setKelas(List<Model_MapelUjian> kelas){
		this.kelas = kelas;
	}

	public List<Model_MapelUjian> getKelas(){
		return kelas;
	}
}