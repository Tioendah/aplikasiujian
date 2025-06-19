package com.tioprasetioa.www.mtsn3rohul.Ui.ppdb;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Ujian_PPdb extends AppCompatActivity implements Metode_Interface {
    String getsim, getujian;
    String url ;
    private static final String KEY_SIM = "sim";
    private static final String KEY_UJIAN = "ujian";
    private Button btn_mulai;
    private FloatingActionButton refresh;
    private WebView webView;
    private TextView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ujian_ppdb);
        FindViewById();



        Logic();


    }

    @Override
    public void FindViewById() {
        refresh = findViewById(R.id.refresh_akdm);
        webView = findViewById(R.id.webUjian_akdm);
        title = findViewById(R.id.titleUjian_akdm);
    }

    @Override
    public void Logic() {
        webView();
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(Ujian_PPdb.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Perhatian!");
                pDialog.setContentText("Refresh akan menghilangkan jawaban Anda!");
                pDialog.setConfirmText("Ya");
                pDialog.setCancelText("Tidak");
                pDialog.setCancelable(true);
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        webView();
                        pDialog.dismissWithAnimation();
                    }
                });
                pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pDialog.cancel();
                    }
                });
                pDialog.show();

            }
        });
    }

    private void webView() {
        getsim = getIntent().getStringExtra(KEY_SIM);
        getujian = getIntent().getStringExtra(KEY_UJIAN);

        if (getsim != null && !getsim.isEmpty()) {
            title.setText("Simulasi Ujian");
            url = getsim;
        } else if (getujian != null && !getujian.isEmpty()) {
            title.setText("Tes Akademik");
            url = getujian;
        } else {
            title.setText("Tidak ada data ujian");
            url = "https://default-url.com"; // Atur URL default jika tidak ada data
        }

        SweetAlertDialog alerLoaading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alerLoaading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alerLoaading.setTitleText("Loading");
        alerLoaading.setCancelable(true);
        alerLoaading.show();

        webView.loadUrl(url);
        if (isNetworkAvailable()) {
            webView.loadUrl(url);
        } else {
            Toast.makeText(this, "No network connection available", Toast.LENGTH_SHORT).show();
        }
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                alerLoaading.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                alerLoaading.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                alerLoaading.dismiss();
                Toast.makeText(Ujian_PPdb.this, "Network error: " + description, Toast.LENGTH_SHORT).show();
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSavePassword(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDefaultFontSize(16); // Default font size
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setDomStorageEnabled(true); // Enable DOM storage
        webSettings.setLoadsImagesAutomatically(true); // Load images automatically
        webSettings.setSupportZoom(true); // Support zoom
        webSettings.setBuiltInZoomControls(true); // Enable zoom controls
        webSettings.setDisplayZoomControls(false); // Hide default zoom controls

    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}