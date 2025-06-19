package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Youtube;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Server.IPServer;

import java.util.List;

public class Adapter_Youtube extends RecyclerView.Adapter<Adapter_Youtube.ViewHolder> {

    private Context context;
    private final List<Model_Youtube>list;

    public Adapter_Youtube(Context context, List<Model_Youtube> list) {
        this.context = context;
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView judul;
        private final TextView tanggal;
        private final ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.tv_judulYT);
            tanggal = itemView.findViewById(R.id.tv_tglYT);
            imageView = itemView.findViewById(R.id.iv_youtube);
            context = itemView.getContext();

        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_youtube, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_Youtube model_youtube = list.get(position);
        holder.judul.setText(model_youtube.getJudul());
        holder.tanggal.setText(model_youtube.getTanggal());
        Glide.with(holder.itemView.getContext())
                .load(IPServer.youtube()+model_youtube.getGambar())
                .into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id =model_youtube.getUrl();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+id));
                Intent intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+id));
                try {
                    context.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    context.startActivity(intent1);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}