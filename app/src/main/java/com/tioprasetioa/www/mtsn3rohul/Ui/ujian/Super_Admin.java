package com.tioprasetioa.www.mtsn3rohul.Ui.ujian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_MapelAdmin;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_statuspdate.Response_statusupdate;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Data_MapelAdmin;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Response_MapelAdmin;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Utils.BarColor;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Super_Admin extends AppCompatActivity implements Metode_Interface {
    private RecyclerView recyclerView;

    private Adapter_MapelAdmin adapterMapelAdmin;
    private List<Data_MapelAdmin>data = new ArrayList<>();
    private SweetAlertDialog alerLoaading;
    private Button btn_kunci, btn_siswacurang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_super_admin);
        BarColor.setBarColor(Super_Admin.this, R.color.orange,R.color.black);
        FindViewById();
        Logic();
    }

    @Override
    public void FindViewById() {
        recyclerView = findViewById(R.id.rc_mapelujianadmin);
        btn_kunci = findViewById(R.id.btn_adminStatus);
        btn_siswacurang = findViewById(R.id.btn_adminListSiswaCurang);
        cekstatus();

    }
    private void cekstatus() {
        int id = 1;
        Utils utils = new Utils();
        utils.VolleyString(getApplicationContext(), "https://mtsn3rokanhulu.sch.id/api/api_statusujian.php", "statusujian", new Utils.VolleyDataString() {
            @Override
            public void onError(String errorMesage) {
                Toast.makeText(utils, "gagal jaringan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String result) {
                if (result.equals("1")){
                    btn_kunci.setBackgroundColor(getResources().getColor(R.color.purple_200));
                    btn_kunci.setText("Buka Kunci");
                    btn_kunci.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int open = 0;
                            sendData(id, open);
                            Toast.makeText(Super_Admin.this, "Membuka Kunci", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (result.equals("0")) {
                    btn_kunci.setBackgroundColor(getResources().getColor(R.color.kuning));
                    btn_kunci.setText("Super Admin");
                    btn_kunci.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int open = 2;
                            sendData(id, open);
                            Toast.makeText(Super_Admin.this, "Membuka Super Admin", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    btn_kunci.setBackgroundColor(getResources().getColor(R.color.Red));
                    btn_kunci.setText("Kunci Ujian");
                    btn_kunci.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int open = 1;
                            sendData(id, open);
                            Toast.makeText(Super_Admin.this, "Berhasil, Kunci Ujian", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            private void sendData(int id, int open) {
                Api api = Server.koneksiretrofit().create(Api.class);
                Call<Response_statusupdate>call = api.updateStatusUjian(id,open);
                call.enqueue(new Callback<Response_statusupdate>() {
                    @Override
                    public void onResponse(Call<Response_statusupdate> call, retrofit2.Response<Response_statusupdate> response) {
                        cekstatus();
                        Toast.makeText(Super_Admin.this, "Berhasil", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Response_statusupdate> call, Throwable throwable) {
                        Toast.makeText(Super_Admin.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void Logic() {
        alerLoaading = new SweetAlertDialog(Super_Admin.this, SweetAlertDialog.PROGRESS_TYPE);
        alerLoaading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alerLoaading.setTitleText("Loading");
        alerLoaading.setCancelable(true);
        alerLoaading.show();
        Getdata();
        btn_siswacurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Super_Admin.this, Daftar_SiswaCurang.class));
            }
        });

    }
    private void Getdata() {

        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_MapelAdmin>call = api.getDataMapelAdmin();
        call.enqueue(new Callback<Response_MapelAdmin>() {
            @Override
            public void onResponse(Call<Response_MapelAdmin> call, Response<Response_MapelAdmin> response) {
              if (response.isSuccessful()){
                  alerLoaading.dismissWithAnimation();
                  data = response.body().getData();
                  adapterMapelAdmin = new Adapter_MapelAdmin(data,getApplicationContext());
                  recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2, LinearLayoutManager.VERTICAL, false));
                  recyclerView.setAdapter(adapterMapelAdmin);
              }else {
                  Toast.makeText(Super_Admin.this, "gagal menarik data", Toast.LENGTH_SHORT).show();
              }
            }
            @Override
            public void onFailure(Call<Response_MapelAdmin> call, Throwable throwable) {
                alerLoaading.dismissWithAnimation();
                Toast.makeText(Super_Admin.this, "Error"+throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}