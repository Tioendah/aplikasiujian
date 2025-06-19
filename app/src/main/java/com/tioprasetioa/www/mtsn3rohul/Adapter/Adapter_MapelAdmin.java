package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Data_MapelAdmin;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Response_MapelUjian;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Adapter_MapelAdmin extends RecyclerView.Adapter<Adapter_MapelAdmin.ViewHolder> {

    private final List<Data_MapelAdmin>list;
    private Context context;

    public Adapter_MapelAdmin(List<Data_MapelAdmin> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txt_mapel;
        private final Button btn_pause;
        private final Button btn_start;
        private final Button btndone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_mapel = itemView.findViewById(R.id.nMapel_admin);
            context = itemView.getContext();
            btn_pause = itemView.findViewById(R.id.btn_pause);
            btn_start = itemView.findViewById(R.id.btn_start);
            btndone = itemView.findViewById(R.id.btn_done);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_mapel_admin,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Data_MapelAdmin dataMapelAdmin = list.get(position);
        holder.txt_mapel.setText(dataMapelAdmin.getMapel());
        holder.btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapel = holder.txt_mapel.getText().toString();
                int pause = 1;
                updateStatMapel(mapel,pause);
            }
        });
        holder.btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapel = holder.txt_mapel.getText().toString();
                int start = 2;
                updateStatMapel(mapel,start);
            }
        });
        holder.btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mapel = holder.txt_mapel.getText().toString();
                int done = 3;
                updateStatMapel(mapel,done);

            }
        });

    }

    private void updateStatMapel(String mapel, int status) {
        Toast.makeText(context, "berhasil", Toast.LENGTH_SHORT).show();
        Api api= Server.koneksiretrofit().create(Api.class);
        Call<Response_MapelUjian> call = api.updatemapelujian(mapel,status);
        call.enqueue(new Callback<Response_MapelUjian>() {
            @Override
            public void onResponse(Call<Response_MapelUjian> call, Response<Response_MapelUjian> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(context, "Berhasil, Ujian Dimulai", Toast.LENGTH_SHORT).show();
                    // Handle success response
                    // e.g., update UI or notify user
                } else {
                    // Handle unsuccessful response
                    // e.g., show error message
//                    Toast.makeText(context, "Gagal Memulai Ujian", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response_MapelUjian> call, Throwable throwable) {
                if (throwable instanceof IOException) {
                    // Network error (e.g., timeout, no internet connection)
//                    showNetworkErrorMessage(throwable.getMessage());
//                    updateStatMapel(data, status);
                } else {
                    // Conversion error or other unexpected error
//                    showGeneralErrorMessage(throwable.getMessage());
                }
            }

            private void showGeneralErrorMessage(String pesan) {
                Toast.makeText(context, pesan, Toast.LENGTH_LONG).show();

            }

            private void showNetworkErrorMessage(String pesan) {
                Toast.makeText(context, pesan, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}