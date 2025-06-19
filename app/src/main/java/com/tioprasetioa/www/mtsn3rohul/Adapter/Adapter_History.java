package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_TampilData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Adapter_History extends RecyclerView.Adapter<Adapter_History.ViewHolder> {

    private final List<ModelData_TampilData>modelDataTampilDataList;

    public Adapter_History(List<ModelData_TampilData> modelDataTampilDataList) {
        this.modelDataTampilDataList = modelDataTampilDataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txnamabuku;
        private final TextView txdatapinjam;
        private final TextView txkembalibuku;
        private final TextView TGLbukudikembalikan;
        private final TextView txreturn;
        private final TextView denda;
        private final TextView waktusekarang;
        private final TextView txdenda;
        private final Context context;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txnamabuku = itemView.findViewById(R.id.etnamabuku);
            txdatapinjam = itemView.findViewById(R.id.etdatapinjam);
            txkembalibuku = itemView.findViewById(R.id.etdatakembali);
            txreturn = itemView.findViewById(R.id.isreturn);
            denda = itemView.findViewById(R.id.Denda);
            txdenda = itemView.findViewById(R.id.txdenda);
            waktusekarang = itemView.findViewById(R.id.Waktuskrg);
            TGLbukudikembalikan = itemView.findViewById(R.id.ettglbukudikembalikan);
            context = itemView.getContext();

        }
    }
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_tampi_data, null);
        return new ViewHolder(view);
    }
    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ModelData_TampilData modelData_tampilData = modelDataTampilDataList.get(position);
        holder.txnamabuku.setText(modelData_tampilData.getItemCode());
        holder.txdatapinjam.setText(modelData_tampilData.getLoanDate());
        holder.txkembalibuku.setText(modelData_tampilData.getDueDate());
        holder.txreturn.setText(String.valueOf(modelData_tampilData.getIsReturn()));
        holder.TGLbukudikembalikan.setText(String.valueOf(modelData_tampilData.getReturnDate()));
        holder.waktusekarang.setVisibility(View.GONE);
        holder.denda.setVisibility(View.GONE);
        holder.txkembalibuku.setVisibility(View.GONE);
        holder.txdenda.setVisibility(View.GONE);
        holder.TGLbukudikembalikan.setVisibility(View.VISIBLE);
        String kembalibuku = holder.TGLbukudikembalikan.getText().toString();
        if (kembalibuku.equals("null")){
            holder.TGLbukudikembalikan.setText("Belum \nDikembalikan");
            holder.TGLbukudikembalikan.setTextSize(12);
            holder.TGLbukudikembalikan.setTextColor(R.color.Red);
        }


    }

        @Override
        public int getItemCount () {
            return modelDataTampilDataList.size();
        }
    }
