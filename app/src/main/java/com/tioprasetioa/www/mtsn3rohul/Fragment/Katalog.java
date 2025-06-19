package com.tioprasetioa.www.mtsn3rohul.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_Button_Kategori;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_Katalog;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_Kategori;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Model.Data.Model_Button_Kategori;
import com.tioprasetioa.www.mtsn3rohul.Model.Data.Parcel_Btn_Kategori;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Data_Kategori;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Katalog;
import com.tioprasetioa.www.mtsn3rohul.Model.pengunjung.DataItem_pengunjung;
import com.tioprasetioa.www.mtsn3rohul.Model.pengunjung.Response_pengunjung;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Kategori;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Katalog extends Fragment implements Adapter_Button_Kategori.Itemclicklistener {


    //kategori 400
    private List<Model_Data_Kategori> list2 = new ArrayList<>();

    //KATALOG SEARCH VIEW
    private RecyclerView recyclerView;
    private Adapter_Katalog adapter_katalog;
    private Api api;
    private SearchView searchView;
    private ProgressBar progressBar;
    private List<Model_Katalog>modelKatalogArrayList;

    //Button Kategori
    private RecyclerView rcBtnKategori;
    private final ArrayList<Parcel_Btn_Kategori>list = new ArrayList<>();

    BarChart barChart;


    public Katalog() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_katalog, container, false);

        //Button Kategori
        rcBtnKategori =view.findViewById(R.id.rc_btnkategori);
        barChart = view.findViewById(R.id.barchart);
        Utils utils = new Utils();
        TextView textView = view.findViewById(R.id.examtx);
        TextView txhasil = view.findViewById(R.id.examhasil);

        utils.Volley(getContext(), "https://mtsn3rokanhulu.sch.id/api/ExamBuku.php", textView, "item_id", new Utils.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                String data = "Jumlah Buku ="+result+" Eksemplar";
                txhasil.setText(data);
            }
        });
        list.addAll(Model_Button_Kategori.menampilkandata());
        rcBtnKategori.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        Adapter_Button_Kategori adapter_button_kategori = new Adapter_Button_Kategori(list);
        adapter_button_kategori.seClickListener(this);
        rcBtnKategori.setAdapter(adapter_button_kategori);


        dataPengunjung();

        recyclerView = view.findViewById(R.id.recycle_katalog);
        progressBar = view.findViewById(R.id.progress_katalog);
        AllData("search_biblio","");
        searchView = view.findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchdata();
        return view;
    }
    @Override
    public void onItemClick(View view, int pos) {

    }

    @Override
    public void onItemClick(int position) {
        if (position==0){
            AllData("search_biblio","");
            searchdata();
        }else if (position==1){
            KaryaUmum();
        }else if (position ==2){
            Filsafat();
        }else if (position ==3 ){
            Agama();
        }else if (position == 4){
            IlmuSosial();
        }else if (position ==5){
            Bahasa();
        }else if (position ==6){
            IlmuMurni();
        }else if(position == 7){
            IlmuTerapan();
        }else if (position==8){
            Kesenian();
        }else if (position==9){
            Kesusatraan();
        }else if (position==10){
            Geografi();
        }
    }

    private void dataPengunjung() {
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_pengunjung> call = api.getData();
        call.enqueue(new Callback<Response_pengunjung>() {
            @Override
            public void onResponse(Call<Response_pengunjung> call, Response<Response_pengunjung> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<DataItem_pengunjung> data = response.body().getData();
                    if (data != null) {
                        // Mengurutkan data berdasarkan ID
                        Collections.sort(data, new Comparator<DataItem_pengunjung>() {
                            @Override
                            public int compare(DataItem_pengunjung o1, DataItem_pengunjung o2) {
                                return o1.getId().compareTo(o2.getId());
                            }
                        });

                        ArrayList<BarEntry> entries = new ArrayList<>();
                        ArrayList<String> labels = new ArrayList<>();

                        // Mengisi data entries dan labels dari data response
                        int index = 0;
                        for (DataItem_pengunjung dataItemPengunjung : data) {
                            String pengunjung = dataItemPengunjung.getPengunjung();
                            String tahun = dataItemPengunjung.getTahun();

                            // Logging data
                            Log.d("PengunjungData", "Index: " + index + ", Pengunjung: " + pengunjung + ", Tahun: " + tahun);

                            if (pengunjung != null && !pengunjung.isEmpty()) {
                                try {
                                    entries.add(new BarEntry(Integer.parseInt(pengunjung), index));
                                    labels.add(tahun); // Misalnya, getTahun() mengembalikan tahun dalam format string
                                    index++;
                                } catch (NumberFormatException e) {
                                    Log.e("PengunjungData", "Error parsing pengunjung to integer: " + pengunjung, e);
                                }
                            } else {
                                Log.w("PengunjungData", "Pengunjung data is null or empty at index: " + index);
                            }
                        }

                        BarDataSet barDataSet = new BarDataSet(entries, "Pengunjung");
                        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                        BarData barData = new BarData(labels, barDataSet);
                        barChart.setData(barData); // Set data ke chart
                        barChart.setDescription("Data Kunjungan Pertahun");  // Set the description
                        barChart.animateY(5000);
                        barChart.invalidate(); // Refresh chart
                    } else {
                        Log.w("PengunjungData", "Data is null");
                    }
                } else {
                    Log.w("PengunjungData", "Response not successful or body is null");
                }
            }

            @Override
            public void onFailure(Call<Response_pengunjung> call, Throwable t) {
                Log.e("PengunjungData", "API call failed", t);
            }
        });
    }
    public void AllData(String type, String key) {
//        recyclerView.setHasFixedSize(true);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
        api = Server.koneksiretrofit().create(Api.class);
        Call<List<Model_Katalog>>call = api.getKatalog(type,key);
        call.enqueue(new Callback<List<Model_Katalog>>() {
            @Override
            public void onResponse(Call<List<Model_Katalog>> call, Response<List<Model_Katalog>> response) {

                if (response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    modelKatalogArrayList = response.body();
                    adapter_katalog = new Adapter_Katalog(modelKatalogArrayList, getContext());
                    recyclerView.setAdapter(adapter_katalog);
                }
            }

            @Override
            public void onFailure(Call<List<Model_Katalog>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
    private void searchdata() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                AllData("search_biblio",query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                AllData("search_biblio",newText);
                return false;
            }
        });

    }
    public void KaryaUmum(){
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Kategori> call = api.kategori99();
        call.enqueue(new Callback<Response_Kategori>() {
            @Override
            public void onResponse(Call<Response_Kategori> call, Response<Response_Kategori> response) {
                list2 = response.body().getData();
                if (list2 == null){
                    Utils.Toast(getContext(),"Buku Kosong");
                    recyclerView.setVisibility(View.GONE);
                }else {
                    list2 = response.body().getData();
                    Adapter_Kategori adapter_kategori = new Adapter_Kategori(list2);
                    recyclerView.setAdapter(adapter_kategori);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Response_Kategori> call, Throwable t) {

            }
        });
    }
    public void Filsafat(){
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Kategori> call = api.kategori100();
        call.enqueue(new Callback<Response_Kategori>() {
            @Override
            public void onResponse(Call<Response_Kategori> call, Response<Response_Kategori> response) {
                list2 = response.body().getData();
                if (list2 == null){
                    Utils.Toast(getContext(),"Buku Kosong");
                    recyclerView.setVisibility(View.GONE);
                }else {
                    list2 = response.body().getData();
                    Adapter_Kategori adapter_kategori = new Adapter_Kategori(list2);
                    recyclerView.setAdapter(adapter_kategori);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Response_Kategori> call, Throwable t) {

            }
        });
    }
    public void Agama (){
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Kategori> call = api.kategori200();
        call.enqueue(new Callback<Response_Kategori>() {
            @Override
            public void onResponse(Call<Response_Kategori> call, Response<Response_Kategori> response) {
                list2 = response.body().getData();
                if (list2 == null){
                    Utils.Toast(getContext(),"Buku Kosong");
                    recyclerView.setVisibility(View.GONE);
                }else {
                    list2 = response.body().getData();
                    Adapter_Kategori adapter_kategori = new Adapter_Kategori(list2);
                    recyclerView.setAdapter(adapter_kategori);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Response_Kategori> call, Throwable t) {

            }
        });
    }
    public void IlmuSosial (){
        recyclerView.setVisibility(View.VISIBLE);
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Kategori> call = api.kategori300();
        call.enqueue(new Callback<Response_Kategori>() {
            @Override
            public void onResponse(Call<Response_Kategori> call, Response<Response_Kategori> response) {
                list2 = response.body().getData();
                if (list2 == null){
                    Utils.Toast(getContext(),"Buku Kosong");
                    recyclerView.setVisibility(View.GONE);
                }else {
                    list2 = response.body().getData();
                    Adapter_Kategori adapter_kategori = new Adapter_Kategori(list2);
                    recyclerView.setAdapter(adapter_kategori);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Response_Kategori> call, Throwable t) {

            }
        });
    }
    public void Bahasa (){
        recyclerView.setVisibility(View.VISIBLE);
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Kategori> call = api.kategori400();
        call.enqueue(new Callback<Response_Kategori>() {
            @Override
            public void onResponse(Call<Response_Kategori> call, Response<Response_Kategori> response) {
                assert response.body() != null;
                list2 = response.body().getData();
                if (list2 == null){
                    Utils.Toast(getContext(),"Buku Kosong");
                    recyclerView.setVisibility(View.GONE);
                }else {
                    list2 = response.body().getData();
                    Adapter_Kategori adapter_kategori = new Adapter_Kategori(list2);
                    recyclerView.setAdapter(adapter_kategori);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Response_Kategori> call, Throwable t) {

            }
        });
    }
    public void IlmuMurni (){
        recyclerView.setVisibility(View.VISIBLE);
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Kategori> call = api.kategori500();
        call.enqueue(new Callback<Response_Kategori>() {
            @Override
            public void onResponse(Call<Response_Kategori> call, Response<Response_Kategori> response) {
                list2 = response.body().getData();
                if (response.isSuccessful()){
                    if (list2 == null){
                        Utils.Toast(getContext(),"Buku Kosong");
                        recyclerView.setVisibility(View.GONE);
                    }else {
                        list2 = response.body().getData();
                        Adapter_Kategori adapter_kategori = new Adapter_Kategori(list2);
                        recyclerView.setAdapter(adapter_kategori);
                        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
                        recyclerView.setVisibility(View.VISIBLE);
                    }
                }


            }

            @Override
            public void onFailure(Call<Response_Kategori> call, Throwable t) {

            }
        });
    }
    public void IlmuTerapan (){
        recyclerView.setVisibility(View.VISIBLE);
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Kategori> call = api.kategori600();
        call.enqueue(new Callback<Response_Kategori>() {
            @Override
            public void onResponse(Call<Response_Kategori> call, Response<Response_Kategori> response) {
                list2 = response.body().getData();
                if (list2 == null){
                    Utils.Toast(getContext(),"Buku Kosong");
                    recyclerView.setVisibility(View.GONE);
                }else {
                    list2 = response.body().getData();
                    Adapter_Kategori adapter_kategori = new Adapter_Kategori(list2);
                    recyclerView.setAdapter(adapter_kategori);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Response_Kategori> call, Throwable t) {

            }
        });
    }
    public void Kesenian (){
        recyclerView.setVisibility(View.VISIBLE);
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Kategori> call = api.kategori700();
        call.enqueue(new Callback<Response_Kategori>() {
            @Override
            public void onResponse(Call<Response_Kategori> call, Response<Response_Kategori> response) {
                list2 = response.body().getData();
                if (list2 == null){
                    Utils.Toast(getContext(),"Buku Kosong");
                    recyclerView.setVisibility(View.GONE);
                }else {
                    list2 = response.body().getData();
                    Adapter_Kategori adapter_kategori = new Adapter_Kategori(list2);
                    recyclerView.setAdapter(adapter_kategori);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Response_Kategori> call, Throwable t) {

            }
        });
    }
    public void Kesusatraan (){
        recyclerView.setVisibility(View.VISIBLE);
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Kategori> call = api.kategori800();
        call.enqueue(new Callback<Response_Kategori>() {
            @Override
            public void onResponse(Call<Response_Kategori> call, Response<Response_Kategori> response) {
                list2 = response.body().getData();
                if (list2 == null){
                    Utils.Toast(getContext(),"Buku Kosong");
                    recyclerView.setVisibility(View.GONE);
                }else {
                    list2 = response.body().getData();
                    Adapter_Kategori adapter_kategori = new Adapter_Kategori(list2);
                    recyclerView.setAdapter(adapter_kategori);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Response_Kategori> call, Throwable t) {

            }
        });
    }
    public void Geografi (){
        recyclerView.setVisibility(View.VISIBLE);
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Kategori> call = api.kategori900();
        call.enqueue(new Callback<Response_Kategori>() {
            @Override
            public void onResponse(Call<Response_Kategori> call, Response<Response_Kategori> response) {
                list2 = response.body().getData();
                if (list2 == null){
                    Utils.Toast(getContext(),"Buku Kosong");
                    recyclerView.setVisibility(View.GONE);
                }else {
                    list2 = response.body().getData();
                    Adapter_Kategori adapter_kategori = new Adapter_Kategori(list2);
                    recyclerView.setAdapter(adapter_kategori);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2, LinearLayoutManager.VERTICAL,true));
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(Call<Response_Kategori> call, Throwable t) {

            }
        });
    }





}
