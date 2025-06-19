package com.tioprasetioa.www.mtsn3rohul.Ui.ppdb;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Utils.Btn_Whatsapp;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PPDB extends AppCompatActivity implements Metode_Interface, View.OnClickListener {
    private WebView webView;
    private ImageView back, forward, keyboard,home, unduh;
    private FloatingActionButton floatingActionButton, floatingActionButtonwa;
    private ValueCallback<Uri[]> filePathCallback;
    private final static int FILE_CHOOSER_REQUEST_CODE = 1;
    private final String url = "https://mtsn3rokanhulu.sch.id/ppdb2025";
    private static final String TARGET_URL = "https://mtsn3rokanhulu.sch.id/ppdb2025/panel_siswa/cetak";
    private static final String URL_SISWA = "https://mtsn3rokanhulu.sch.id/ppdb2025/panel_siswa/";
    private static final String URL_WA = "https://chat.whatsapp.com/LOAoFyt9uv9FweNRwVjQgb";
    private static final String URL_BIODATA = "https://mtsn3rokanhulu.sch.id/ppdb2025/panel_siswa/biodata";
    private static final String URL_LULUS = "https://mtsn3rokanhulu.sch.id/ppdb2025/panel_siswa/cetak_lulus";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ppdb);
        FindViewById();
        Logic();
    }

    @Override
    public void FindViewById() {
        back = findViewById(R.id.img_backppdb);
        forward = findViewById(R.id.img_nextppdb);
        keyboard = findViewById(R.id.img_keyboardppdb);
        unduh = findViewById(R.id.img_unduh);
        home = findViewById(R.id.img_homeppdb);
        floatingActionButton = findViewById(R.id.refreshppdb);
        webView = findViewById(R.id.webppdb);
        floatingActionButtonwa = findViewById(R.id.float_wa);
    }
    @Override
    public void Logic() {
        home.setOnClickListener(this);
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        keyboard.setOnClickListener(this);
        unduh.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        floatingActionButtonwa.setOnClickListener(this);

        SharedPreferences prefs = getSharedPreferences("WebViewPrefs", MODE_PRIVATE);
        String lastUrl = prefs.getString("last_url", null);

        if (lastUrl != null) {
            webView(lastUrl);
        } else {
            webView(url); // URL default
        }


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_backppdb:
                if (webView.canGoBack()){
                    webView.goBack();
                }else {
                    Toast.makeText(this, "No Back History Available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_nextppdb:
                if (webView.canGoForward()){
                    webView.goForward();
                }else {
                    Toast.makeText(this, "No Forward History Available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_keyboardppdb:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.refreshppdb:
                SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Refresh Halaman");
                pDialog.setContentText("Tindakan ini akan memuat ulang halaman dan menghapus jawaban");
                pDialog.setConfirmText("Ya");
                pDialog.setCancelText("Tidak");
                pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        pDialog.cancel();
                    }
                });
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        SharedPreferences prefs = getSharedPreferences("WebViewPrefs", MODE_PRIVATE);
                        String lastUrl = prefs.getString("last_url", null);
                        webView(lastUrl);
                        pDialog.dismissWithAnimation();
                    }
                });
                pDialog.show();
                break;
            case R.id.img_homeppdb:
                SharedPreferences prefs = getSharedPreferences("WebViewPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("last_url", url);
                editor.apply();
                webView(url);
                break;
            case R.id.img_unduh:
                SharedPreferences sp = getSharedPreferences("download",MODE_PRIVATE);
                String getKey = sp.getString("download_key","");
                if (getKey.equals("done")){
                    checkPermission();
                    // Contoh panggilan metode untuk membuka file di folder unduhan
                    openDownloadFolder("Bukti_Pendaftaran_PPDB_MTsN_3_Rohul.pdf");
                }else {
                    Toast.makeText(this, "File Belum Didownload", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.float_wa:
                Btn_Whatsapp btn_whatsapp = new Btn_Whatsapp();
                btn_whatsapp.Whatsapp(PPDB.this,"http://api.whatsapp.com/send?phone=6281276939553&text=Assalamulaikum Pak Roni, ","PILIH WHATSAPP");
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin diberikan
            } else {
                Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openDownloadFolder(String fileName) {
        Uri uri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/" + fileName);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "*/*");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application found to open the file.", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint({"ObsoleteSdkInt", "SetJavaScriptEnabled"})
    private void webView(String url) {
        SweetAlertDialog alerLoaading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alerLoaading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alerLoaading.setTitleText("Loading");
        alerLoaading.setCancelable(true);
        alerLoaading.show();

        SharedPreferences prefs = getSharedPreferences("WebViewPrefs", MODE_PRIVATE);
        String lastUrl = prefs.getString("last_url", null);

        if (lastUrl != null) {
            webView.loadUrl(lastUrl);
        } else {
            webView.loadUrl(url); // URL default
        }

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(@NonNull WebView view, @NonNull WebResourceRequest request) {
                String url = request.getUrl().toString();

                if (url.startsWith("whatsapp:")) {
                    // Handle the whatsapp URL
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(intent);
                    return true;
                }

                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                alerLoaading.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                alerLoaading.dismiss();
                if (TARGET_URL.equals(url)){
                    createWebPrintJob(webView,"Bukti_Pendaftaran_PPDB_MTsN_3_Rohul");
                } else if (URL_LULUS.equals(url)) {
                    createWebPrintJob(webView,"Bukti_Lulus_PPDB_MTsN_Rohul");
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                alerLoaading.dismiss();
                Toast.makeText(PPDB.this, "Network error: " + description, Toast.LENGTH_SHORT).show();
            }
        });
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(true);

        webView.setHorizontalScrollBarEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                PPDB.this.filePathCallback = filePathCallback;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("*/*");
                startActivityForResult(Intent.createChooser(intent, "File Chooser"), FILE_CHOOSER_REQUEST_CODE);
                return true;
            }
        });

        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(true);
//        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSettings.setAllowContentAccess(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDefaultFontSize(16); // Default font size
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setDomStorageEnabled(true); // Enable DOM storage
        webSettings.setLoadsImagesAutomatically(true); // Load images automatically
        webSettings.setSupportZoom(true); // Support zoom
        webSettings.setBuiltInZoomControls(true); // Enable zoom controls
        webSettings.setDisplayZoomControls(false); // Hide default zoom controls
        webSettings.setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true); // Mengaktifkan kontrol zoom bawaan
        webView.getSettings().setDisplayZoomControls(false); // Menyembunyikan kontrol zoom default
        webView.getSettings().setSupportZoom(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }


    }
    private void createWebPrintJob(WebView webView, String nameFile) {
        SharedPreferences sharedPreferences = getSharedPreferences("download",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("download_key","done");
        editor.apply();

        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        if (printManager != null) {
            PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(nameFile);
            String jobName = getString(R.string.app_name) + " Document";
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setMediaSize(PrintAttributes.MediaSize.ISO_A4);
            builder.setResolution(new PrintAttributes.Resolution("pdf", "pdf", 600, 600));
            builder.setMinMargins(PrintAttributes.Margins.NO_MARGINS);
            PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());
            Utils.tapview(PPDB.this, R.id.img_unduh, "Klik Untuk Lihat Lokasi File",null);
            if (printJob.isCompleted()) {

                Log.d("debug", "berhasil");
            } else if (printJob.isFailed()) {
                Log.d("debug", "gagal");
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("WebViewPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("last_url", webView.getUrl());
        editor.apply();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        if (TARGET_URL.equals(webView.getUrl())){
            webView.loadUrl(URL_SISWA);
        } else if (URL_WA.equals(webView.getUrl())) {
            webView.loadUrl(URL_SISWA);
        } else if (URL_BIODATA.equals(webView.getUrl())) {
            webView.loadUrl(URL_SISWA);
        } else if (URL_LULUS.equals(webView.getUrl())) {
            webView.loadUrl(URL_SISWA);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FILE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (filePathCallback != null) {
                Uri[] results = data != null ? new Uri[]{data.getData()} : null;
                filePathCallback.onReceiveValue(results);
                filePathCallback = null;
            }
        } else {
            if (filePathCallback != null) {
                filePathCallback.onReceiveValue(null);
                filePathCallback = null;
            }
        }
    }
}