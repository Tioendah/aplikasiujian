package com.tioprasetioa.www.mtsn3rohul.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

public class Btn_Whatsapp extends AppCompatActivity {
    public void Whatsapp(Context context,String url, String message){
        Intent intent = null, chooser = null;
        intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        chooser = Intent.createChooser(intent,message);
        context.startActivity(chooser);
    }
}
