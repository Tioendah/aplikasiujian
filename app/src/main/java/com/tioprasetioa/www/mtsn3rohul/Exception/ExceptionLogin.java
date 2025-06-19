package com.tioprasetioa.www.mtsn3rohul.Exception;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_Login_Data;

public class ExceptionLogin {
    public static void validasi(Model_Login_Data model_login_data) throws Exception {
        if (model_login_data.getMemberId2() == null) {
            throw new Exception("Tidak Boleh Kosong");
        }
    }
}
