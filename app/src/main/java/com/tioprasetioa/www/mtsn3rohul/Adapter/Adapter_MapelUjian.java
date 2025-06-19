package com.tioprasetioa.www.mtsn3rohul.Adapter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Model_MapelUjian;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.ujian.Soal_Ujian;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Adapter_MapelUjian extends RecyclerView.Adapter<Adapter_MapelUjian.ViewHolder> {

    private final List<Model_MapelUjian>data;
    private Context context;

    public Adapter_MapelUjian(List<Model_MapelUjian> data, Context context) {
        this.data = data;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView guru;
        private final TextView mapel;
        private final CardView cardView;
        private final ImageView imgstatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            guru = itemView.findViewById(R.id.nGuru);
            mapel = itemView.findViewById(R.id.nMapel);
            imgstatus = itemView.findViewById(R.id.img_status);
            cardView = itemView.findViewById(R.id.cardmapel);
            context = itemView.getContext();
        }
    }
    @NonNull
    @Override
    public Adapter_MapelUjian.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_adapter_mapel_ujian,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_MapelUjian.ViewHolder holder, int position) {
        Model_MapelUjian modelMapelUjian = data.get(position);
        SharedPreferences sharedPreferences = context.getSharedPreferences(Utils.KEY_UJIAN,Context.MODE_PRIVATE);
        String getAdmin = sharedPreferences.getString("nama2024","");

        int status = modelMapelUjian.getStatus();
        if (status==3){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            holder.imgstatus.setVisibility(View.VISIBLE);
        }else if (status==2){
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.white));
            holder.imgstatus.setVisibility(View.GONE);
        }else {
            holder.imgstatus.setVisibility(View.GONE);
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.orange));
        }
        holder.guru.setText(modelMapelUjian.getGuru());
        holder.mapel.setText(modelMapelUjian.getMapel());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (getAdmin.equals(Utils.KEY_VALUE)){
                    if (status==1){
                        Toast.makeText(context, "Ujian Belum Dimulai", Toast.LENGTH_SHORT).show();
                    } else if (status==3) {
                        Toast.makeText(context, "Ujian Sudah Dilaksanakan", Toast.LENGTH_SHORT).show();
                    } else {
                        LayoutInflater inflater = LayoutInflater.from(context);
                        View popupView = inflater.inflate(R.layout.popup_admin, null);
                        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                        sweetAlertDialog.setTitleText("Pin Ujian");

                        int pinmasuk = modelMapelUjian.getPinIn();
                        int pinout = modelMapelUjian.getPinOut();
                        int pinexit = modelMapelUjian.getPin_exit();
                        ImageView imgmasuk = popupView.findViewById(R.id.iv_masuk);
                        ImageView imgout = popupView.findViewById(R.id.iv_out);
                        ImageView imgexit = popupView.findViewById(R.id.iv_exit);
                        TextView tv_masuk = popupView.findViewById(R.id.cp_pinmasuk);
                        TextView tv_out = popupView.findViewById(R.id.cp_out);
                        TextView tv_exit = popupView.findViewById(R.id.cp_pinexit);
                        tv_masuk.setText("Pin Masuk "+pinmasuk);
                        tv_out.setText("Pin Pergantian "+pinout);
                        tv_exit.setText("Pin Pulang "+pinexit);
                        imgmasuk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                copypaste(pinmasuk);
                                Toast.makeText(context, "Pin Masuk Berhasil Disalin", Toast.LENGTH_SHORT).show();
                            }
                        });

                        imgout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                copypaste(pinout);
                                Toast.makeText(context, "Pin Pergantian Berhasil Disalin", Toast.LENGTH_SHORT).show();
                            }
                        });

                        imgexit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                copypaste(pinexit);
                                Toast.makeText(context, "Pin Pulang Berhasil Disalin", Toast.LENGTH_SHORT).show();
                            }
                        });
                        sweetAlertDialog.setCustomView(popupView);
                        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                Model_MapelUjian modelMapelUjian2= new Model_MapelUjian();
                                modelMapelUjian2.setGuru(modelMapelUjian.getGuru());
                                modelMapelUjian2.setMapel(modelMapelUjian.getMapel());
                                modelMapelUjian2.setLink(modelMapelUjian.getLink());
                                modelMapelUjian2.setPinIn(modelMapelUjian.getPinIn());
                                modelMapelUjian2.setPinOut(modelMapelUjian.getPinOut());
                                modelMapelUjian2.setPin_exit(modelMapelUjian.getPin_exit());
                                Intent intent= new Intent(context.getApplicationContext(), Soal_Ujian.class);
                                intent.putExtra(Soal_Ujian.KEY_MAPEL,modelMapelUjian2);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                context.startActivity(intent);
                                sweetAlertDialog.dismissWithAnimation();

                            }
                        });
                        sweetAlertDialog.setCancelable(true);
                        sweetAlertDialog.show();
                    }
                }else {
                    if (status==1){
                        Toast.makeText(context, "Ujian Belum Dimulai", Toast.LENGTH_SHORT).show();
                    } else if (status==3) {
                        Toast.makeText(context, "Ujian Sudah Dilaksanakan", Toast.LENGTH_SHORT).show();
                    } else {
                            SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("Pastikan Mapel Sudah Sesuai!");
                            pDialog.setContentText("Apakah sudah benar mapel ujian "+ modelMapelUjian.getMapel()+" ?");
                            pDialog.setConfirmText("Ya");
                            pDialog.setCancelText("Tidak");
                            pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    pDialog.cancel();
                                }
                            });
                            pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    Model_MapelUjian modelMapelUjian1= new Model_MapelUjian();
                                    modelMapelUjian1.setGuru(modelMapelUjian.getGuru());
                                    modelMapelUjian1.setMapel(modelMapelUjian.getMapel());
                                    modelMapelUjian1.setLink(modelMapelUjian.getLink());
                                    modelMapelUjian1.setPinIn(modelMapelUjian.getPinIn());
                                    modelMapelUjian1.setPinOut(modelMapelUjian.getPinOut());
                                    modelMapelUjian1.setPin_exit(modelMapelUjian.getPin_exit());
                                    Intent intent= new Intent(context.getApplicationContext(), Soal_Ujian.class);
                                    intent.putExtra(Soal_Ujian.KEY_MAPEL,modelMapelUjian1);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    context.startActivity(intent);
                                    pDialog.dismissWithAnimation();
                                }
                            });
                            pDialog.show();
                        }

                    }
                }

            private void copypaste(int pin) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Copied Text", String.valueOf(pin));
                clipboard.setPrimaryClip(clip);
            }

        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


}