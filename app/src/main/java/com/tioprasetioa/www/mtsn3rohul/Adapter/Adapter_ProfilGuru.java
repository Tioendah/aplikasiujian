package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_ProfilGuru;
import com.tioprasetioa.www.mtsn3rohul.Server.IPServer;

import java.util.List;

public class Adapter_ProfilGuru extends RecyclerView.Adapter<Adapter_ProfilGuru.ViewHolder> {

    private final List<ModelData_ProfilGuru>list;
    private Context context;

    public Adapter_ProfilGuru(List<ModelData_ProfilGuru> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView nama;
        private final TextView ttl;
        private final TextView pdk;
        private final TextView jbt;
        private final TextView jbtpdk;
        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_profilgr);
            nama = itemView.findViewById(R.id.id_namagr);
            ttl = itemView.findViewById(R.id.id_TTLgr);
            pdk = itemView.findViewById(R.id.id_pendidikan);
            jbt = itemView.findViewById(R.id.id_Jabatan);
            jbtpdk = itemView.findViewById(R.id.id_JbtTambahan);
            imageView = itemView.findViewById(R.id.id_imagegr);
            context = itemView.getContext();
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_profil_guru,null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelData_ProfilGuru modelData_profilGuru = list.get(position);
        holder.id.setText(String.valueOf(modelData_profilGuru.getId()));
        holder.nama.setText(modelData_profilGuru.getNama());
        holder.pdk.setText(modelData_profilGuru.getPendidikan());
        holder.jbt.setText(modelData_profilGuru.getJabatan());

        holder.jbtpdk.setText(modelData_profilGuru.getJbtTambahan());
        String bold = holder.jbtpdk.getText().toString();
        if (bold.equals("Waka Kurikulum")){
            holder.jbtpdk.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        }else if (bold.equals("Waka Sapras")){
            holder.jbtpdk.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        }else if (bold.equals("Waka Humas")){
            holder.jbtpdk.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        }else if (bold.equals("Waka Kesiswaan")){
            holder.jbtpdk.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        }else {
            holder.jbtpdk.setTypeface(null,Typeface.NORMAL);
        }
        Glide.with(context)
                .load(IPServer.imageprofilguru()+modelData_profilGuru.getImage())
                .fitCenter()
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}