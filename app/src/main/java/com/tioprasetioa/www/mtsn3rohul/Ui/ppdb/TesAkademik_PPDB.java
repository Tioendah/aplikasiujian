package com.tioprasetioa.www.mtsn3rohul.Ui.ppdb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

public class TesAkademik_PPDB extends AppCompatActivity {
    private static final String KEY_URL = "https://mtsn3rokanhulu.sch.id/api/api_tesakademikppdb.php";
    private CardView sim, ujian;
    String nama, kelas;
    SharedPreferences sharedPreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tes_akademik_ppdb);


        sim = findViewById(R.id.cv_akdm);
        ujian = findViewById(R.id.cv_ujianakdm);
        data(KEY_URL,"status");

    }
    private void data(String link, String status) {
        sharedPreferences = getApplicationContext().getSharedPreferences("Login2024", Context.MODE_PRIVATE);
        nama = sharedPreferences.getString("nama2024", "");
        Utils utils = new Utils();

        utils.VolleyString(TesAkademik_PPDB.this, link, status, new Utils.VolleyDataString() {
            @Override
            public void onError(String errorMesage) {

            }

            @Override
            public void onSuccess(String result) {
                if (result == null || result.isEmpty()) {
                    showToast("Tidak ada jaringan, \nPeriksa Koneksi Anda!");
                    return;
                }

                Utils utils1 = new Utils();
                Utils utils3 = new Utils();

                if (result.equals("0")) {
                    fetchSimulasi(utils1);
                    setUjianButton("Ujian Belum Dimulai");
                    if (nama.equals("TIOPRASETIO36")){
                        fetchSimulasi(utils1);
                        fetchUjian(utils3);
                    }
                } else if (nama.equals("TIOPRASETIO36")) {
                    fetchSimulasi(utils1);
                    fetchUjian(utils3);
                } else if (result.equals("1")) {
                    fetchUjian(utils3);
                } else {
                    showToast("Tidak ada jaringan, \nPeriksa Koneksi Anda!");
                }
            }
        });
    }

    private void fetchSimulasi(Utils utils) {
        utils.VolleyString(getApplicationContext(), KEY_URL, "simulasi", new Utils.VolleyDataString() {
            @Override
            public void onError(String errorMesage) {

            }

            @Override
            public void onSuccess(String resul) {
                if (resul != null) {
                    sim.setOnClickListener(v -> {
                        Intent intent = new Intent(TesAkademik_PPDB.this, Ujian_PPdb.class);
                        intent.putExtra("sim", resul);
                        startActivity(intent);
                    });
                }
            }
        });
    }

    private void fetchUjian(Utils utils) {
        utils.VolleyString(getApplicationContext(), KEY_URL, "ujian", new Utils.VolleyDataString() {
            @Override
            public void onError(String errorMesage) {

            }

            @Override
            public void onSuccess(String result) {
                if (result != null) {
                    ujian.setOnClickListener(v -> {
                        Intent intent = new Intent(TesAkademik_PPDB.this, Ujian_PPdb.class);
                        intent.putExtra("ujian", result);
                        startActivity(intent);
                    });
                }
            }
        });
    }

    private void setUjianButton(String message) {
        ujian.setOnClickListener(v -> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}