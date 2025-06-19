package com.tioprasetioa.www.mtsn3rohul.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Sharef_Pref_Intro {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor editor;
    private final Context context;

    int PRIVATEMODE = 0;

    private static final String KEY_FirstTime_launch = "FirstTime_launch";
    private static final String KEY_NAME = "NAME";

    //untuk pengecekan untuk pertama kali muncul atau tidak
    //pengecekan dilakukan oleh metod boolean
    public Sharef_Pref_Intro(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences(KEY_NAME, PRIVATEMODE);
        editor = sharedPreferences.edit();
    }

    //ketika diset true maka akan mekseskusi
    public void setPertamakali(boolean pertamakali){
        editor.putBoolean(KEY_FirstTime_launch, pertamakali);
        editor.commit();
    }
    public boolean jikapertamakalimuncul(){
        return sharedPreferences.getBoolean(KEY_FirstTime_launch,true);
    }
    public void reset(){
        editor.clear();
        editor.commit();
    }
}
