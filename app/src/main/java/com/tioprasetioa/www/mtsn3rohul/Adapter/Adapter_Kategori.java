package com.tioprasetioa.www.mtsn3rohul.Adapter;

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

import com.tioprasetioa.www.mtsn3rohul.Model.Model_Data_Kategori;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Server.IPServer;
import com.tioprasetioa.www.mtsn3rohul.Ui.Detail_Buku;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.List;

public class Adapter_Kategori extends RecyclerView.Adapter<Adapter_Kategori.ViewHolder> {

    private Context context;
    private final List<Model_Data_Kategori>list;

    public Adapter_Kategori(List<Model_Data_Kategori> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final TextView tvjudul;
        private final TextView id;
        private final TextView pengarang;
        private final TextView penerbit;
        private final Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_katalog400);
            tvjudul = itemView.findViewById(R.id.tv_judul400);
            id = itemView.findViewById(R.id.id_katalog400);
            pengarang = itemView.findViewById(R.id.tv_pengarang400);
            penerbit = itemView.findViewById(R.id.tv_penerbit400);
            button = itemView.findViewById(R.id.btn_katalog400);
            context = itemView.getContext();
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_kategori,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_Data_Kategori model_data_kategori = list.get(position);
        holder.tvjudul.setText(model_data_kategori.getTitle());
        holder.pengarang.setText(model_data_kategori.getAuthor());
        holder.penerbit.setText(model_data_kategori.getPublisher());
        holder.id.setText(String.valueOf(model_data_kategori.getBiblio_id()));
        IPServer ipServer = new IPServer();
        Utils.Glide(context,ipServer.slims+model_data_kategori.getImage(),holder.imageView);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model_Data_Kategori model_data_kategori1 = new Model_Data_Kategori();
                model_data_kategori1.setBiblio_id(model_data_kategori.getBiblio_id());
                model_data_kategori1.setTitle(model_data_kategori.getTitle());
                model_data_kategori1.setAuthor(model_data_kategori.getAuthor());
                String i = "1";
                model_data_kategori1.setPublisher(model_data_kategori.getPublisher());
                model_data_kategori1.setImage(model_data_kategori.getImage());
                Intent intent = new Intent(context.getApplicationContext(), Detail_Buku.class);
                intent.putExtra(Detail_Buku.KEY_Detail,model_data_kategori1);
                intent.putExtra(Detail_Buku.KEY_Key,i);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}