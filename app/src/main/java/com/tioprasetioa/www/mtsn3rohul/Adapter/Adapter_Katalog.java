package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Katalog;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Server.IPServer;
import com.tioprasetioa.www.mtsn3rohul.Ui.Detail_Buku;

import java.util.List;

public class Adapter_Katalog extends RecyclerView.Adapter<Adapter_Katalog.ViewHolder> {

    private final List<Model_Katalog> modelKatalogArrayList;
    private final Context context;
    private final IPServer ipServer = new IPServer();
    public Adapter_Katalog(List<Model_Katalog> modelKatalogArrayList, Context context) {
        this.modelKatalogArrayList = modelKatalogArrayList;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView judul;
        private final TextView pengarang;
        private final TextView penerbit;
        private final Button button;
        private final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_katalog);
            id = itemView.findViewById(R.id.id_katalog2);
            judul = itemView.findViewById(R.id.tv_judul);
            penerbit = itemView.findViewById(R.id.tv_penerbit);
            pengarang = itemView.findViewById(R.id.tv_pengarang);
            button = itemView.findViewById(R.id.btn_katalog);


        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_katalog, null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Model_Katalog model_katalog = modelKatalogArrayList.get(position);
        holder.id.setText(String.valueOf(model_katalog.getBiblio_id()));
        holder.judul.setText(model_katalog.getTitle());
        holder.pengarang.setText(model_katalog.getAuthor());
        holder.penerbit.setText(model_katalog.getPublisher());
        Glide.with(holder.itemView.getContext())
                .load(ipServer.slims+model_katalog.getImage())
                .into(holder.imageView);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                model_katalog.setBiblio_id(model_katalog.getBiblio_id());
                model_katalog.setTitle(model_katalog.getTitle());
                model_katalog.setAuthor(model_katalog.getAuthor());
                model_katalog.setPublisher(model_katalog.getPublisher());
                model_katalog.setImage(model_katalog.getImage());
                String o = "2";
                Intent i = new Intent(context.getApplicationContext(), Detail_Buku.class);
                i.putExtra(Detail_Buku.KEY_Detail,model_katalog);
                i.putExtra(Detail_Buku.KEY_Key,o);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelKatalogArrayList.size();
    }


}