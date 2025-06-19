package com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen;


import static com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.DbAbsen.KolomDb.KEY_Namatabel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_Data_RekapAbsen;
import com.tioprasetioa.www.mtsn3rohul.R;

import java.util.List;

public class Adapter_RekapAbsen extends RecyclerView.Adapter<Adapter_RekapAbsen.ViewHolder> {

    private List<Model_Data_RekapAbsen>list;
    private Context context;

    public Adapter_RekapAbsen(){

    }
    public Adapter_RekapAbsen(List<Model_Data_RekapAbsen> list) {
        this.list = list;
    }
    public void  setListData(List<Model_Data_RekapAbsen>model_data_rekapAbsens){
        this.list = model_data_rekapAbsens;

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView masuk;
        private final TextView pulang;
        private final TextView deletall;
        private final TextView hari;
        public TextView nomor;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nomor = itemView.findViewById(R.id.no);
            deletall = itemView.findViewById(R.id.id_vdeletall);
            id = itemView.findViewById(R.id.id_rekap);
            hari = itemView.findViewById(R.id.harirekap);
            masuk = itemView.findViewById(R.id.id_masuk);
            pulang = itemView.findViewById(R.id.id_pulang);
            context = itemView.getContext();
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_rekap_absen,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        final Model_Data_RekapAbsen model_data_rekapAbsen = list.get(position);
        holder.nomor.setText(String.valueOf(position+1));
        holder.id.setText(model_data_rekapAbsen.getId());
        holder.masuk.setText(model_data_rekapAbsen.getMasuk());
        holder.pulang.setText(model_data_rekapAbsen.getPulang());
        holder.hari.setText(model_data_rekapAbsen.getHari());
        holder.deletall.setText(model_data_rekapAbsen.getDeleteAll());
        String Pulang = holder.pulang.getText().toString();

        if (Pulang.equals("EDIT!")){
            holder.pulang.setTextColor(Color.RED);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            //inten untuk mengirim data ke update
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.inflate(R.menu.database);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.hapus:

                                AlertDialog.Builder alertDialogBuilder2 = new AlertDialog.Builder(context);
                                alertDialogBuilder2.setTitle("Yakin ingin menghapus data?");
//                                alertDialogBuilder.setMessage(string);
                                alertDialogBuilder2.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        DbAbsen dbAbsen = new DbAbsen(context.getApplicationContext());
                                        SQLiteDatabase sqLiteDatabase = dbAbsen.getWritableDatabase();
                                        String pilih = DbAbsen.KolomDb.KEY_IdTgl + " Like ?";
                                        String[] stringsArray = {model_data_rekapAbsen.getId()};
                                        sqLiteDatabase.delete(KEY_Namatabel,pilih,stringsArray);

                                        String pilihposisi = String.valueOf(model_data_rekapAbsen.getId().indexOf(model_data_rekapAbsen.getId()));
                                        list.remove(position);
                                        notifyItemChanged(position);
                                        if (pilihposisi==null){
                                            notifyItemRangeChanged(Integer.parseInt(String.valueOf(position)),list.size());
                                        }
                                    }
                                });
                                alertDialogBuilder2.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialogBuilder2.show();
                                break;
                            case R.id.hapussemua:
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                                alertDialogBuilder.setTitle("Yakin ingin menghapus data?");
//                                alertDialogBuilder.setMessage(string);
                                alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try{
                                            DbAbsen dbAbsen1 = new DbAbsen(context.getApplicationContext());
                                            SQLiteDatabase sqLiteDatabase1 = dbAbsen1.getWritableDatabase();

                                            String pilihpos = DbAbsen.KolomDb.KEY_DeletAll + " LIKE ?";
                                            String [] array = {model_data_rekapAbsen.getDeleteAll()};

                                            sqLiteDatabase1.delete(KEY_Namatabel,pilihpos,array);
                                            list.removeAll(list);
                                            notifyDataSetChanged();
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }finally {
                                            Toast.makeText(context,"Berhasil Menghapus Semua Data ",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                alertDialogBuilder.show();
                                break;
                            case R.id.update:
                                model_data_rekapAbsen.setId(model_data_rekapAbsen.getId());
                                Intent i = new Intent(context, Update.class);
                                i.putExtra(Update.KEY_UPDATE,model_data_rekapAbsen);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(i);
                                ((Activity)context).finish();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }


}