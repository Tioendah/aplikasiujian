package com.tioprasetioa.www.mtsn3rohul.Ui.ujian;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
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
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.listsiswacurang.Response_SiswaCurang;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Model_MapelUjian;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Ui.MainActivity;
import com.tioprasetioa.www.mtsn3rohul.Utils.MyDeviceAdminReceiver;
import com.tioprasetioa.www.mtsn3rohul.Utils.SweetAlert;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Soal_Ujian extends AppCompatActivity implements Metode_Interface, View.OnClickListener {
    private ComponentName mAdminComponentName;
    private final boolean isAppStopped = false;
    public static final String KEY_MAPEL = "MAPEL";
    private DevicePolicyManager mDevicePolicyManager;
    private WebView webView;
    private boolean isDataSaved = false;

    SweetAlertDialog alerLoaading;
    Model_MapelUjian modelMapelUjian;
    private Button button;
    private ImageView back, forward, hidekeyboard, logout, done;
    private FloatingActionButton refresh;
    private TextView guru, mapel, save;
    SharedPreferences sh;
    private boolean isSendDataCalled = false;
    private boolean isBackPressed = false;

    private final Handler handler = new Handler();
    private Runnable runnable;
//    private final int interval = 300000; // 5 menit
    private final int interval = 120000; // 1 menit
//    private final int interval = 10000; // 5 detik
    private String nama, kelas, nguru, nmapel, nkategori, nkonsekuensi;
    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_soal_ujian);

        FindViewById();
        Logic();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mAdminComponentName = MyDeviceAdminReceiver.getComponentName(this);
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);

        SharedPreferences sp = getApplicationContext().getSharedPreferences(Utils.KEY_UJIAN, Context.MODE_PRIVATE);

        nama = sp.getString("nama2024","");
        kelas = sp.getString("kelas2024","");
        modelMapelUjian = getIntent().getParcelableExtra(KEY_MAPEL);
        nguru = modelMapelUjian.getGuru();
        nmapel = modelMapelUjian.getMapel();
        nkategori = "Tindakan Pelanggaran Berat: Peserta ujian telah keluar dari aplikasi ujian secara tidak sah saat masih dalam proses mengerjakan soal ujian, yang dapat mengindikasikan upaya kecurangan atau ketidakpatuhan terhadap peraturan ujian yang telah ditetapkan";
        nkonsekuensi = "Sanksi:\n" +
                "Peserta ujian akan diwajibkan untuk mengikuti ujian ulang dengan batas nilai paling tinggi setara dengan Kriteria Ketuntasan Minimal (KKM).\n" +
                "Apabila terdapat guru yang tidak bersedia memberikan ujian ulang, maka peserta ujian yang bersangkutan akan menerima nilai akhir sebesar 20";

        guru.setText(nguru);
        mapel.setText(nmapel);

        hideSystemUi();

        //proses penguncian
        startWakeUpTimer();
        try {
            startLockTask();
        }catch (IllegalArgumentException e){
            getWindow().getDecorView().post(() -> {
                if (isTaskRoot()) {
                    startLockTaskMode();
                } else {
                    Log.e(TAG, "Aktivitas bukan root task. Tidak dapat memulai Lock Task Mode.");
                }
            });
        }

        button = findViewById(R.id.startUjian);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh.setEnabled(true);
                save.setEnabled(true);
                if (isInLockTaskMode()) {
                   checkForBannedApps();
                    Toast.makeText(Soal_Ujian.this, "security system is activated", Toast.LENGTH_SHORT).show();
                    pinmasuk();
                    webView();
                    button.setVisibility(View.GONE);
                    // Aplikasi sedang berjalan dalam kunci tugas
                    // Tambahkan kode yang ingin dieksekusi saat aplikasi dalam kunci tugas di sini
                } else {
                    // Aplikasi sedang tidak dalam kunci tugas
                    SweetAlertDialog dialog = new SweetAlertDialog(Soal_Ujian.this,SweetAlertDialog.ERROR_TYPE);
                    dialog.setTitleText("Klik Tulisan 'Mengerti/Got it'");
                    dialog.setContentText("Untuk Memulai Ujian");
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            startLockTask();
                        }
                    });
                    dialog.show();
                }
            }
        });
    }
//    ======= MENGH
    private void startWakeUpTimer() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                wakeUpScreen();
                handler.postDelayed(this, 240000); // ulang tiap 5 menit
            }
        };
        handler.post(runnable);
    }
    private void wakeUpScreen() {
        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        if (powerManager != null && !powerManager.isInteractive()) {
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                            PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    "ujianApp:WakeLock");
            wakeLock.acquire(3000); // nyalakan selama 3 detik saja
        }
    }

    private void startLockTaskMode() {
        try {
            DevicePolicyManager dpm = getSystemService(DevicePolicyManager.class);

            if (dpm != null && dpm.isLockTaskPermitted(getPackageName())) {
                Log.d(TAG, "Lock Task Mode diizinkan untuk aplikasi ini.");
                startLockTask();
                Log.d(TAG, "Lock Task Mode berhasil dimulai.");
            } else {
                Log.e(TAG, "Lock Task Mode tidak diizinkan untuk aplikasi ini.");
            }
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Gagal memulai Lock Task Mode: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Kesalahan tak terduga saat memulai Lock Task Mode: " + e.getMessage());
        }
    }

    private boolean isAppInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true; // Aplikasi ditemukan
        } catch (PackageManager.NameNotFoundException e) {
            return false; // Aplikasi tidak ditemukan
        }
    }
    private boolean isInLockTaskMode() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (activityManager != null) {
            return activityManager.isInLockTaskMode();
        }
        return false;
    }

    @Override
    protected void onPause() {
        super.onPause();

        ActivityManager activityManager = (ActivityManager) getApplicationContext()
                .getSystemService(getApplicationContext().ACTIVITY_SERVICE);
        activityManager.moveTaskToFront(getTaskId(), 0);

    }
    private void hideSystemUi() {

        // hide status bar
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE  | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        decorView.setSystemUiVisibility(uiOptions);

    }

    @Override
    public void onBackPressed() {
        isBackPressed = true;
        if (isInLockTaskMode()){
            Toast.makeText(this, "Masukkan PIN untuk keluar!", Toast.LENGTH_SHORT).show();
        }else {
            super.onBackPressed();
        }

    }
    @Override
    public void FindViewById() {
        guru = findViewById(R.id.titleGuru);
        mapel = findViewById(R.id.titleUjian);
        back = findViewById(R.id.img_back);
        forward = findViewById(R.id.img_next);
        hidekeyboard = findViewById(R.id.img_keyboard);
        refresh = findViewById(R.id.refresh2);
        logout = findViewById(R.id.img_logout);
        save = findViewById(R.id.Save);
        save.setEnabled(false);
        webView = findViewById(R.id.webUjian);
        done = findViewById(R.id.done_ujian);

    }


    @Override
    public void Logic() {
        back.setOnClickListener(this);
        forward.setOnClickListener(this);
        hidekeyboard.setOnClickListener(this);
        logout.setOnClickListener(this);
        refresh.setOnClickListener(this);
        refresh.setEnabled(false);
        save.setOnClickListener(this);
        logout.setOnClickListener(this);


        SharedPreferences sp = getSharedPreferences("cekstatus", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit(); // Untuk mengedit SharedPreferences
        editor.putString("status", "BelumSelesaiUjian");        editor.apply();

        // Memeriksa status ujian dan mengatur runnable
        setupRunnable();


    }
    private void checkForBannedApps() {
        PackageManager packageManager = getPackageManager();
        Map<String, String> bannedApps = new HashMap<>();
        bannedApps.put("com.alexmanzana.bubbleall", "Bubbleall");
        bannedApps.put("floatbrowser.floating.browser.float.web.window", "Float Browser");
        bannedApps.put("com.lwi.android.flapps", "Floating Apps");
        bannedApps.put("com.jack850628.floatwindow", "Float Window");
        bannedApps.put("com.farmerbb.taskbar", "Taskbar");
        bannedApps.put("com.applay.overlay", "Overlay");
        bannedApps.put("com.sns.suraj.floatingandmultitaskingapps", "Floating & Multitasking Apps");
        bannedApps.put("com.artds.split.dual.screen.nb", "Split Screen");
        bannedApps.put("com.inventivestudio.floating_menu", "Floating Menu");
        bannedApps.put("com.xpp.floatbrowser", "Float Browser Pro");
        bannedApps.put("com.dsandds.flaotingapps.sp", "Floating Apps SP");
        bannedApps.put("comspli.exaspli.splitscspli", "Split Screen Plus");
        bannedApps.put("com.split.screen.shortcut.overview.accessibility.notification", "Split Screen Shortcut");
        bannedApps.put("com.split.screen", "Split Screen");
        bannedApps.put("com.maika.floatee", "Floatee");
        bannedApps.put("com.fooview.android.fooview", "FooView");
        bannedApps.put("cz.rdq.floatingtools", "Floating Tools");
        bannedApps.put("com.vmos.google", "VMOS");
        bannedApps.put("com.facebook.orca", "Messenger");

        // Cek setiap aplikasi dalam map
        for (Map.Entry<String, String> entry : bannedApps.entrySet()) {
            String packageName = entry.getKey();
            String appName = entry.getValue();
            boolean isInstalled = isAppInstalled(packageName, packageManager);
            if (isInstalled) {
                block("Terindikasi App Ilegal!","Hapus "+appName,appName);
//                Toast.makeText(this, appName + " terinstal di perangkat ini.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void setupRunnable() {
        runnable = new Runnable() {
            @Override
            public void run() {
                if (isInLockTaskMode()) {
                    // Lakukan sesuatu di sini
                    cekstatus();
                    handler.postDelayed(this, interval);
                } else {
                    SharedPreferences sp = getSharedPreferences("cekstatus", Context.MODE_PRIVATE);
                    String cekStatusUjian = sp.getString("status", "");
                    if (cekStatusUjian.equals("BelumSelesaiUjian")) {
                        nkategori = "Pelanggaran Kategori Fatal: Keluar dari aplikasi ketika masih mengerjakan soal ujian.";
                        nkonsekuensi = "Sanksi: 1. Ujian ulang dengan nilai maksimal KKM, 2. Guru yang tidak bersedia memberikan ujian ulang, maka siswa akan mendapatkan nilai 20.";
                        sendData(nama, kelas, nmapel, nguru, Utils.Date(), nkategori, nkonsekuensi);
                        alert("Anda Terindikasi Curang!");
                    } else {
                        nkategori = "Pelanggaran Kategori Menengah: Peserta ujian keluar dari aplikasi ujian dalam kondisi sudah siap mengerjakan soal-soal ujian.";
                        nkonsekuensi = "Sanksi: Siswa akan dikurangin nilainya sebanyak 40%.";
                        sendData(nama, kelas, nmapel, nguru, Utils.Date(), nkategori, nkonsekuensi);
                        alert("Anda Keluar dari Aplikasi Sebelum Waktunya!");
                    }

                    // Hentikan runnable
                    handler.removeCallbacks(runnable);
                }
            }

        };

        // Mulai runnable pertama kali
        handler.postDelayed(runnable, interval);
    }
    private void alert(String title) {
        SweetAlertDialog pDialog = new SweetAlertDialog(Soal_Ujian.this, SweetAlertDialog.WARNING_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(title);
        pDialog.setContentText("Segera! Hubungi Pengawas Ujian Anda");
        pDialog.setConfirmText("Ya");
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                startActivity(new Intent(Soal_Ujian.this, BerandaUjian.class));
                pDialog.dismissWithAnimation();
                finish();
            }
        });
        pDialog.show();
    }
    private void block(String title, String desc, String appilegal) {
        nkategori = "Terdeteksi Terdapat Aplikasi Ilegal "+appilegal;
        nkonsekuensi = "Dihukum Oleh Pengawas";
        sendData(nama, kelas, nmapel, nguru, Utils.Date(), nkategori, nkonsekuensi);
        SweetAlertDialog pDialog = new SweetAlertDialog(Soal_Ujian.this, SweetAlertDialog.WARNING_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(title);
        pDialog.setContentText(desc);
        pDialog.setConfirmText("Ya");
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                startActivity(new Intent(Soal_Ujian.this, BerandaUjian.class));
                pDialog.dismissWithAnimation();
                stopLockTask();
            }
        });
        pDialog.show();
    }

    private void cekstatus() {
        Toast.makeText(this, "Mengecek Kecurangan", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "Tidak Ada Pelanggaran Ditemukan", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img_back:
                // Cek apakah WebView bisa kembali ke halaman sebelumnya
                if(webView.canGoBack()){
                    // Jika bisa, kembali ke halaman sebelumnya
                    webView.goBack();
                } else {
                    // Jika tidak, tampilkan pesan Toast
                    Toast.makeText(this, "No back history available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_next:
                if(webView.canGoForward()){
                    // Jika bisa, maju ke halaman berikutnya
                    webView.goForward();
                } else {
                    // Jika tidak, tampilkan pesan Toast
                    Toast.makeText(this, "No forward history available", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.img_keyboard:
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.refresh2:
                if (isInLockTaskMode()){
                    SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Refresh Halaman");
                    pDialog.setContentText("Tindakan ini akan memuat ulang halaman dan menghapus jawaban");
                    pDialog.setConfirmText("Ya");
                    pDialog.setCancelable(true);
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
                            // Call webView() to refresh the page
                            webView();
                            pDialog.dismissWithAnimation();
                            button.setVisibility(View.GONE);
                        }
                    });

                    if (!isFinishing() && !isDestroyed()) {
                        pDialog.show();
                    }

                }else {
                    Toast.makeText(this, "Silahkan klik mulai ujian", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Save:
                try {
                    SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                    pDialog.setTitleText("Perhatian!");
                    pDialog.setContentText("Yakin, Anda ingin menyimpan Jawaban Anda?");
                    pDialog.setConfirmText("Ya");
                    pDialog.setCancelText("Tidak");
                    pDialog.setCancelable(false); // Tidak bisa dibatalkan dengan menekan di luar dialog

// Set cancel click listener
                    pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            pDialog.cancel(); // Menutup dialog jika tidak setuju
                        }
                    });

                    pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            // Menutup dialog dengan animasi
                            sweetAlertDialog.dismissWithAnimation();

                            // Menyimpan status jawaban
                            sh = getApplicationContext().getSharedPreferences("cekstatus", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sh.edit();
                            editor.putString("status", "Tersimpan");
                            editor.apply();

                            // Tandai data telah disimpan
                            isDataSaved = true;

                            // Menyembunyikan WebView dan menampilkan tombol selesai
                            webView.setVisibility(View.GONE);
                            done.setVisibility(View.VISIBLE);

                            // Pastikan tidak ada perintah yang menyebabkan aplikasi keluar
                            // Jangan tambahkan finish() atau System.exit(0) di sini
                        }
                    });

// Pastikan dialog hanya ditampilkan jika aplikasi dalam keadaan aktif dan tidak terkunci
                    if (!isFinishing() && !isDestroyed()) {
                        pDialog.show();
                    }
                }catch (NullPointerException e){
                    Toast.makeText(this, "Silahkan Klik Ulang!", Toast.LENGTH_SHORT).show();
                }


                break;

            case R.id.img_logout:
                alertexit();
                break;
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    private void webView() {
        SweetAlertDialog alerLoaading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alerLoaading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alerLoaading.setTitleText("Loading");
        alerLoaading.setCancelable(true);
        alerLoaading.show();
        modelMapelUjian = getIntent().getParcelableExtra(KEY_MAPEL);
        String nguru = modelMapelUjian.getGuru();
        String nmapel = modelMapelUjian.getMapel();
        guru.setText(nguru);
        mapel.setText(nmapel);

        String link = modelMapelUjian.getLink();
        webView.loadUrl(link);

        // Enable Javascript
        webView.getSettings().setSavePassword(true);


        // Force links and redirects to open in the WebView instead of in a browser
        webView.setWebViewClient(new WebViewClient());
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.getCacheMode();
        webSettings.getDefaultFixedFontSize();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                loading.setVisibility(View.VISIBLE);
                alerLoaading.getProgressHelper().spin();
                alerLoaading.show();

            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
                alerLoaading.getProgressHelper().stopSpinning();
                alerLoaading.dismiss();
                Utils.tapview(Soal_Ujian.this,R.id.refresh2,"SSL Error, Hubungi Pengawas!", "Silahkan Restart Hp Anda");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                sh = getApplicationContext().getSharedPreferences("cekstatus", Context.MODE_PRIVATE);
                String datash =  sh.getString("status","");
                if (datash.equals("BelumSelesaiUjian")){
                    int errorCode = error.getErrorCode();

                    if (errorCode == ERROR_HOST_LOOKUP ||
                            errorCode == ERROR_CONNECT ||
                            errorCode == ERROR_TIMEOUT) {
                        // Error karena tidak ada internet
                        Utils.tapview(Soal_Ujian.this, R.id.refresh2, "Gangguan Jaringan / Paket Internet Habis", "Silahkan Coba Klik Tombol ini!");
                    }
                }else {
                    Log.d("aman","aman");
                }

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                alerLoaading.getProgressHelper().stopSpinning();
                alerLoaading.dismiss();
            }

        });
    }
    @Override
    protected void onStop() {
        super.onStop();

        if (isBackPressed) {
            // Reset flag
            isBackPressed = false;
            return; // Don't call sendData() when back button is pressed
        }

        SharedPreferences sp = getApplicationContext().getSharedPreferences("cekstatus", Context.MODE_PRIVATE);
        String cekstatus = sp.getString("status", "");

        // Periksa apakah data sudah disimpan
        if (!isDataSaved) { // hanya panggil sendData jika data belum disimpan
            if (cekstatus.equals("BelumSelesaiUjian")) {
                if (!isSendDataCalled) {
                    sendData(nama, kelas, nmapel, nguru, Utils.Date(), nkategori, nkonsekuensi);
                    isSendDataCalled = true; // Set flag to true after calling sendData
                }

                // Tampilkan dialog SweetAlert untuk indikasi kecurangan
                SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Anda Terindikasi curang!");
                pDialog.setContentText("Segera! Hubungi Pengawas Ujian Anda");
                pDialog.setConfirmText("Ya");
                pDialog.setCancelable(false);
                pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        stopLockTask();
                        startActivity(new Intent(Soal_Ujian.this, BerandaUjian.class));
                        pDialog.dismissWithAnimation();
                    }
                });
                pDialog.show();
            } else {
                if (!isSendDataCalled) {
                    nkategori = "Pelanggaran Kategori Menengah: Peserta ujian keluar dari aplikasi ujian dalam kondisi sudah siap mengerjakan soal-soal ujian.";
                    nkonsekuensi = "Sanksi: Siswa akan dikurangin nilainya sebanyak 40%.";
                    sendData(nama, kelas, nmapel, nguru, Utils.Date(), nkategori, nkonsekuensi);
                    isSendDataCalled = true; // Set flag to true after calling sendData
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);

    }
    private void sendData(String nama, String kelas, String mapel, String guru, String waktu, String kategori, String konsekuensi){
        try {
            Api api = Server.koneksiretrofit().create(Api.class);
            Call<Response_SiswaCurang> responseSiswaCurangCall = api.postData(nama, kelas, mapel, guru, waktu, kategori, konsekuensi);
            responseSiswaCurangCall.enqueue(new Callback<Response_SiswaCurang>() {
                @Override
                public void onResponse(Call<Response_SiswaCurang> call, Response<Response_SiswaCurang> response) {
                    if (response.isSuccessful()) {
                        Utils.Log("Berhasil", "Berhasil Mengirim Data");
                    } else {
                        // Handle response failure but request was made successfully
                        Toast.makeText(Soal_Ujian.this, "Gagal Mengirim Data, Kesalahan server", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Response_SiswaCurang> call, Throwable throwable) {
                    // Handle request failure
                    Toast.makeText(Soal_Ujian.this, "Ganguan Sinyal" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    Utils.Log("Error", throwable.getMessage());
                }
            });
        } catch (Exception e) {
            // Handle any other exceptions that might occur
            Toast.makeText(Soal_Ujian.this, "Terjadi kesalahan: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            Utils.Log("Exception", e.getMessage());
        }
    }
    public void pinmasuk() {
        // Inflate layout untuk dialog
        LayoutInflater inflater = LayoutInflater.from(Soal_Ujian.this);
        View popupView = inflater.inflate(R.layout.popup_layout, null);

        // Buat SweetAlertDialog
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Soal_Ujian.this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Masukkan PIN!");
        sweetAlertDialog.setCustomView(popupView);
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                // Ambil input dari EditText
                EditText editText = popupView.findViewById(R.id.et_pin_exit);
                String userInput = editText.getText().toString();

                // Ambil PIN yang benar dari model
                modelMapelUjian = getIntent().getParcelableExtra(KEY_MAPEL);
                int pinIn = modelMapelUjian.getPinIn();

                if (userInput.equals(String.valueOf(pinIn))) {
                    // Validasi PIN benar
                    checkForBannedApps();

                    // Pastikan dialog ditutup hanya jika aktivitas masih aktif
                    if (!isFinishing() && !isDestroyed()) {
                        sweetAlertDialog.dismissWithAnimation();
                        SweetAlert sweetAlert = new SweetAlert();
                        sweetAlert.customimage(Soal_Ujian.this,R.drawable.alertpower,"Peringatan!", "Dilarang menekan tombol power selama ujian!", "Ok, Saya mengerti");
                    }
                } else {
                    // PIN salah
                    editText.setText("");
                    Toast.makeText(Soal_Ujian.this, "Pin Salah!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Pastikan dialog tidak dapat dibatalkan
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.show();
    }
    public void alertexit(){
        LayoutInflater inflater = LayoutInflater.from(Soal_Ujian.this);
        View popupView = inflater.inflate(R.layout.popup_layout, null);
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(Soal_Ujian.this, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setTitleText("Masukkan PIN!");
        sweetAlertDialog.setCustomView(popupView);

        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                EditText editText = popupView.findViewById(R.id.et_pin_exit);
                String userInput = editText.getText().toString();
                modelMapelUjian = getIntent().getParcelableExtra(KEY_MAPEL);
                int pinIn = modelMapelUjian.getPinIn();
                int pinOut = modelMapelUjian.getPinOut();
                int pin_Exit = modelMapelUjian.getPin_exit();

                if (userInput.equals(String.valueOf(pinIn))){
                    // Dismiss dialog if pinIn matches
                    dismissDialogIfNeeded(sweetAlertDialog);
                } else if (userInput.equals(String.valueOf(pinOut))) {
                    stopLockTask();
                    onBackPressed();
                } else if (userInput.equals(String.valueOf(pin_Exit))) {
                    isBackPressed = true;
                    stopLockTask();
                    startActivity(new Intent(Soal_Ujian.this, MainActivity.class));
                    finishAffinity();
                } else {
                    editText.setText("");
                    Toast.makeText(Soal_Ujian.this, "Pin Salah!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        sweetAlertDialog.setCancelable(true);

        // Show dialog if the activity is still active
        if (!isFinishing() && !isDestroyed()) {
            sweetAlertDialog.show();
        }
    }

    private void dismissDialogIfNeeded(SweetAlertDialog sweetAlertDialog) {
        try {
            // Dismiss dialog safely
            if (!isFinishing() && !isDestroyed()) {
                sweetAlertDialog.dismissWithAnimation();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); // Log the exception for debugging
        }
    }



}