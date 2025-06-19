package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_ProfilGuru;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_ProfilGuru;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_ProfilGuru;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profil_Guru extends AppCompatActivity implements Metode_Interface {

    private RecyclerView recyclerView;
    private Adapter_ProfilGuru adapter_profilGuru;
    private List<ModelData_ProfilGuru>list ;

    //progressbar
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_guru);
        FindViewById();
        Logic();

    }
    @Override
    public void FindViewById() {
        recyclerView = findViewById(R.id.recycle_profil);
        progressBar = findViewById(R.id.progress_profilgr);
        progressBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void Logic() {
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_ProfilGuru>call = api.profilguru();
        call.enqueue(new Callback<Response_ProfilGuru>() {
            @Override
            public void onResponse(Call<Response_ProfilGuru> call, Response<Response_ProfilGuru> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();
                list = response.body().getData();
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                adapter_profilGuru = new Adapter_ProfilGuru(list);
                recyclerView.setAdapter(adapter_profilGuru);
                adapter_profilGuru.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
//                Toast.makeText(Profil_Guru.this,"Error"+kode+pesan,Toast.LENGTH_SHORT);
            }

            @Override
            public void onFailure(Call<Response_ProfilGuru> call, Throwable t) {
//                Toast.makeText(Profil_Guru.this,"Error"+t.getMessage(),Toast.LENGTH_SHORT);
            }
        });
    }
}