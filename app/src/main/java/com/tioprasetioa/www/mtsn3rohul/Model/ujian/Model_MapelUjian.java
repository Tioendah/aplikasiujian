package com.tioprasetioa.www.mtsn3rohul.Model.ujian;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Model_MapelUjian implements Parcelable {


	public Model_MapelUjian(){

	}
	@SerializedName("guru")
	private String guru;

	@SerializedName("pin_out")
	private int pinOut;

	@SerializedName("pin_in")
	private int pinIn;

	@SerializedName("pin_exit")
	private int pin_exit;
	@SerializedName("kelas")
	private int kelas;

	@SerializedName("link")
	private String link;

	@SerializedName("id")
	private int id;

	@SerializedName("mapel")
	private String mapel;

	@SerializedName("status")
	private int status;

	protected Model_MapelUjian(Parcel in) {
		guru = in.readString();
		pinOut = in.readInt();
		pinIn = in.readInt();
		pin_exit = in.readInt();
		kelas = in.readInt();
		link = in.readString();
		id = in.readInt();
		mapel = in.readString();
		status = in.readInt();
	}

	public static final Creator<Model_MapelUjian> CREATOR = new Creator<Model_MapelUjian>() {
		@Override
		public Model_MapelUjian createFromParcel(Parcel in) {
			return new Model_MapelUjian(in);
		}

		@Override
		public Model_MapelUjian[] newArray(int size) {
			return new Model_MapelUjian[size];
		}
	};

	public String getGuru() {
		return guru;
	}

	public void setGuru(String guru) {
		this.guru = guru;
	}

	public int getPinOut() {
		return pinOut;
	}

	public void setPinOut(int pinOut) {
		this.pinOut = pinOut;
	}

	public int getPinIn() {
		return pinIn;
	}

	public void setPinIn(int pinIn) {
		this.pinIn = pinIn;
	}

	public int getPin_exit() {
		return pin_exit;
	}

	public void setPin_exit(int pin_exit) {
		this.pin_exit = pin_exit;
	}

	public int getKelas() {
		return kelas;
	}

	public void setKelas(int kelas) {
		this.kelas = kelas;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMapel() {
		return mapel;
	}

	public void setMapel(String mapel) {
		this.mapel = mapel;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(@NonNull Parcel dest, int flags) {
		dest.writeString(guru);
		dest.writeInt(pinOut);
		dest.writeInt(pinIn);
		dest.writeInt(pin_exit);
		dest.writeInt(kelas);
		dest.writeString(link);
		dest.writeInt(id);
		dest.writeString(mapel);
		dest.writeInt(status);
	}
}