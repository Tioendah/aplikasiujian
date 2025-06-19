package com.tioprasetioa.www.mtsn3rohul.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.MainActivity;

import java.util.Random;

public class FirebaseService extends FirebaseMessagingService {
    private static final String CHANNEL_ID = "FirebaseServiceChannel";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);

        // Simpan status notifikasi di shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("news", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("news_key", "notif");
        editor.apply();

        // Ambil data dari pesan Firebase
        String title = message.getNotification() != null ? message.getNotification().getTitle() : message.getData().get("title");
        String body = message.getNotification() != null ? message.getNotification().getBody() : message.getData().get("body");

        // Jika title atau body kosong, beri nilai default
        if (title == null) title = "MTsN 3 Rokan Hulu";
        if (body == null) body = "Pesan baru dari aplikasi.";

        // Kirim notifikasi
        sendNotification(title, body);
    }

    private void sendNotification(String title, String body) {
        // Intent untuk membuka MainActivity saat notifikasi diklik
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Buat Notification Manager untuk mengelola notifikasi
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(manager); // Buat channel notifikasi jika di Android O atau lebih
        }

        // Buat notifikasi
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body));

        // Kirim notifikasi
        if (manager != null) {
            manager.notify(new Random().nextInt(), builder.build());
        }

        // Putar suara notifikasi
        playNotificationSound();
    }

    private void playNotificationSound() {
        try {
            // Ambil suara notifikasi dari file resources
            Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.m3rnews);
            Ringtone r = RingtoneManager.getRingtone(this, sound);
            r.play();
        } catch (Exception e) {
            Log.e("FirebaseService", "Error playing notification sound", e);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager notificationManager) {
        // Membuat channel notifikasi untuk Android Oreo dan lebih tinggi
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "Firebase Notifications", NotificationManager.IMPORTANCE_HIGH);
        notificationChannel.setDescription("Channel for Firebase notifications");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.WHITE);

        notificationManager.createNotificationChannel(notificationChannel);
    }
}
