package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Model.ModelData_TampilData;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Services.Notif_Servis;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Adapter_TampiData extends RecyclerView.Adapter<Adapter_TampiData.ViewHolder> {

    private final List<ModelData_TampilData>modelDataTampilDataList;
    private final Calendar calSekarang = Calendar.getInstance();
    private final Calendar calBatas = Calendar.getInstance();
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private Context context;
    public Adapter_TampiData(List<ModelData_TampilData> modelDataTampilDataList) {
        this.modelDataTampilDataList = modelDataTampilDataList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txnamabuku;
        private final TextView wktuskrg;
        private final TextView txdatapinjam;
        private final TextView txkembalibuku;
        private final TextView txreturn;
        private final TextView denda;
        private TextView totaldenda;
        private final TextView txdenda;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            txdenda = itemView.findViewById(R.id.txdenda);
            wktuskrg = itemView.findViewById(R.id.Waktuskrg);
            txnamabuku = itemView.findViewById(R.id.etnamabuku);
            txdatapinjam = itemView.findViewById(R.id.etdatapinjam);
            txkembalibuku = itemView.findViewById(R.id.etdatakembali);
            txreturn = itemView.findViewById(R.id.isreturn);//menentukan 0/1 buku telah dikembalikan atau belum
            denda = itemView.findViewById(R.id.Denda);
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
        String string = holder.txreturn.getText().toString();

        String batas= holder.txkembalibuku.getText().toString();
        String todaystring = simpleDateFormat.format(calSekarang.getTime());
        holder.wktuskrg.setText(todaystring);


        if (string.equals("1")){
            holder.txnamabuku.setVisibility(View.GONE);
            holder.txdatapinjam.setVisibility(View.GONE);
            holder.txkembalibuku.setVisibility(View.GONE);
            holder.denda.setVisibility(View.GONE);
            holder.txdenda.setVisibility(View.GONE);
            holder.itemView.getContext().stopService(new Intent(holder.itemView.getContext(), Notif_Servis.class));
        }else {
            holder.txnamabuku.setVisibility(View.VISIBLE);
            holder.txdatapinjam.setVisibility(View.VISIBLE);
            holder.txkembalibuku.setVisibility(View.VISIBLE);
            holder.denda.setVisibility(View.VISIBLE);
            holder.txdenda.setVisibility(View.VISIBLE);
            if (todaystring.compareTo(batas)>0){
                holder.itemView.getContext().startService(new Intent(holder.itemView.getContext(), Notif_Servis.class));
                try {
                    calBatas.setTime(simpleDateFormat.parse(modelData_tampilData.getDueDate()));
                    int selisihbulan = (int) daysbetween(calBatas,calSekarang);
                    int denda = selisihbulan*2000;
                    holder.denda.setText("Rp. "+denda);

                }catch (ParseException e){
                    e.getMessage();
                }
            }
        }

    }

    private static long daysbetween(Calendar tanggalawal, Calendar tanggalakhir) {
        long lama = 0;
        Calendar tanggal = (Calendar) tanggalawal.clone();
        while (tanggal.before(tanggalakhir)){
            tanggal.add(Calendar.DAY_OF_MONTH, 1);
            lama++;
        }return lama;
    }

    @Override
    public int getItemCount() {
        return modelDataTampilDataList.size();
    }
    
}