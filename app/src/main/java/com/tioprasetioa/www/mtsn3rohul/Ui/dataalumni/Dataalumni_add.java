package com.tioprasetioa.www.mtsn3rohul.Ui.dataalumni;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.tioprasetioa.www.mtsn3rohul.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Dataalumni_add extends AppCompatActivity {
    private WebView webView;
    private ValueCallback<Uri[]> filePathCallback;
    private final int FILE_CHOOSER_REQUEST_CODE = 1001;
    public static final String KEY_ALUMNI = "ALUMNI";
    public static final String KEY_ADDALUMNI2 = "ADDALUMNI";
    private static final String KEY_URL = "https://mtsn3rokanhulu.sch.id/dataalumni/";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dataalumni_add);
        webView = findViewById(R.id.web_createalumni);
        loadData();
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void loadData() {
        SweetAlertDialog alerLoaading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alerLoaading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alerLoaading.setTitleText("Loading");
        alerLoaading.setCancelable(false);
        alerLoaading.show();

        Intent intent = getIntent();
        String key = intent.getStringExtra(KEY_ALUMNI);
        if (key.equals("add_alumni")) {
            webView.loadUrl("https://mtsn3rokanhulu.sch.id/dataalumni/create/");
        }else {
            webView.loadUrl("https://mtsn3rokanhulu.sch.id/dataalumni/admin/");
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.getCacheMode();
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.getDefaultFixedFontSize();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                // Tampilkan loading saat halaman mulai dimuat
                if (!alerLoaading.isShowing()) {
                    alerLoaading.getProgressHelper().spin();
                    alerLoaading.show();
                }
            }
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // Tangani SSL error secara aman (dibatalkan)
                alerLoaading.dismiss();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                alerLoaading.getProgressHelper().stopSpinning();
                alerLoaading.dismiss();
                if (KEY_URL.equals(url)){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.iloveimg.com/id/kompres-gambar"));
                    intent.setPackage("com.android.chrome");
                    try{
                        startActivity(intent);
                    }catch(ActivityNotFoundException e){
                        intent.setPackage(null);
                        startActivity(intent);
                    }
                }
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback,
                                             FileChooserParams fileChooserParams) {
                Dataalumni_add.this.filePathCallback = filePathCallback;

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                startActivityForResult(Intent.createChooser(intent, "Pilih File"), FILE_CHOOSER_REQUEST_CODE);
                return true;
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            if (filePathCallback != null) {
                Uri[] results = null;

                if (resultCode == RESULT_OK && data != null) {
                    Uri uri = data.getData();
                    if (uri != null) {
                        results = new Uri[]{uri};
                    }
                }

                filePathCallback.onReceiveValue(results);
                filePathCallback = null;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
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