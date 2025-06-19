package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Galeri_Home;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.WebViewBerita;

import java.util.List;

public class Adapter_Galeri_Home extends RecyclerView.Adapter<Adapter_Galeri_Home.ViewHolder> {

    private final Context context;
    private final List<Model_data_Galeri_Home>galeriHomeslist;

    public Adapter_Galeri_Home(Context context, List<Model_data_Galeri_Home> galeriHomeslist) {
        this.context = context;
        this.galeriHomeslist = galeriHomeslist;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView judul;
        private final TextView id;
        private final TextView kategori;
        private final Context context;
        public ViewHolder( View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_galeri);
            judul = itemView.findViewById(R.id.judul_galeri);
            id = itemView.findViewById(R.id.id_galeri);
            kategori = itemView.findViewById(R.id.kategori_galeri);
            context = itemView.getContext();
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_galeri_home,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder( Adapter_Galeri_Home.ViewHolder holder, int position) {
        Model_data_Galeri_Home model_data_galeri_home = galeriHomeslist.get(position);
        holder.judul.setText(model_data_galeri_home.getJudul());
        holder.id.setText(String.valueOf(model_data_galeri_home.getId()));
        holder.kategori.setText(model_data_galeri_home.getKategori());
        Glide.with(context)
                .load(model_data_galeri_home.getImage())
                .fitCenter()
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model_data_Galeri_Home model_data_galeri_home1 = new Model_data_Galeri_Home();
                Intent intent = new Intent(context, WebViewBerita.class);
                String text = "1";
                model_data_galeri_home1.setUrl(model_data_galeri_home.getUrl());
                intent.putExtra(WebViewBerita.KEY_Web,model_data_galeri_home1);
                intent.putExtra(WebViewBerita.KEY_key,text);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return galeriHomeslist.size();
    }

}