package com.tioprasetioa.www.mtsn3rohul.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelData_DetailListLoan2 {


    @Expose
    @SerializedName("item_code")
    private String item_code;

    @Expose
    @SerializedName("member_id")
    private String member_id;

    @Expose
    @SerializedName("loan_id")
    private String loan_id;

    @Expose
    @SerializedName("loan_date")
    private String loan_date;

    @Expose
    @SerializedName("is_return")
    private String is_return;

    @Expose
    @SerializedName("return_date")
    private String return_date;

    @Expose
    @SerializedName("mapel")
    private String mapel;

    @Expose
    @SerializedName("due_date")
    private String due_date;

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    public String getLoan_date() {
        return loan_date;
    }

    public void setLoan_date(String loan_date) {
        this.loan_date = loan_date;
    }

    public String getIs_return() {
        return is_return;
    }

    public void setIs_return(String is_return) {
        this.is_return = is_return;
    }

    public String getReturn_date() {
        return return_date;
    }

    public void setReturn_date(String return_date) {
        this.return_date = return_date;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }
}
