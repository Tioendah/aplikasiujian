package com.tioprasetioa.www.mtsn3rohul.Ui;

import static com.tioprasetioa.www.mtsn3rohul.Utils.Constans.TOPIC;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.messaging.FirebaseMessaging;
import com.tioprasetioa.www.mtsn3rohul.Fragment.Akun;
import com.tioprasetioa.www.mtsn3rohul.Fragment.Beranda;
import com.tioprasetioa.www.mtsn3rohul.Fragment.Berita_Sekolah;
import com.tioprasetioa.www.mtsn3rohul.Fragment.Katalog;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Utils.SweetAlert;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener, View.OnClickListener, Metode_Interface {
    //DIAGRAM PIE
    ArrayList<HashMap<String,String>> list_data,list_datav, list_DataE;
    private final int REQUES_CORE = 11;
    private FloatingActionButton floatingActionButton;

    private static final int REQUEST_POST_NOTIFICATIONS_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindViewById();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.i("Subscribe", "Berhasil subscribe ke topik myTopic");
                    } else {
                        Log.e("Subscribe", "Gagal subscribe ke topik");
                    }
                });
        SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences("news", MODE_PRIVATE);
        String ceknews = sharedPreferences.getString("news_key","");
        if (ceknews.equals("notif")){
            Berita_Sekolah berita_sekolah = new Berita_Sekolah();
            FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
            fragmentTransaction1.replace(R.id.frame,berita_sekolah);
            fragmentTransaction1.commit();
            floatingActionButton.setVisibility(View.GONE);
        }else {
            Beranda beranda = new Beranda();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame,beranda);
            fragmentTransaction.commit();
        }
        //Bottom fragment yang akan di eksekusi pertama kali
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottombar);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        // Memeriksa dan meminta izin POST_NOTIFICATIONS jika belum diberikan
//        cek notifikasi
        requestPermission();


        update();
        Logic();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_POST_NOTIFICATIONS_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Izin di terima", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Izin di tolak", Toast.LENGTH_SHORT).show();
                // Izin ditolak, beri tahu pengguna bahwa izin diperlukan untuk menampilkan notifikasi
            }
        }
    }
    private void update() {
        try {
            Utils utils = new Utils();
            utils.VolleyString(MainActivity.this, "https://mtsn3rokanhulu.sch.id/api/api_statusujian.php", "version13", new Utils.VolleyDataString() {
                @Override
                public void onError(String errorMesage) {

                }

                @Override
                public void onSuccess(String result) {
                    if (result.equals("1")){
                        SweetAlert sweetAlert = new SweetAlert();
                        sweetAlert.AlerUpdateVersion("Ada Pembaharuan!", "Apakah Anda ingin melakukan pembaharuan?","Ya","Tidak",MainActivity.this,MainActivity.this);
                    }else {
                        Log.d("msg update version", "onSuccess: sudah versi terbaru");
                    }
                }
            });
        }catch (NullPointerException e){
            Toast.makeText(this, "Jaringan Tidak Setabil", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode ==REQUES_CORE){
            Utils.Toast(MainActivity.this,"Start Download");
            if (resultCode != RESULT_OK){
                Log.d("mmm","Update Failed"+resultCode);
            }
        }
    }

    @Override
    public void FindViewById() {
        floatingActionButton = findViewById(R.id.floating_refresh);
        floatingActionButton.setOnClickListener(this);
        floatingActionButton.setVisibility(View.GONE);

        //sharepreference

    }

    @Override
    public void Logic() {
//        untuk menginisialisasi sdk
        new Thread(
                () -> {
                    // Initialize the Google Mobile Ads SDK on a background thread.
                    MobileAds.initialize(this, initializationStatus -> {});
                })
                .start();
//        getTopic();
        //MENANGKAP VOLLEY
        //Tap target View
//        new MaterialIntroView.Builder(MainActivity.this)
//                .enableIcon(false)
//                .setFocusGravity(FocusGravity.CENTER)
//                .setFocusType(Focus.MINIMUM)
//                .enableFadeAnimation(true)
//                .setDelayMillis(1000)
//                .performClick(true)
//                .setInfoText("Fitur terbaru 'E Library' ")
//                .setShape(ShapeType.CIRCLE)
//                .setTarget(findViewById(R.id.id_katalog))
//                .setUsageId("Katalog")
//                .show();


    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            //posisi tidak menentukan yang penting posisi di xml
            case R.id.id_Beranda:
                Beranda beranda = new Beranda();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame,beranda);
                fragmentTransaction.commit();
                floatingActionButton.setVisibility(View.GONE);
                return true;

            case R.id.id_berita:
                Berita_Sekolah berita_sekolah = new Berita_Sekolah();
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.frame,berita_sekolah);
                fragmentTransaction1.commit();
                floatingActionButton.setVisibility(View.GONE);
                return true;

            case R.id.id_katalog:
                //untuk mengirim data ke fragment dialog yang akan menjadi diagram PIE
                Katalog katalog = new Katalog();
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.frame,katalog);
                fragmentTransaction3.commit();
                floatingActionButton.setVisibility(View.GONE);
                return true;
            case R.id.id_library:
                Akun akun = new Akun();
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.frame, akun);
                fragmentTransaction2.commit();
                floatingActionButton.setVisibility(View.VISIBLE);
                return true;

        }
        return false;
    }
    public void refresh(){
        Akun akun = new Akun();
        FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
        fragmentTransaction2.replace(R.id.frame, akun);
        fragmentTransaction2.commit();

    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        finish();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.floating_refresh) {
            refresh();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    private final ActivityResultLauncher<String> resultLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted){
                    // Permission Granted
                    //Get Device token from firebase
                    getDeviceToken();
                }else{
                    //Permission denied
                }
            }
    );
    public void requestPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED){
                //Permission already Granted
            }else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){
                //You can explain user that why do you need permission bu showing Dialogue box or Toast Message
            }else {
                //Request For Permission
                resultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }else {
            //GEt Device Token from Firebase
//            getDeviceToken();
        }
    }

    public void getDeviceToken(){

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()){
                    Log.e("FireBaseLogs"," Fetching Toke Failed"+ task.getException());
                    return;
                }
                //Get Device Token
                String token = task.getResult();
//                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                Log.d("FireBaseLogs","Device Token: "+token);
            }
        });
    }




}
