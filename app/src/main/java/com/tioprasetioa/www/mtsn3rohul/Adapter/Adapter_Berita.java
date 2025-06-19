package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Berita;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.WebViewBerita;
import com.tioprasetioa.www.mtsn3rohul.Ui.admin.Admin_Berita;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.List;

public class Adapter_Berita extends RecyclerView.Adapter<Adapter_Berita.Viewholder> {

    private Context context;
    private final List<Model_data_Berita>modelDataBeritaList;

    public Adapter_Berita(Context context, List<Model_data_Berita> modelDataBeritaList) {
        this.context = context;
        this.modelDataBeritaList = modelDataBeritaList;
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private final ImageView id_imageView;
        private final TextView id_kategori;
        private final TextView id_berita;
        private final TextView id_judulberita;
        private final TextView id_hari;
        private final TextView id_view;
        private final Button btn;

        public Viewholder( View itemView) {
            super(itemView);
            btn = itemView.findViewById(R.id.btn_addlink);
            id_imageView = itemView.findViewById(R.id.image_berita);
            id_kategori = itemView.findViewById(R.id.kategori);
            id_judulberita = itemView.findViewById(R.id.judul_berita);
            id_hari = itemView.findViewById(R.id.hari_berita);
            id_berita = itemView.findViewById(R.id.id_berita);
            id_view = itemView.findViewById(R.id.view);
            context = itemView.getContext();
        }
    }
    @Override
    public Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_berita,null);
        return new Viewholder(view);
    }
    @Override
    public void onBindViewHolder(Adapter_Berita.Viewholder holder, int position) {
        Model_data_Berita model_data_berita = modelDataBeritaList.get(position);
        holder.id_berita.setText(String.valueOf(model_data_berita.getTulisan_id()));
        holder.id_kategori.setText(model_data_berita.getTulisan_kategori_nama());
        holder.id_judulberita.setText(model_data_berita.getTulisan_judul());
        holder.id_hari.setText(model_data_berita.getTulisan_tanggal());
        holder.id_view.setText(model_data_berita.getTulisan_views());
        SharedPreferences sp = context.getSharedPreferences(Utils.KEY_UJIAN, Context.MODE_PRIVATE);
        String nama = sp.getString("nama2024","");
        String kelas = sp.getString("kelas2024","");
        if (nama.equals(Utils.KEY_VALUE) && kelas.equals("778")){
            holder.btn.setVisibility(View.VISIBLE);
        }else {
            holder.btn.setVisibility(View.GONE);
        }
        Glide.with(context)
                .load("https://www.mtsn3rokanhulu.sch.id/assets/images/"+model_data_berita.getTulisan_gambar())
                .fitCenter()
                .into(holder.id_imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model_data_Berita model_data_berita1 = new Model_data_Berita();
                model_data_berita1.setUrl(model_data_berita.getUrl());
                String text = "3";
                Intent intent= new Intent(context, WebViewBerita.class);
                intent.putExtra(WebViewBerita.KEY_Web,model_data_berita1);
                intent.putExtra(WebViewBerita.KEY_key,text);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Model_data_Berita modelDataBerita2 = new Model_data_Berita();
                modelDataBerita2.setTulisan_judul(model_data_berita.getTulisan_judul());
                modelDataBerita2.setTulisan_id(model_data_berita.getTulisan_id());
                Intent intent = new Intent(context, Admin_Berita.class);
                intent.putExtra(Admin_Berita.KEY_Admin,modelDataBerita2);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return modelDataBeritaList.size();
    }


}