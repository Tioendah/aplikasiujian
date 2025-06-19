package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_DetailBuku;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Generic.BadResponse;
import com.tioprasetioa.www.mtsn3rohul.Generic.BaseResponse;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Data_Kategori;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_DetailBuku;
import com.tioprasetioa.www.mtsn3rohul.Server.IPServer;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_DetailBuku;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Katalog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_Buku extends AppCompatActivity implements Metode_Interface {
    //Buku Pertama
    public static final String KEY_Detail = "Detail";
    public static final String KEY_Key = "Key";
    private TextView txBiblioId,txauthor,txpublisher,txtitle;
    private ImageView iv_Detail;
    private Button btn_detail;
    Model_Katalog model_katalog;
    private final IPServer ipServer = new IPServer();

    //Buku Kedua
    private Adapter_DetailBuku adapter_detailBuku;
    private RecyclerView recyclerView;
    private TextView tvbibliom;//untuk menarik data
    private final List<Model_DetailBuku>model_detailBukus = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buku);

        FindViewById();
        initLogic2();
        Logic();
    }
    @Override
    public void FindViewById() {
        //FindViewById Buku Pertama
        btn_detail = findViewById(R.id.btn_detail);
        txBiblioId = findViewById(R.id.biblio_id);
        txpublisher = findViewById(R.id.publisher);
        txtitle = findViewById(R.id.title_detail);
        txauthor = findViewById(R.id.author);
        iv_Detail = findViewById(R.id.img_detail);

        //FindViewById Buku Kedua
        tvbibliom = findViewById(R.id.txBiblioM);
        recyclerView = findViewById(R.id.recycle_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter_detailBuku = new Adapter_DetailBuku(model_detailBukus);
        recyclerView.setAdapter(adapter_detailBuku);

    }

    @Override
    public void initLogic2() {

    }
    @Override
    public void Logic() {
        //cek
        String getdata = getIntent().getStringExtra(KEY_Key);

        if (getdata.equals("1")){
            Model_Data_Kategori modelDataKategori = getIntent().getParcelableExtra(KEY_Detail);
            txBiblioId.setText(String.valueOf(modelDataKategori.getBiblio_id()));
            txpublisher.setText(modelDataKategori.getPublisher());
            txauthor.setText(modelDataKategori.getAuthor());
            txtitle.setText(modelDataKategori
                    .getTitle());
            Glide.with(this)
                    .load(ipServer.slims+modelDataKategori.getImage())
                    .fitCenter()
                    .into(iv_Detail);
        }else if (getdata.equals("2")){
            model_katalog = getIntent().getParcelableExtra(KEY_Detail);
            txBiblioId.setText(String.valueOf(model_katalog.getBiblio_id()));
            txpublisher.setText(model_katalog.getPublisher());
            txauthor.setText(model_katalog.getAuthor());
            txtitle.setText(model_katalog.getTitle());
            Glide.with(this)
                    .load(ipServer.slims+model_katalog.getImage())
                    .fitCenter()
                    .into(iv_Detail);
        }

        //Detail Buku Pertama

//        if (model_katalog!=null){
//            txBiblioId.setText(String.valueOf(model_katalog.getBiblio_id()));
//            txpublisher.setText(model_katalog.getPublisher());
//            txauthor.setText(model_katalog.getAuthor());
//            txtitle.setText(model_katalog.getTitle());
//            Glide.with(this)
//                    .load(ipServer.slims+model_katalog.getImage())
//                    .fitCenter()
//                    .into(iv_Detail);
//        }
        //Detail Buku Kedua
        String menarikdata = txBiblioId.getText().toString();
        tvbibliom.setText(menarikdata);
        String biblio_id = tvbibliom.getText().toString();
        tarikdata(biblio_id);

        //btn_wa
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String buku = txtitle.getText().toString();
                Intent intent = null, chooser = null;
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=6285355700153&text=Assalamulaikum bu Rini. Saya (namakamu) ingin bertanya buku yang berjudul "+buku+" tersedia bu?"));
                chooser = Intent.createChooser(intent,"Pilih Whatsapp");
                startActivity(chooser);
            }
        });
    }
    private void tarikdata(String biblio_id) {
        model_detailBukus.clear();
        adapter_detailBuku.notifyDataSetChanged();
        Api api = Server.koneksiretrofit().create(Api.class);
        api.getdetail(biblio_id).enqueue(new Callback<BaseResponse<Response_DetailBuku>>() {
            @Override
            public void onResponse(Call<BaseResponse<Response_DetailBuku>> call, Response<BaseResponse<Response_DetailBuku>> response) {
                if (response.code() !=200){
                    try {
                        String str = response.errorBody().string();
                        BadResponse badResponse = new Gson().fromJson(str,BadResponse.class);
                        String message = "Data Tidak Tampil";
                        if (badResponse != null){
                            message = badResponse.getMessage();
                        }
                        Toast.makeText(Detail_Buku.this,message,Toast.LENGTH_SHORT).show();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    return;
                }
                model_detailBukus.addAll(response.body().getData().getBiblio());
                adapter_detailBuku.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<BaseResponse<Response_DetailBuku>> call, Throwable t) {
                Toast.makeText(Detail_Buku.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}