package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.Rekap_Absen;
import com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.View_Rekap_Absen;

public class Actiivity_Identitas extends AppCompatActivity implements Metode_Interface, View.OnClickListener {

    private EditText vNama, vNip, vUnitKerja, vBulan;
    private String NAMA, NIP, UNITKERJA, BULAN;
    private Button button, button2;
    private SharedPreferences sp;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.identitas_db);

        FindViewById();
        Logic();
    }
    @Override
    public void FindViewById() {
        vNama = findViewById(R.id.Nama);
        vNip = findViewById(R.id.NIP);
        vUnitKerja = findViewById(R.id.UnitKerja);
        vBulan = findViewById(R.id.Bulan);
        button = findViewById(R.id.btn_save);
        button2 = findViewById(R.id.btn_lihat);
        sp = getSharedPreferences("Myuser", Context.MODE_PRIVATE);
    }
    @Override
    public void Logic() {
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, Rekap_Absen.class);
        startActivity(i);
        onDestroy();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.btn_save:
             NAMA = vNama.getText().toString();
             NIP = vNip.getText().toString();
             UNITKERJA = vUnitKerja.getText().toString();
             BULAN = vBulan.getText().toString();

             SharedPreferences.Editor editor = sp.edit();

             editor.putString("nama", NAMA);
             editor.putString("nip", NIP);
             editor.putString("unitkerja", UNITKERJA);
             editor.putString("bulan", BULAN);
             editor.commit();
             Toast.makeText(Actiivity_Identitas.this, "Sukes", Toast.LENGTH_SHORT).show();
             break;
         case R.id.btn_lihat:
             Intent i = new Intent(Actiivity_Identitas.this, View_Rekap_Absen.class);
             startActivity(i);
             break;
     }



    }



}
