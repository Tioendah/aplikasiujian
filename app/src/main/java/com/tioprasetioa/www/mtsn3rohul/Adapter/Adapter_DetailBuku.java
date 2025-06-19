package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_DetailBuku;

import java.util.List;

public class Adapter_DetailBuku extends RecyclerView.Adapter<Adapter_DetailBuku.ViewHolder> {
    private final List<Model_DetailBuku> model_detailBukus;
    private Context context;

    public Adapter_DetailBuku(List<Model_DetailBuku> model_detailBukus) {
        this.model_detailBukus = model_detailBukus;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvlanguage;
        private final TextView tvedition;
        private final TextView tvisbn;
        private final TextView tvpublisheryear;
        private final TextView tvcollation;
        private final TextView tvnotes;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvedition = itemView.findViewById(R.id.txedition);
            tvisbn = itemView.findViewById(R.id.txisbn);
            tvpublisheryear = itemView.findViewById(R.id.txpublishYear);
            tvcollation = itemView.findViewById(R.id.txcollation);
            tvlanguage = itemView.findViewById(R.id.txlanguage);
            tvnotes = itemView.findViewById(R.id.txnotes);
            context = itemView.getContext();
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_detail_buku,null);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model_DetailBuku model_detailBuku = model_detailBukus.get(position);
        holder.tvedition.setText(model_detailBuku.getEdition());
        holder.tvisbn.setText(model_detailBuku.getIsbn_issn());
        holder.tvpublisheryear.setText(model_detailBuku.getPublish_year());
        holder.tvcollation.setText(model_detailBuku.getCollation());
        holder.tvnotes.setText(model_detailBuku.getNotes());
        holder.tvlanguage.setText(model_detailBuku.getLanguange_id());

        String string = holder.tvlanguage.getText().toString();

        if (string.equals("id")){
            holder.tvlanguage.setText("Bahasa Indonesia");
        }else {
            holder.tvlanguage.setText("Bahasa Inggris");
        }
    }

    @Override
    public int getItemCount() {
        return model_detailBukus.size();
    }


}