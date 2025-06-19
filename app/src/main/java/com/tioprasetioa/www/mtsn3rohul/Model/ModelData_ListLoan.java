package com.tioprasetioa.www.mtsn3rohul.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelData_ListLoan implements Parcelable {
    private String member_id;
    private int is_return;
    private String item_code;
    private String loan_date;//tanggal peminjaman
    private String return_date;//tanggal pengembalian
    private String mapel;
    private String due_date;

    public ModelData_ListLoan(){

    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public int getIs_return() {
        return is_return;
    }

    public void setIs_return(int is_return) {
        this.is_return = is_return;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getLoan_date() {
        return loan_date;
    }

    public void setLoan_date(String loan_date) {
        this.loan_date = loan_date;
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

    public String getDue_date() {
        return due_date;
    }

    public void setDue_date(String due_date) {
        this.due_date = due_date;
    }

    protected ModelData_ListLoan(Parcel in) {
        member_id = in.readString();
        is_return = in.readInt();
        item_code = in.readString();
        loan_date = in.readString();
        return_date = in.readString();
        mapel = in.readString();
        due_date = in.readString();
    }

    public static final Creator<ModelData_ListLoan> CREATOR = new Creator<ModelData_ListLoan>() {
        @Override
        public ModelData_ListLoan createFromParcel(Parcel in) {
            return new ModelData_ListLoan(in);
        }

        @Override
        public ModelData_ListLoan[] newArray(int size) {
            return new ModelData_ListLoan[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(member_id);
        parcel.writeInt(is_return);
        parcel.writeString(item_code);
        parcel.writeString(loan_date);
        parcel.writeString(return_date);
        parcel.writeString(mapel);
        parcel.writeString(due_date);
    }
}
