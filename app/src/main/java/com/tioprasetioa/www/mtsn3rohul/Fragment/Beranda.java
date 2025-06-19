package com.tioprasetioa.www.mtsn3rohul.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_Galeri_Home;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_IconHome;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_ImageSlider;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_Youtube;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Youtube;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Galeri_Home;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Image_Slider;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Galeri_Home;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_ImageSlider;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Youtube;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Ui.Login;
import com.tioprasetioa.www.mtsn3rohul.Utils.SharedPreference_Login;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Beranda extends Fragment  {

    //slider image
    private SliderView sliderView;
    private Adapter_ImageSlider adapter_imageSlider;
    private List<Model_data_Image_Slider> lismoimageslider;
    private ImageView imageView1;
    private ShimmerFrameLayout shimmerFrameLayout, sFrameLayoutgalerihome, shimmerFrameLayoutgaleri2;

    //waktu
    private TextView txt_waktu, name;
    private SharedPreference_Login sharedPreference_login;

    //Galeri Home
    private RecyclerView rc_galeri;
    private Adapter_Galeri_Home adapter_galeri_home;
    private RecyclerView.LayoutManager layoutManagergalerihome;
    private List<Model_data_Galeri_Home> model_data_galeri_homeList;
    //Icon Home
    private RecyclerView recyclerView;
    private Adapter_IconHome adapter_iconHome;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String>txtlist;
    private ArrayList<Integer>imglist;
    private ArrayList<Integer>backlist;

    //youtube
    private RecyclerView recyclerViewyt;
    private Adapter_Youtube adapter_youtube;
    private List<Model_Youtube>listyt = new ArrayList<>();


    //icon
    private final String[] textdata = {"Profil Sekolah","Profil Guru", "Alumni Madrasah","Rekap \nAbsen Guru","Ujian", "Ujian \nPPDB", "Lokasi Sekolah", "PPDB"};
    private final int[] imgdata ={R.drawable.ic_home,R.drawable.ic_person,R.drawable.ic_osima,R.drawable.finger_print,R.drawable.ic_perangkatpbm, R.drawable.ic_perangkatpbm, R.drawable.ic_pin, R.drawable.ic_registration};
    private final int []backdata= {R.drawable.shape_background, R.drawable.shape_biru,R.drawable.shape_hijau, R.drawable.shape_kuning,R.drawable.shape_merah, R.drawable.shape_biru200,  R.drawable.shape_biru500, R.drawable.shape_abu};
    //Construktor
    public Beranda() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        sliderView = view.findViewById(R.id.imageSlider);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_home);

        imageView1 = view.findViewById(R.id.image_beranda);
        imageView1.setImageResource(R.drawable.icon);
        //Icon home
        txtlist = new ArrayList<>();
        imglist = new ArrayList<>();
        backlist = new ArrayList<>();
        Utils.tapview(getContext(),R.drawable.ic_perangkatpbm,"Tes Akademik","Silahkan Klik di sini ");
        recyclerView = view.findViewById(R.id.rc_iconhome);

        int kolom = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), kolom));
//        recyclerView.setHasFixedSize(true);
        adapter_iconHome = new Adapter_IconHome(getContext(), txtlist, imglist, backlist);
        recyclerView.setAdapter(adapter_iconHome);
        data();

        //metode waktu
        txt_waktu = view.findViewById(R.id.id_morning);
        waktu();

        //nama akun
        sharedPreference_login = new SharedPreference_Login(getContext());
        String nama = sharedPreference_login.getuserDetail().get(SharedPreference_Login.USERNAME);
        name = view.findViewById(R.id.akun);
        if (nama==null){
            name.setVisibility(View.GONE);
        }else {
            name.setText(nama);
        }
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Login.class));
            }
        });



        //galerihome
        rc_galeri = view.findViewById(R.id.rc_galeri_home);
        sFrameLayoutgalerihome = view.findViewById(R.id.shimmer_galeri);
        shimmerFrameLayoutgaleri2 = view.findViewById(R.id.shimmer_galeri2);

        //youtube
        recyclerViewyt = view.findViewById(R.id.recycle_youtube);


        menampilkanyoutube();
        menampilkandata();
        menampilkandatagaleri();
        return view;//catatan tidak boleh dibawah ini
    }
    private void menampilkanyoutube() {
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Youtube>call = api.metodyoutube();
        call.enqueue(new Callback<Response_Youtube>() {
            @Override
            public void onResponse(Call<Response_Youtube> call, @NonNull Response<Response_Youtube> response) {
                if (response.isSuccessful()){
                    shimmerFrameLayoutgaleri2.stopShimmer();
                    shimmerFrameLayoutgaleri2.setVisibility(View.GONE);
                    sFrameLayoutgalerihome.stopShimmer();
                    sFrameLayoutgalerihome.setVisibility(View.GONE);
                    assert response.body() != null;
                    listyt = response.body().getData();
                    adapter_youtube = new Adapter_Youtube(getContext(),listyt);
                    recyclerViewyt.setAdapter(adapter_youtube);

//                recyclerViewyt.setLayoutManager(linearLayoutManager);
                    LinearLayoutManager lm = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,true);
                    recyclerViewyt.setLayoutManager(lm);
                }else {
                    Log.e("Error",response.message());
                }

            }

            @Override
            public void onFailure(Call<Response_Youtube> call, Throwable t) {


            }
        });
    }

    private void menampilkandatagaleri() {
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Galeri_Home> response_galeri_homeCall = api.galeri_home();

        response_galeri_homeCall.enqueue(new Callback<Response_Galeri_Home>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<Response_Galeri_Home> call, Response<Response_Galeri_Home> response) {
                // Pastikan response.body() tidak null sebelum diakses
                if (response.isSuccessful() && response.body() != null) {
                    List<Model_data_Galeri_Home> data = response.body().getData();

                    // Validasi apakah data tidak null
                    if (data != null && !data.isEmpty()) {
                        model_data_galeri_homeList = data;

                        // Pastikan adapter tidak null
                        if (adapter_galeri_home == null) {
                            adapter_galeri_home = new Adapter_Galeri_Home(getContext(), model_data_galeri_homeList);
                            rc_galeri.setAdapter(adapter_galeri_home);
                        } else {
//                            adapter_galeri_home.updateData(model_data_galeri_homeList);
                            adapter_galeri_home.notifyDataSetChanged();
                        }

                        // Atur RecyclerView layout manager
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                        linearLayoutManager.setReverseLayout(true);
                        linearLayoutManager.setStackFromEnd(true);
                        rc_galeri.setLayoutManager(linearLayoutManager);

                    } else {
                        Log.e("API Response", "Data galeri kosong");
                    }
                } else {
                    Log.e("API Response", "Response tidak berhasil: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Response_Galeri_Home> call, Throwable t) {
                Log.e("API Error", "Gagal memuat data galeri: " + t.getMessage());
            }
        });
    }


    private void waktu() {
        Calendar calender= Calendar.getInstance();
        int waktu = calender.get(Calendar.HOUR_OF_DAY);
        if (waktu >= 0 && waktu <12){
            txt_waktu.setText("Morning");
        }else if (waktu>=12 && waktu <15){
            txt_waktu.setText("Noon");
        }else if (waktu>=15 && waktu<18){
            txt_waktu.setText("Afternoon");
        }else if (waktu>=18 && waktu<24){
            txt_waktu.setText("Night");
        }
    }

    private void data() {
        for (int a=0;a< textdata.length;a++){
            txtlist.add(textdata[a]);
            imglist.add(imgdata[a]);
            backlist.add(backdata[a]);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void menampilkandata() {
        Api api_ = Server.koneksiretrofit().create(Api.class);
        Call<Response_ImageSlider> sliderCall = api_.image_sliderCall();
        sliderCall.enqueue(new Callback<Response_ImageSlider>() {
            @Override
            public void onResponse(Call<Response_ImageSlider> call, Response<Response_ImageSlider> response) {
                if (response.isSuccessful() && response.body() != null) { // Pastikan response sukses dan body tidak null
                    lismoimageslider = response.body().getData();
                    if (lismoimageslider != null) { // Pastikan getData() tidak null
                        shimmerFrameLayout.stopShimmer();
                        shimmerFrameLayout.setVisibility(View.GONE);
                        adapter_imageSlider = new Adapter_ImageSlider(getContext(), lismoimageslider);
                        sliderView.setSliderAdapter(adapter_imageSlider);
                        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
                        sliderView.setIndicatorSelectedColor(Color.WHITE);
                        sliderView.setIndicatorUnselectedColor(Color.GRAY);
                        sliderView.startAutoCycle();
                        sliderView.canScrollVertically(sliderView.getCurrentPagePosition());
                    } else {
                        Log.e("Error", "Data slider is null");
                    }
                } else {
                    Log.e("Error", response.message() != null ? response.message() : "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<Response_ImageSlider> call, Throwable t) {
                Log.e("Error", t.getMessage() != null ? t.getMessage() : "Unknown error");
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayoutgaleri2.startShimmer();
        shimmerFrameLayoutgaleri2.setVisibility(View.VISIBLE);
        sFrameLayoutgalerihome.startShimmer();
        sFrameLayoutgalerihome.setVisibility(View.VISIBLE);

    }


}