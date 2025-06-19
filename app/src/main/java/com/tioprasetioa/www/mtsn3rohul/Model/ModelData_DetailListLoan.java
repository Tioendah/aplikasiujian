package com.tioprasetioa.www.mtsn3rohul.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelData_DetailListLoan {
    @Expose
    @SerializedName("member_id")
    private String member_id;

    @Expose
    @SerializedName("member_name")
    private String member_name;

    @Expose
    @SerializedName("gender")
    private String gender;

    @Expose
    @SerializedName("member_phone")
    private String member_phone;

    @Expose
    @SerializedName("pin")
    private String pin;//nomor pribadi

    @Expose
    @SerializedName("member_image")
    private String member_image;//nomor pribadi

    public String getMember_image() {
        return member_image;
    }

    public void setMember_image(String member_image) {
        this.member_image = member_image;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMember_phone() {
        return member_phone;
    }

    public void setMember_phone(String member_phone) {
        this.member_phone = member_phone;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
