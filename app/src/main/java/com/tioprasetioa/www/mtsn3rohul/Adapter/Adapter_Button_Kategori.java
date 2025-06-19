package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Model.Data.Parcel_Btn_Kategori;
import com.tioprasetioa.www.mtsn3rohul.R;

import java.util.ArrayList;

public class Adapter_Button_Kategori extends RecyclerView.Adapter<Adapter_Button_Kategori.ViewHolder> {

    private Itemclicklistener monItemClickListener;
    private final ArrayList<Parcel_Btn_Kategori>list;

    private final int lasytposisi= -1;
    int row_index = -1;
    public Adapter_Button_Kategori(ArrayList<Parcel_Btn_Kategori> list) {
        this.list = list;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView btn_kategori;
        private final Context context;
        private final RelativeLayout relativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            relativeLayout = itemView.findViewById(R.id.relativeLayout_btnkategori);
            btn_kategori = itemView.findViewById(R.id.btn_kategori);
            btn_kategori.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            if (monItemClickListener != null)monItemClickListener.onItemClick(v,getAdapterPosition());
        }
    }

    public void seClickListener(Itemclicklistener listener){
        monItemClickListener = listener;
    }
    public interface Itemclicklistener{
        void onItemClick(View view, int pos);
        void onItemClick(int pos);
    }
    @NonNull
    @Override
    public Adapter_Button_Kategori.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_button_kategori,null);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onBindViewHolder(@NonNull Adapter_Button_Kategori.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Parcel_Btn_Kategori parcel_btn_kategori= list.get(position);
        holder.btn_kategori.setText(parcel_btn_kategori.getKategori());

        holder.btn_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
                if (monItemClickListener != null)monItemClickListener.onItemClick(position);
            }
        });
        if (row_index==position){
            holder.relativeLayout.setBackgroundResource(R.color.kuning);
        }else {
            holder.relativeLayout.setBackgroundResource(R.color.green);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}