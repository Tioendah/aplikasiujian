package com.tioprasetioa.www.mtsn3rohul.Fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_History;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_ListLoan;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_TampiData;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Generic.BadResponse;
import com.tioprasetioa.www.mtsn3rohul.Generic.BaseResponse;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_ListLoan;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_TampilData;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_ListLoan;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_TampilData;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Services.Notif_Servis;
import com.tioprasetioa.www.mtsn3rohul.Ui.Create_Member;
import com.tioprasetioa.www.mtsn3rohul.Ui.Login;
import com.tioprasetioa.www.mtsn3rohul.Utils.SharedPreference_Login;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Akun extends Fragment implements View.OnClickListener {

    private Context context;
    //tampildata
    private Adapter_TampiData adapter_tampiData;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView, textTotal;
    private final List<ModelData_TampilData>modelDataTampilDatalist = new ArrayList<>();
    private CardView cardView;
    /////////////////////////////////////////////

    //shared Login
    private SharedPreference_Login session_manager;
    private TextView etUsername, etName, kategori;
    private TextView etmembertype;
    private TextView etmembersince;
    private TextView etExpired;
    private ImageView imageView, Logout;
    private Button button;
    private TextView etgender;
    private String username, name;
    public String membertype, membersincedate, gender,expire;
    private ImageView imagelibrary;

    //HISTORY
    private RecyclerView recyclerViewhistory;
    private Adapter_History history;
    private RecyclerView.LayoutManager layoutManagerhistory;
    private CardView cardViewhistory;
    private final List<ModelData_TampilData>modelDataTampilDataHistroy = new ArrayList<>();


    //listloan
    private RecyclerView recyclerViewlistloan;
    private Adapter_ListLoan adapter_listLoan;
    private List<ModelData_ListLoan>list;
    private RelativeLayout relativeLayout;


    private TextView tv_tidakadapinjaman;
    //refresh button
    FloatingActionButton floatingActionButton, floatinsertdata;
    public Akun() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_library, container, false);

        context = getContext();

        //text tidak ada pinjaman
        tv_tidakadapinjaman = view.findViewById(R.id.tv_tidakadapinjaman);
        //LOGIN
        imagelibrary = view.findViewById(R.id.image_libary);

        session_manager = new SharedPreference_Login(getContext());
        etUsername = view.findViewById(R.id.etmember_name);
        etName = view.findViewById(R.id.username);
        etExpired = view.findViewById(R.id.etexpired);//
        etmembertype = view.findViewById(R.id.etmember_type);
        etgender = view.findViewById(R.id.txgender);
        etmembersince = view.findViewById(R.id.member_since_date);
        kategori = view.findViewById(R.id.txkategori);
        button = view.findViewById(R.id.cekLogin);
        Logout = view.findViewById(R.id.logout);

        //listloan
        recyclerViewlistloan  = view.findViewById(R.id.rc_listloan);
        relativeLayout = view.findViewById(R.id.rl_include);
        listloan();

        username = session_manager.getuserDetail().get(SharedPreference_Login.USERNAME);
        name = session_manager.getuserDetail().get(SharedPreference_Login.MEMBER_ID);
        gender = session_manager.getuserDetail().get(SharedPreference_Login.KEY_GENDER);
        membersincedate = session_manager.getuserDetail().get(SharedPreference_Login.KEY_SINCE_DATE);
        membertype = session_manager.getuserDetail().get(SharedPreference_Login.KEY_MEMBER_TYPE);
        expire = session_manager.getuserDetail().get(SharedPreference_Login.KEY_EXPIRE);

        logicAkun();
        floatingActionButton = view.findViewById(R.id.floating_refreshakun);
        floatinsertdata = view.findViewById(R.id.float_input);
        floatinsertdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), Create_Member.class));
                refresh();
            }
        });

        //TAMPIL DATA
        recyclerView = view.findViewById(R.id.recycle_library);
        textView = view.findViewById(R.id.tvmemberid);
        textTotal = view.findViewById(R.id.totaldenda);
        cardView = view.findViewById(R.id.cardViewlibrary);

        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter_tampiData = new Adapter_TampiData(modelDataTampilDatalist);
        recyclerView.setAdapter(adapter_tampiData);
        textView.setText(name);
        String member_id = textView.getText().toString();
        menampilkandata(member_id);
        cekdata();

        //HISTORY  //////////////////////////////////////////////////////////////////////////////
        recyclerViewhistory = view.findViewById(R.id.recycle_history);
        cardViewhistory = view.findViewById(R.id.cardakun);
        history = new Adapter_History(modelDataTampilDataHistroy);
        layoutManagerhistory = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerViewhistory.setLayoutManager(layoutManagerhistory);
        recyclerViewhistory.setAdapter(history);
        historymenampilkandata(member_id);
        cekadmin();

        //cek hide admin

        return view;
    }
    private boolean cekadmin() {
        boolean cek = false;
        if (name==null){
            name = "Belum Login";
        }
        if (name.equals("admin")){
            cek = true;
            cardView.setVisibility(View.GONE);
            cardViewhistory.setVisibility(View.GONE);
            recyclerViewlistloan.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.VISIBLE);
            floatinsertdata.setVisibility(View.VISIBLE);
        }else if (name.equals("Belum Login")){
            cek = true;
            cardView.setVisibility(View.VISIBLE);
            cardViewhistory.setVisibility(View.VISIBLE);
        }else {
            cek = true;
            cardView.setVisibility(View.VISIBLE);
            cardViewhistory.setVisibility(View.VISIBLE);
        }
        return cek;
    }

    private void listloan() {
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_ListLoan> call = api.LIST_LOAN_CALL();
        call.enqueue(new Callback<Response_ListLoan>() {
            @Override
            public void onResponse(Call<Response_ListLoan> call, Response<Response_ListLoan> response) {
                list = response.body().getData();
                if (list != null) {
                    list = response.body().getData();
                    adapter_listLoan = new Adapter_ListLoan(list, getContext());
                    recyclerViewlistloan.setAdapter(adapter_listLoan);
                    recyclerViewlistloan.setLayoutManager(new LinearLayoutManager(getContext()));
                } else {
                    // Pastikan getContext() tidak null
                    if (getContext() != null) {
                        Utils.Toast(getContext(), "Tidak Ada pinjaman");
                    }
                }
            }

            @Override
            public void onFailure(Call<Response_ListLoan> call, Throwable t) {
                // Pastikan getContext() tidak null
                if (getContext() != null) {
                    Utils.Toast(getContext(), t.getMessage());
                }
            }
        });
    }


    private void logicAkun() {

        etUsername.setText(username);
        etName.setText(name);
        try {
            etgender.setText(gender);
        }catch (Exception e){
            etgender.setText("2");
        }
        //LOGIN TULISAN BELUM LOGIN

        //logic login
        etmembertype.setText(membertype);
        etmembersince.setText(membersincedate);
        etExpired.setText(expire);
        button.setVisibility(View.GONE);
        button.setOnClickListener(this);
        Logout.setOnClickListener(this);
        //untuk mengganti gambar profil akun
        String gender = etgender.getText().toString();
        if (gender.equals("1")){
            imagelibrary.setImageResource(R.drawable.ic_person_library);
        }else if (gender.equals("0")){
            imagelibrary.setImageResource(R.drawable.ic_muslim);
        }else if (gender.equals("2")){
            imagelibrary.setImageResource(R.drawable.ic_default);

        }
        if (!session_manager.isLoggedin()){
            button.setVisibility(View.VISIBLE);
            context.stopService(new Intent(getContext(), Notif_Servis.class));
        }

    }
    private void historymenampilkandata(String member_id) {
        modelDataTampilDataHistroy.clear();
        history.notifyDataSetChanged();
        Api api = Server.koneksiretrofit().create(Api.class);
        api.MenampilkanData(member_id).enqueue(new Callback<BaseResponse<Response_TampilData>>() {
            @Override
            public void onResponse(Call<BaseResponse<Response_TampilData>> call, Response<BaseResponse<Response_TampilData>> response) {
                if (response.code() != 200){
                    try {
                        String pesan = response.errorBody().string();
                        BadResponse badResponse = new Gson().fromJson(pesan,BadResponse.class);
                        String message = "Error";
                    }catch (Exception e){
                        e.getMessage();

                    }
                    return;
                }
                modelDataTampilDataHistroy.addAll(response.body().getData().getLoans());
                history.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResponse<Response_TampilData>> call, Throwable t) {
                Activity activity = getActivity();
                if (activity != null) {
                    Toast.makeText(activity, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void menampilkandata(String member_id) {
        modelDataTampilDatalist.clear();
        adapter_tampiData.notifyDataSetChanged();
        Api api = Server.koneksiretrofit().create(Api.class);
        api.MenampilkanData(member_id).enqueue(new Callback<BaseResponse<Response_TampilData>>() {
            @Override
            public void onResponse(Call<BaseResponse<Response_TampilData>> call, Response<BaseResponse<Response_TampilData>> response) {
                if (response.code() != 200){
                    try {
                        String  str = response.errorBody().string();
                        BadResponse badResponse = new Gson().fromJson(str, BadResponse.class);
                        String message = "error";
                        if (badResponse != null){
                            message = badResponse.getMessage();
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    return;
                }

                modelDataTampilDatalist.addAll(response.body().getData().getLoans());

                adapter_tampiData.notifyDataSetChanged();
                int totaldenda = 0;
                int denda = 2000;
                Calendar today = Calendar.getInstance();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                for(int i = 0; i< modelDataTampilDatalist.size();i++){
                    try {
                        String s = modelDataTampilDatalist.get(i).getReturnDate();
                        if (s==null){
                            Calendar batasCalendar = Calendar.getInstance();
                            Date batasDate = format.parse(modelDataTampilDatalist.get(i).getDueDate());
                            batasCalendar.setTime(batasDate);
                            long lamaDenda = daysDiff(batasCalendar,today);
                            totaldenda += lamaDenda * denda;
                        }

                    }catch (ParseException e){
                        e.getMessage();
                    }
                }
                textTotal.setText("Rp. " + totaldenda);
                String kategoris = kategori.getText().toString();
                String tidakadapinjaman = textTotal.getText().toString();

                if (tidakadapinjaman.equals("Rp. 0")){
                    tv_tidakadapinjaman.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Response_TampilData>> call, Throwable t) {

            }
        });
    }
    private long daysDiff(Calendar calendar1, Calendar calendar) {
        long lama = 0;
        Calendar tanggal = (Calendar) calendar1.clone();
        while (tanggal.before(calendar)) {
            tanggal.add(Calendar.DAY_OF_MONTH, 1);
            lama++;
        }
        return lama;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cekLogin:
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
                refresh();
                break;
            case R.id.logout:
                session_manager.logoutsesion();
                Intent i = new Intent(getActivity(), Login.class);
                startActivity(i);
                refresh();
                break;

        }
    }

    public void refresh() {
        Utils.tapview(getContext(), R.id.floating_refreshakun, "Silahkan Refresh",null);
    }

    public boolean cekdata() {
        boolean tidakbolehkosong = false;
        if (TextUtils.isEmpty(membertype)) {
            kategori.setText("Belum login");
            Toast.makeText(getContext(), "Anda Belum Login", Toast.LENGTH_SHORT).show();
            tidakbolehkosong = true;
        } else if (membertype.equals("1")) {
            kategori.setText("Siswa");
            tidakbolehkosong = true;
        } else if (membertype.equals("2")) {
            kategori.setText("Umum");
            tidakbolehkosong = true;
        }
        return tidakbolehkosong;
    }

    @Override
    public void onResume() {
        super.onResume();

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}