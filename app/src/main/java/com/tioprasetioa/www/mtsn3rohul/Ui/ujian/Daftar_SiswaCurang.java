package com.tioprasetioa.www.mtsn3rohul.Ui.ujian;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_SiswaCurang;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.SiswaCurang.DataItem;
import com.tioprasetioa.www.mtsn3rohul.Model.SiswaCurang.Response;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Server.IPServer;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class Daftar_SiswaCurang extends AppCompatActivity implements Metode_Interface {

    FloatingActionButton fl_refresh, fl_delete;
    RecyclerView recyclerView;
    Adapter_SiswaCurang adapterSiswaCurang;
    private List<DataItem> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daftar_siswa_curang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FindViewById();
        Logic();
    }
    public void DeleteAlldata(final Runnable callback) {
        IPServer ipServer = new IPServer();
        Utils utils = new Utils();

        utils.deleteAllData(Daftar_SiswaCurang.this, ipServer.url + "api_deletesiswacurrang.php",
                "Data berhasil dihapus", "Gagal menghapus data",
                new Utils.DeleteCallback() {
                    @Override
                    public void onSuccess() {
                        callback.run(); // Panggil Logic setelah data benar-benar terhapus
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(Daftar_SiswaCurang.this, "Gagal menghapus data", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public void FindViewById() {
        fl_delete= findViewById(R.id.delete_listsiswacurang);
        fl_refresh = findViewById(R.id.refresh_listsiswacurang);
        recyclerView = findViewById(R.id.rc_siswacurang);
        fl_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logic();
            }
        });
        fl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SweetAlertDialog pDialog = new SweetAlertDialog(Daftar_SiswaCurang.this, SweetAlertDialog.WARNING_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Delete Data");
                pDialog.setContentText("Yakin Ingin Hapus Data?");
                pDialog.setCancelable(true);
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
                        try {
                            DeleteAlldata(() -> {
                                Logic(); // Refresh RecyclerView setelah penghapusan sukses
                                pDialog.dismissWithAnimation();
                            });
                        } catch (Exception e) {
                            Toast.makeText(Daftar_SiswaCurang.this, "Gagal hapus Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                pDialog.show();
            }
        });
    }

    @Override
    public void Logic() {
        SweetAlertDialog alerLoaading = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        alerLoaading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alerLoaading.setTitleText("Loading");
        alerLoaading.setCancelable(true);
        alerLoaading.show();

        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response> call = api.getDataSiswaCurang();
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.body() != null && response.body().getData() != null) {
                    // Mengisi data dari response
                    // Pastikan ini sesuai dengan struktur response Anda
                    data = response.body().getData();
                    alerLoaading.dismissWithAnimation();

                } else {
                    alerLoaading.dismissWithAnimation();
                    data = new ArrayList<>();
                    Toast.makeText(Daftar_SiswaCurang.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Daftar_SiswaCurang.this);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager); // Set LayoutManager
                adapterSiswaCurang = new Adapter_SiswaCurang(data, Daftar_SiswaCurang.this);
                recyclerView.setAdapter(adapterSiswaCurang);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                alerLoaading.dismissWithAnimation();
                Log.d("mesg", "Error: " + t.getMessage());
                Toast.makeText(Daftar_SiswaCurang.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
