package com.tioprasetioa.www.mtsn3rohul.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_Berita;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Berita;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Berita;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Berita_Sekolah extends Fragment {

    //Berita
    private RecyclerView recyclerView;
    private Adapter_Berita adapter_berita;
    private List<Model_data_Berita>modelDataBeritaList;

    //progress bar
    private ProgressBar progressBar;
    public Berita_Sekolah() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_berita__sekolah, container, false);
        recyclerView = view.findViewById(R.id.recycle_berita);
        menampilkandata();
        progressBar = view.findViewById(R.id.progress_circular);
        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("news", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("news_key","beranda");
        editor.apply();
        return view;
    }
    private void menampilkandata() {
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Berita> responseBeritaCall = api.berita_metode();
        responseBeritaCall.enqueue(new Callback<Response_Berita>() {
            @Override
            public void onResponse(Call<Response_Berita> call, Response<Response_Berita> response) {
                modelDataBeritaList = response.body().getData();
                if (modelDataBeritaList == null) {
                    // Pastikan getContext() tidak null
                    if (getContext() != null) {
                        Utils.Toast(getContext(), "Berita Kosong");
                    }
                } else {
                    modelDataBeritaList = response.body().getData();
                    adapter_berita = new Adapter_Berita(getContext(), modelDataBeritaList);
                    recyclerView.setAdapter(adapter_berita);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                    linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Response_Berita> call, Throwable t) {
                // Pastikan getContext() tidak null
                if (getContext() != null) {
                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}