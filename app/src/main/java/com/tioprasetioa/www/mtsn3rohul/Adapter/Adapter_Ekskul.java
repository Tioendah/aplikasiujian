package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tioprasetioa.www.mtsn3rohul.Model.ekskul.Item;
import com.tioprasetioa.www.mtsn3rohul.R;

import java.util.List;

public class Adapter_Ekskul extends BaseAdapter {

    private final List<Item> list;
    private final Context context;

    public Adapter_Ekskul(Context context, List<Item> list) {
        this.context = context;
        this.list = list;
    }

    public void setData(List<Item> data) {
        list.clear();
        if (data != null) {
            list.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView Nama;
        TextView judul;
        TextView deskripsi;
        TextView jadwal;
        ImageView imageView;
        ImageView imageViewpembina;

        ViewHolder(View v) {
            Nama = v.findViewById(R.id.tv_ekskul);
            judul = v.findViewById(R.id.Judul);
            deskripsi = v.findViewById(R.id.Desc_ekskul);
            jadwal = v.findViewById(R.id.Jadwal_ekskul);
            imageView = v.findViewById(R.id.image_ekskul);
            imageViewpembina = v.findViewById(R.id.img_pembina_ekskul);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.activity_adapter_ekskul, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Set data into views
        Item model = list.get(position);
        viewHolder.Nama.setText(model.getCouch());
        viewHolder.judul.setText(model.getTitle());
        viewHolder.deskripsi.setText(model.getDesc());
        viewHolder.jadwal.setText(model.getTime());

        // Load images using Glide library
        Glide.with(context)
                .load(model.getImage())
                .into(viewHolder.imageView);

        Glide.with(context)
                .load(model.getImage())
                .into(viewHolder.imageViewpembina);

        return convertView;
    }
}
