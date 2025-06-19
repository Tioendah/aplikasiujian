package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.tioprasetioa.www.mtsn3rohul.R;

public class Splash_Screen extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Mengatur animasi gambar dengan Glide
        imageView = findViewById(R.id.splash_gif);
        Glide.with(this)
                .load(R.raw.ic_splashgif)
                .fitCenter()
                .into(imageView);

        // Menggunakan Handler untuk menunda eksekusi
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Pindah ke MainActivity
            startActivity(new Intent(Splash_Screen.this, MainActivity.class));

            // Memulai service dengan metode yang sesuai
//            Intent serviceIntent = new Intent(Splash_Screen.this, FirebaseService.class);
//
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                ContextCompat.startForegroundService(Splash_Screen.this, serviceIntent);
//            } else {
//                startService(serviceIntent);
//            }


            // Menutup Splash Screen
            finish();
        }, 2000); // 2000 ms = 2 detik
    }
}
