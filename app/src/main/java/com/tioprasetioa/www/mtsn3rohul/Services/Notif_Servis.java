package com.tioprasetioa.www.mtsn3rohul.Services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.MainActivity;

public class Notif_Servis extends Service {
    public static final String KEY_Notifikasi = "Notifikasi";
    private Service service;
    @Override
    public void onCreate() {
        super.onCreate();
        membuatnotif();
    }
    private void membuatnotif() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.Q){
            NotificationChannel notificationChannel = new NotificationChannel(KEY_Notifikasi,"MTsN 3 Rohul Notifikasi", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        membuatnotif2();

        return START_NOT_STICKY;
    }
    private void membuatnotif2() {
        Intent intentz = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, intentz, 0);
        Notification notification = new Notification.Builder(this,KEY_Notifikasi)
                .setContentTitle("MTsN 3 Rohul Library")
                .setContentText("Buku Perpustakaan yang Anda Pinjam Jatuh Tempo, Anda Dikenakan Denda jika terlambat")
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT | Notification.FLAG_NO_CLEAR;

        startForeground(1,notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        membuatnotif2();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
