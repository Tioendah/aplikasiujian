package com.tioprasetioa.www.mtsn3rohul.Model.SiswaCurang;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("guru")
	private String guru;

	@SerializedName("nama")
	private String nama;

	@SerializedName("kelas")
	private String kelas;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("id")
	private String id;

	@SerializedName("mapel")
	private String mapel;

	public void setGuru(String guru){
		this.guru = guru;
	}

	public String getGuru(){
		return guru;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setKelas(String kelas){
		this.kelas = kelas;
	}

	public String getKelas(){
		return kelas;
	}

	public void setWaktu(String waktu){
		this.waktu = waktu;
	}

	public String getWaktu(){
		return waktu;
	}

	public void setKategori(String kategori){
		this.kategori = kategori;
	}

	public String getKategori(){
		return kategori;
	}

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