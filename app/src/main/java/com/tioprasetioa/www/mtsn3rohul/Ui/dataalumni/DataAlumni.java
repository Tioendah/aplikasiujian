package com.tioprasetioa.www.mtsn3rohul.Ui.dataalumni;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DataAlumni extends AppCompatActivity {
    WebView webView;
    SharedPreferences sharedPreferences, introalumni;
    private static final String KEY_URL = "https://www.mtsn3rokanhulu.sch.id/";
    FloatingActionButton fab_create, fab_admin;
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_data_alumni);

        webView = findViewById(R.id.web_alumni);
        fab_create = findViewById(R.id.add_alumni);
        fab_admin = findViewById(R.id.view_admin);

        loadData();

        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.KEY_UJIAN, Context.MODE_PRIVATE);
        String nama = sharedPreferences.getString("nama2024", "");
        String kelas = sharedPreferences.getString("kelas2024", "");
        if (nama.equals(Utils.KEY_VALUE) && kelas.equals("778")) {
            fab_create.setVisibility(View.VISIBLE);
            fab_admin.setVisibility(View.VISIBLE);
        } else {
            fab_create.setVisibility(View.VISIBLE);
            fab_admin.setVisibility(View.GONE);
        }

        introalumni = getApplicationContext().getSharedPreferences("introalumni",Context.MODE_PRIVATE);
        String cek = introalumni.getString("cek","");
        if (cek.isEmpty()){
            Utils.tapview(DataAlumni.this, R.id.add_alumni,"Add Data", "Klik di sini untuk isi data alumni.");
        }

        fab_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DataAlumni.this, Dataalumni_add.class);
                intent.putExtra(Dataalumni_add.KEY_ALUMNI, "add_alumni");
                startActivity(intent);
                SharedPreferences introalumniadd = getSharedPreferences("introalumni", MODE_PRIVATE);
                SharedPreferences.Editor editor = introalumniadd.edit();
                editor.putString("cek","add");
                editor.apply();
            }
        });
        fab_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(DataAlumni.this, Dataalumni_add.class);
                intent.putExtra(Dataalumni_add.KEY_ALUMNI, "admin");
                startActivity(intent);
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void loadData() {
        // Inisialisasi dialog loading
        SweetAlertDialog alertLoading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alertLoading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alertLoading.setTitleText("Loading");
        alertLoading.setCancelable(false);
        alertLoading.show();

        // Konfigurasi WebView
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        // Atur WebViewClient
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.e("SSL_ERROR", "SSL Error: " + error.toString()); // Debug log opsional
            }

            // Ketika halaman selesai dimuat
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                alertLoading.getProgressHelper().stopSpinning();
                alertLoading.dismiss();
            }
        });

        // Mulai muat halaman
        webView.loadUrl("https://mtsn3rokanhulu.sch.id/dataalumni/");
    }
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack(); // Kembali ke halaman sebelumnya
        } else {
            super.onBackPressed();
        }
    }
}