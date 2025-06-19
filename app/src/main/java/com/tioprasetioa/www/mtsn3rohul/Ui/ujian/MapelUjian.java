package com.tioprasetioa.www.mtsn3rohul.Ui.ujian;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_MapelUjian;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Data;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Model_MapelUjian;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Response_MapelUjian;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Utils.BarColor;
import com.tioprasetioa.www.mtsn3rohul.Utils.SweetAlert;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapelUjian extends AppCompatActivity implements Metode_Interface {

    RecyclerView recyclerView;
    TextView textView, textNama, textKelas;
    LinearLayoutManager linearLayoutManager;
    Adapter_MapelUjian adapter_mapelUjian;
    String dataKelas, getData;
    FloatingActionButton floatingActionButton;
    List<Model_MapelUjian>data = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mapel_ujian);

        BarColor.setBarColor(MapelUjian.this, R.color.orange, R.color.black);

        FindViewById();
        Logic();
        back();

    }

    private void back() {

        OnBackPressedCallback onBackPressedCallback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                SweetAlert sweetAlert = new SweetAlert();
                sweetAlert.AlertBerandaUjian("Peringatan!","Tombol Back akan menutup halaman ini, tetap lanjutkan?","Ya","Tidak",MapelUjian.this,MapelUjian.this);

            }
        };
        getOnBackPressedDispatcher().addCallback(this,onBackPressedCallback);
    }

    @Override
    public void FindViewById() {
        recyclerView = findViewById(R.id.rc_mapelujian);
        textNama = findViewById(R.id.tx_User);
        textKelas = findViewById(R.id.tx_kelas);
        textView = findViewById(R.id.tx_mapelujian);
        floatingActionButton = findViewById(R.id.refresh_mapelujian);


    }

    @Override
    public void Logic() {
        SharedPreferences spGet = getApplicationContext().getSharedPreferences(Utils.KEY_UJIAN, Context.MODE_PRIVATE);
        String nama = spGet.getString("nama2024", "");
        String kelas = spGet.getString("kelas2024", "");

// Mengatur tampilan berdasarkan data yang didapatkan
        if (nama.equals(Utils.KEY_VALUE)) {
            textNama.setText("Admin");
            textKelas.setVisibility(View.GONE);
        } else {
            textNama.setText(nama);
            textKelas.setText(kelas);
        }


        Intent intent = getIntent();

        if(intent != null) {
            dataKelas = intent.getStringExtra("data");
            textView.setText(dataKelas);
            // Lakukan sesuatu dengan data yang diterima
        }
        getData = textView.getText().toString();
        dumpData(getData);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dumpData(getData);
            }
        });

    }

    private void dumpData(String getData) {
        SweetAlertDialog alerLoaading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alerLoaading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alerLoaading.setTitleText("Loading");
        alerLoaading.setCancelable(true);
        alerLoaading.show();
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_MapelUjian<Data>> call = api.getMapelUjian(getData);
        call.enqueue(new Callback<Response_MapelUjian<Data>>() {
            @Override
            public void onResponse(Call<Response_MapelUjian<Data>> call, Response<Response_MapelUjian<Data>> response) {
                if (response.body()!=null){
                    alerLoaading.dismissWithAnimation();
                    data.addAll(response.body().getData().getKelas());
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.VERTICAL,false));
                    adapter_mapelUjian = new Adapter_MapelUjian(data,MapelUjian.this);
                    recyclerView.setAdapter(adapter_mapelUjian);
                }else {
                    Toast.makeText(MapelUjian.this, "gagal", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Response_MapelUjian<Data>> call, Throwable throwable) {
                Toast.makeText(MapelUjian.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}