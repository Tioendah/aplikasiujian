package com.tioprasetioa.www.mtsn3rohul.Generic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResponse<T> {
    @Expose
    @SerializedName("code")
    private int code;

    @Expose
    @SerializedName("message")
    private String message;

    @Expose
    @SerializedName("data")
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
