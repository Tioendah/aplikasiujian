package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Model.SiswaCurang.DataItem;
import com.tioprasetioa.www.mtsn3rohul.R;

import java.util.List;

public class Adapter_SiswaCurang extends RecyclerView.Adapter<Adapter_SiswaCurang.ViewHolder> {

    private List<DataItem>dataItemList;
    private Context context;

    public Adapter_SiswaCurang(List<DataItem> dataItemList, Context context) {
        this.dataItemList = dataItemList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nama, kelas, mapel, guru, waktu, kategori;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.nama_siswacurang);
            kelas = itemView.findViewById(R.id.kelas_siswacurang);
            mapel = itemView.findViewById(R.id.mapel_siswacurang);
            guru = itemView.findViewById(R.id.guru_siswacurang);
            waktu = itemView.findViewById(R.id.waktu_siswacurang);
            kategori = itemView.findViewById(R.id.kategori_siswacurang);
            context = itemView.getContext();
        }
    }
    @NonNull
    @Override
    public Adapter_SiswaCurang.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_siswa_curang,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_SiswaCurang.ViewHolder holder, int position) {
        DataItem dataItem = dataItemList.get(position);
        holder.nama.setText(dataItem.getNama());
        holder.kelas.setText(dataItem.getKelas());
        holder.mapel.setText(dataItem.getMapel());
        holder.guru.setText(dataItem.getGuru());
        holder.waktu.setText(dataItem.getWaktu());
        holder.kategori.setText(dataItem.getKategori());
    }

    @Override
    public int getItemCount() {
        return dataItemList.size();
    }


}