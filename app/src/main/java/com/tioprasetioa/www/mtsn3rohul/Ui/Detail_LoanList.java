package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_LisloanDetail1;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_ListLoanDetail2;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Generic.BadResponse;
import com.tioprasetioa.www.mtsn3rohul.Generic.BaseResponse;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_DetailListLoan;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_DetailListLoan2;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_ListLoan;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_DetailListLoans;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_DetailListLoanss;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Detail_LoanList extends AppCompatActivity implements Metode_Interface, SwipeRefreshLayout.OnRefreshListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String KEY_LISTLOAN = "LISTLOAN";
    private Adapter_LisloanDetail1 adapter_lisloanDetail1;
    private RecyclerView recyclerView, recyclerView2;
    private TextView textView;
    private final List<ModelData_DetailListLoan>list = new ArrayList<>();


    //menampilkan data kedua
    private Adapter_ListLoanDetail2 adapter_listLoanDetail2;
    private final List<ModelData_DetailListLoan2>data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_loan_list);
        FindViewById();
        Logic();

    }
    public void refresh(){
        this.recreate();
    }


    @Override
    public void FindViewById() {
        recyclerView =findViewById(R.id.rc_listloandetail);
        recyclerView2 =findViewById(R.id.rc_listloandetail2);
        textView = findViewById(R.id.tx_retrieve);
        swipeRefreshLayout = findViewById(R.id.refresh_loan);
    }

    @Override
    public void Logic() {
        ModelData_ListLoan modelDataListLoan = getIntent().getParcelableExtra(KEY_LISTLOAN);
        String text = modelDataListLoan.getMember_id();
        textView.setText(text);
        viewdata(text);
        viewdata2(text);
    }
    private void viewdata2(String text) {
        adapter_listLoanDetail2 = new Adapter_ListLoanDetail2(data, getApplicationContext());
        recyclerView2.setAdapter(adapter_listLoanDetail2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<BaseResponse<Response_DetailListLoanss>>call = api.getListLoanss(text);
        call.enqueue(new Callback<BaseResponse<Response_DetailListLoanss>>() {
            @Override
            public void onResponse(Call<BaseResponse<Response_DetailListLoanss>> call, Response<BaseResponse<Response_DetailListLoanss>> response) {
                if (response.code() != 200){
                    try {
                        String str = response.errorBody().string();
                        BadResponse badResponse = new Gson().fromJson(str, BadResponse.class);
                        String message = "Error";
                        if (badResponse != null){
                            message = badResponse.getMessage();
                        }
                        Utils.Toast(Detail_LoanList.this,message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                data.addAll(response.body().getData().getLoans());
                adapter_listLoanDetail2.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<BaseResponse<Response_DetailListLoanss>> call, Throwable t) {

            }
        });
    }

    private void viewdata(String text) {
        adapter_lisloanDetail1 = new Adapter_LisloanDetail1(list,getApplicationContext());
        recyclerView.setAdapter(adapter_lisloanDetail1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<BaseResponse<Response_DetailListLoans>> call = api.getListLoans(text);
        call.enqueue(new Callback<BaseResponse<Response_DetailListLoans>>() {
            @Override
            public void onResponse(Call<BaseResponse<Response_DetailListLoans>> call, Response<BaseResponse<Response_DetailListLoans>> response) {
                if (response.code() != 200){
                    try {
                        String str = response.errorBody().string();
                        BadResponse badResponse = new Gson().fromJson(str, BadResponse.class);
                        String message = "Error";
                        if (badResponse != null){
                            message = badResponse.getMessage();
                        }
                        Utils.Toast(Detail_LoanList.this,message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                list.addAll(response.body().getData().getLoans());
                adapter_lisloanDetail1.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call<BaseResponse<Response_DetailListLoans>> call, Throwable t) {

            }
        });
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(false);
    }
}