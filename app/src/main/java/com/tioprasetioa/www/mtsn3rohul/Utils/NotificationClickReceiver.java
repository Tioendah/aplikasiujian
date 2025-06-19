package com.tioprasetioa.www.mtsn3rohul.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.tioprasetioa.www.mtsn3rohul.Ui.MainActivity;

public class NotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Notifikasi diklik!", Toast.LENGTH_SHORT).show();

        // Simpan data ke SharedPreferences
        SharedPreferences sharedPreferences = context.getSharedPreferences("news", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("news_key", "notif");
        editor.apply();

        // Arahkan ke MainActivity
        Intent activityIntent = new Intent(context, MainActivity.class);
        activityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activityIntent);
    }
}