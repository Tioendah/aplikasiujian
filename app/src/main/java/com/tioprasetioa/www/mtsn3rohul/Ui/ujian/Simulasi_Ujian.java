package com.tioprasetioa.www.mtsn3rohul.Ui.ujian;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Simulasi_Ujian extends AppCompatActivity implements Metode_Interface, View.OnClickListener {

    SharedPreferences sharedPreferences;
    private WebView webView;
    private TextView tx_save;
    private final Handler handler = new Handler();
    private Runnable runnable;
    private final int interval = 60000;



    private ImageView back, forward, keyboard, logout, img_nointernet, iv_error, done;
    private FloatingActionButton floatingActionButton;
    Button button;
    String url = "https://forms.gle/5j65UdaPWeaVT4DV8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_simulasi_ujian);
        FindViewById();
        Logic();


    }
    private void taprefresh() {
        Utils.tapview(this,R.id.refresh_sim,"Refresh","Jika tampilan seperti di atas berarti jaringan Anda bermasalah, silahkan klik ini!");
    }

    @Override
    public void FindViewById() {
        back = findViewById(R.id.img_back);
        forward = findViewById(R.id.img_next);
        keyboard = findViewById(R.id.img_keyboard);
        logout = findViewById(R.id.img_logout);
        floatingActionButton = findViewById(R.id.refresh_sim);
        button = findViewById(R.id.startUjian_sim);
        webView = findViewById(R.id.webUjian_sim);
        img_nointernet = findViewById(R.id.img_nointernet);
        tx_save = findViewById(R.id.save_sim);
        iv_error = findViewById(R.id.error_sim);
        done = findViewById(R.id.done_simm);

    }

    @Override
    public void Logic() {
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        keyboard.setOnClickListener(this);
        logout.setOnClickListener(this);
        floatingActionButton.setOnClickListener(this);
        button.setOnClickListener(this);
        img_nointernet.setVisibility(View.GONE);
        tx_save.setOnClickListener(this);
    }
    private void webView() {
        SweetAlertDialog alerLoaading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alerLoaading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alerLoaading.setTitleText("Loading");
        alerLoaading.setCancelable(false);
        alerLoaading.show();

        webView.loadUrl(url);
        if (isNetworkAvailable()) {
            webView.loadUrl(url);
        } else {
            img_nointernet.setVisibility(View.VISIBLE);
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
                img_nointernet.setVisibility(View.GONE);
                Toast.makeText(Simulasi_Ujian.this, "Network error: " + description, Toast.LENGTH_SHORT).show();
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


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back:
                if (webView.canGoBack()){
                    webView.goBack();
                }else {
                    Toast.makeText(this, "No Back History Available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_next:
                if (webView.canGoForward()){
                    webView.goForward();
                }else {
                    Toast.makeText(this, "No Forward History Available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_keyboard:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                break;
            case R.id.refresh_sim:
                iv_error.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
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
                        webView(); // Memanggil webView untuk memuat ulang halaman
                        pDialog.dismissWithAnimation(); // Menutup dialog
                        button.setVisibility(View.GONE);

                        sharedPreferences = getSharedPreferences("introsim", Context.MODE_PRIVATE);
                        String cek = sharedPreferences.getString("intro", ""); // Ambil nilai awal

                        Log.d("shared", "Nilai awal intro: " + cek);

                        if (cek.isEmpty()) { // Jika belum ada nilai tersimpan
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("intro", "link");
                            editor.apply(); // Gunakan apply() agar lebih cepat dan aman

                            // Ambil ulang nilai setelah penyimpanan
                            String updatedCek = sharedPreferences.getString("intro", "");
                            Log.d("shared", "Nilai setelah disimpan: " + updatedCek);

                            // Tampilkan Toast dengan nilai yang baru

                            // Panggil metode runnable
                            runnable();
                        }


                    }
                });
                pDialog.show();
                break;
            case R.id.img_logout:
                alertexit();
                Toast.makeText(this, "isi pin bebas", Toast.LENGTH_SHORT).show();
                break;
            case R.id.startUjian_sim:
                sharedPreferences = getSharedPreferences("introsim", Context.MODE_PRIVATE);
                String cek = sharedPreferences.getString("intro","");
                if (cek.isEmpty()){
                    taprefresh();
                    iv_error.setVisibility(View.VISIBLE);
                }else {
                    webView();
                    webView.setVisibility(View.VISIBLE);
                }
                button.setVisibility(View.GONE);
                break;
            case R.id.save_sim:
                try {
                    SweetAlertDialog pDialogp = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                    pDialogp.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialogp.setTitleText("Perhatian!");
                    pDialogp.setContentText("Yakin, Anda ingin menyimpan Jawaban Anda?");
                    pDialogp.setConfirmText("Ya");
                    pDialogp.setCancelText("Tidak");
                    pDialogp.setCancelable(false); // Tidak bisa dibatalkan dengan menekan di luar dialog

                    pDialogp.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            pDialogp.cancel(); // Menutup dialog jika tidak setuju
                        }
                    });

                    pDialogp.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            // Menutup dialog dengan animasi
                            sweetAlertDialog.dismissWithAnimation();
                            webView.setVisibility(View.GONE);
                            done.setVisibility(View.VISIBLE);
                            sharedPreferences = getSharedPreferences("introsim", Context.MODE_PRIVATE);
                            String cekupdate = sharedPreferences.getString("intro","");
                            Log.d("shared", cekupdate);
                            if (cekupdate.equals("save")){
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("intro","pinout");
                                editor.apply();
                                Utils.tapview(Simulasi_Ujian.this,R.id.img_logout,"Pin Out","jika sudah selesai mengerjakan silahkan klik ini");
                            }

                        }
                    });

                    if (!isFinishing() && !isDestroyed()) {
                        pDialogp.show();
                    }
                }catch (NullPointerException e){
                    Toast.makeText(this, "Silahkan Klik Ulang!", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }
    public void alertexit(){
        LayoutInflater inflater = LayoutInflater.from(Simulasi_Ujian.this);
        View popupView = inflater.inflate(R.layout.popup_layout, null);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Simulasi_Ujian.this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Masukkan PIN!");
        sweetAlertDialog.setCustomView(popupView);

        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                EditText editText = popupView.findViewById(R.id.et_pin_exit);
                String userInput = editText.getText().toString();
                if (!userInput.isEmpty()){
                    finish();
                }
            }
        });

        sweetAlertDialog.setCancelable(true);

        // Show dialog if the activity is still active
        if (!isFinishing() && !isDestroyed()) {
            sweetAlertDialog.show();
        }
    }

    private void runnable() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            // Ambil SharedPreferences
            sharedPreferences = getSharedPreferences("introsim", Context.MODE_PRIVATE);
            String cek = sharedPreferences.getString("intro", "");


            Log.d("shared", "Nilai intro sebelum pengecekan: " + cek);

            if (cek.equals("link")) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("intro", "save");
                editor.apply();

                // Ambil ulang nilai setelah penyimpanan
                String updatedCek = sharedPreferences.getString("intro", "");
                Log.d("shared", "Nilai intro setelah disimpan: " + updatedCek);

                // Panggil metode untuk menampilkan UI atau petunjuk
                Utils.tapview(Simulasi_Ujian.this, R.id.save_sim, "Simpan Jawaban", "Jika sudah selesai mengerjakan soal, silahkan klik di sini!");
            }

            // Tampilkan toast setelah 3 detik

        }, 20000);
    }

}