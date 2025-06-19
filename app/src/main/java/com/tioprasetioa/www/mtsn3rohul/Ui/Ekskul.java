package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextSwitcher;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_Ekskul;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.ekskul.Item;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Ekskul extends AppCompatActivity implements Metode_Interface {


    private FeatureCoverFlow mCoverFlow;
    private Adapter_Ekskul mAdapter;
    private final ArrayList<Item> mData = new ArrayList<>(0);
    private TextSwitcher mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ekskul);


        fetchData();



        mAdapter = new Adapter_Ekskul(this,mData);
        mAdapter.setData(mData);
        mCoverFlow = findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(Ekskul.this,
//                        getResources().getString(mData.get(position).getId()),
//                        Toast.LENGTH_SHORT).show();
            }
        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
//                mTitle.setText(getResources().getString(mData.get(position).getId()));
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });
    }

    private void fetchData() {
        Api apiService = Server.koneksiretrofit().create(Api.class);
        Call<List<Item>> call = apiService.getItems();
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(Call<List<Item>> call, Response<List<Item>> response) {
                if (response.isSuccessful()) {
                    List<Item> games = response.body();
                    if (games != null) {
                        mData.addAll(games);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(Ekskul.this, "Failed to get data from API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Item>> call, Throwable throwable) {

            }
        });

    }

    @Override
    public void FindViewById() {



    }

    @Override
    public void Logic() {


    }
    private void data(){
//
    }

}///image/bruno.jpg