package com.tioprasetioa.www.mtsn3rohul.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_Login_Data;

import java.util.HashMap;

public class SharedPreference_Login {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final Context _context;

    public static  final String IS_LOGGED_IN = "isLoggedIIn";
    public static  final String MEMBER_ID = "id";
    public static  final String USERNAME = "USERNAME";
    public static final String KEY_MEMBER_TYPE = "MEMBER_TYPE";
    public static final String KEY_SINCE_DATE = "SINCE_DATE";
    public static final String KEY_GENDER = "GENDER";
    public static final String KEY_EXPIRE = "EXPIRE";

    public SharedPreference_Login(Context context){
        //lokasi penyimpanan
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }
    public void createLoginSesion(Model_Login_Data user){
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(MEMBER_ID,user.getMemberId2());
        editor.putString(USERNAME,user.getMemberName());
        editor.putString(KEY_SINCE_DATE, user.getMember_since_date());
        editor.putString(KEY_MEMBER_TYPE,user.getMemberTypeId());
        editor.putString(KEY_GENDER,user.getGender());
        editor.putString(KEY_EXPIRE,user.getExpireDate());
        editor.commit();
    }
    public HashMap<String, String> getuserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(MEMBER_ID, sharedPreferences.getString(MEMBER_ID,null));
        user.put(USERNAME,sharedPreferences.getString(USERNAME, null));
        user.put(KEY_MEMBER_TYPE,sharedPreferences.getString(KEY_MEMBER_TYPE,null));
        user.put(KEY_SINCE_DATE,sharedPreferences.getString(KEY_SINCE_DATE,null));
        user.put(KEY_SINCE_DATE,sharedPreferences.getString(KEY_SINCE_DATE,null));
        user.put(KEY_GENDER,sharedPreferences.getString(KEY_GENDER,null));
        user.put(KEY_EXPIRE,sharedPreferences.getString(KEY_EXPIRE,null));
        return user;
    }
    //untuk menghapus sesion data
    public void logoutsesion(){
        editor.clear();
        editor.commit();
    }
    //pengecekaan jika orang tersebut sudah login maka tidak perlu login kembali
    public boolean isLoggedin(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }

}
