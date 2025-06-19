package com.tioprasetioa.www.mtsn3rohul.Ui.ujian;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tioprasetioa.www.mtsn3rohul.Adapter.Adapter_Home_Ujian;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.MainActivity;
import com.tioprasetioa.www.mtsn3rohul.Utils.BarColor;
import com.tioprasetioa.www.mtsn3rohul.Utils.ExpandableListDataPump;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class BerandaUjian extends AppCompatActivity implements Metode_Interface {
    private SharedPreferences sp;

    private TextView textView, tx_status;
    private Button button_admin, button_sim;
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    ImageView imageView;
    private FloatingActionButton floatingActionButton;
    private RelativeLayout relativeLayout;
    SharedPreferences sharedPreferences, sharedceksim;
    String nama, kelas;
    HashMap<String, List<String>> expandableListDetail;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_beranda_ujian);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BarColor.setBarColor(BerandaUjian.this, R.color.orange,R.color.black);
        FindViewById();
        cekstatus();
        Logic();
        cekLogin();


    }
    @Override
    public void FindViewById() {
        imageView = findViewById(R.id.img_locked);
        textView = findViewById(R.id.NameSiswa);
        tx_status = findViewById(R.id.statusUjian);
        button_admin = findViewById(R.id.btn_admin);
        floatingActionButton = findViewById(R.id.refresh_berandaujian);
        expandableListView = findViewById(R.id.expandableListView);
        relativeLayout = findViewById(R.id.rl_sim);
        button_sim = findViewById(R.id.btn_sim);
        sharedPreferences = getApplicationContext().getSharedPreferences(Utils.KEY_UJIAN, Context.MODE_PRIVATE);
        nama = sharedPreferences.getString("nama2024","");
        kelas = sharedPreferences.getString("kelas2024","");

        if (nama.isEmpty()){
            button_sim.setText("Simulasi Ujian");
        }else {
            button_sim.setText("Cek Jaringan!");
        }
    }

    @Override
    public void Logic() {
//        cek status simulasi

        expandableListView.setVisibility(View.GONE);
        imageView.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        Glide.with(BerandaUjian.this)
                .load(R.drawable.ic_padlock)
                .centerCrop()
                .into(imageView);
        imageView.setVisibility(View.VISIBLE);
        expandableListView.setVisibility(View.GONE);
        tx_status.setText("Terkunci");

        button_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BerandaUjian.this, Super_Admin.class));
            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cekstatus();
                expandleView();
            }
        });
        button_sim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BerandaUjian.this, Simulasi_Ujian.class));
            }
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        startActivity(new Intent(BerandaUjian.this, MainActivity.class));
        finish();
    }

    private void cekstatus() {
        Utils utils = new Utils();
        utils.VolleyString(BerandaUjian.this, "https://mtsn3rokanhulu.sch.id/api/api_statusujian.php", "statusujian", new Utils.VolleyDataString() {
            @Override
            public void onSuccess(String result) {
                String status = result;
                if (!isDestroyed()) { // Cek apakah Activity belum dihancurkan
                    if (status.equals("1")) {
                        Glide.with(BerandaUjian.this)
                                .load(R.drawable.ic_padlock)
                                .centerCrop()
                                .into(imageView);

                        imageView.setVisibility(View.VISIBLE);
                        relativeLayout.setVisibility(View.VISIBLE);
                        expandableListView.setVisibility(View.GONE);
                        tx_status.setText("Terkunci");
                        Toast.makeText(BerandaUjian.this, "Terkunci", Toast.LENGTH_SHORT).show();
                    } else if (status.equals("2")) {
                        if (nama.equals(Utils.KEY_VALUE) && kelas.equals("778")) {
                            tx_status.setText("Terbuka");
                            relativeLayout.setVisibility(View.GONE);
                            imageView.setVisibility(View.GONE);
                            expandableListView.setVisibility(View.VISIBLE);
                        } else {
                            imageView.setVisibility(View.VISIBLE);
                            relativeLayout.setVisibility(View.VISIBLE);
                            expandableListView.setVisibility(View.GONE);
                            tx_status.setText("Terkunci");
                        }
                    } else if (status.isEmpty()) {
                        Utils.tapview(BerandaUjian.this,R.id.refresh_berandaujian,"Ganguan Jaringan!", "Silahkan Klik ini!");
                    } else {
                        tx_status.setText("Terbuka");
                        relativeLayout.setVisibility(View.GONE);
                        imageView.setVisibility(View.GONE);
                        expandableListView.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onError(String errorMessage) {

                // Tampilkan Toast jika terjadi error
                if (!isDestroyed()) {
                    Utils.tapview(BerandaUjian.this, R.id.refresh_berandaujian,"Gangguan Jaringan / Paket Internet Habis", "Silahkan Coba Klik di sini!");
                }
            }
        });

    }

    private void cekLogin() {
        if (nama.equals(Utils.KEY_VALUE) && kelas.equals("778")){
            button_admin.setVisibility(View.VISIBLE);
        }else {
            button_admin.setVisibility(View.GONE);
        }
        if (nama.isEmpty()){
            LayoutInflater inflater = LayoutInflater.from(BerandaUjian.this);
            View popupView = inflater.inflate(R.layout.activity_alertlogin, null);
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(BerandaUjian.this,SweetAlertDialog.WARNING_TYPE);
            sweetAlertDialog.setTitleText("Mohon isi dengan benar! \n Data ini hanya bisa diisi sekali!");
            sweetAlertDialog.setCustomView(popupView);
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Utils.tapview(BerandaUjian.this,R.id.btn_sim,"Simulasi Ujian","Klik ini untuk mulai simulasi!");
                    EditText editText = popupView.findViewById(R.id.et_alerUser);
                    EditText editText2 = popupView.findViewById(R.id.et_alertKelas);
                    String Snama = editText.getText().toString();
                    String userkelas = editText2.getText().toString();

                    int cekdata= userkelas.length();
                    if (Snama.equals("")||userkelas.equals("")){
                        Toast.makeText(BerandaUjian.this, "Isi Untuk Kehadiran Ketika Ujian", Toast.LENGTH_SHORT).show();
                    } else if (cekdata != 3){
                        Toast.makeText(BerandaUjian.this, "Pengisian Kelas Salah\n contoh (7.1)", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences sp = getSharedPreferences(Utils.KEY_UJIAN, Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();//untuk mengedit s
                        editor.putString("nama2024", Snama);
                        editor.putString("kelas2024", userkelas);
                        editor.apply();
                        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
                        String namaSS = sharedPreferences.getString("nama2024","");
                        if (namaSS.equals(Utils.KEY_VALUE)){
                            textView.setText("Super Admin");
                        }
                        sweetAlertDialog.dismissWithAnimation();
                        expandleView();
                        cekstatus();
                    }

                }
            });
            sweetAlertDialog.setCancelable(false);
            sweetAlertDialog.show();
        }else {
            expandleView();
            cekstatus();

            textView.setText(nama);
        }

    }

    private void expandleView() {
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        Collections.sort(expandableListTitle);
        expandableListAdapter = new Adapter_Home_Ujian(this, expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getApplicationContext(),
//                        expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();

            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // Ambil judul grup dan detail child yang diklik
                String group = expandableListTitle.get(groupPosition);
                String child = expandableListDetail.get(group).get(childPosition);
                String dataToPass = "";

                Class<?> targetActivityClass = null;
                if (groupPosition == 0) {
                    // Grup pertama
                    if (childPosition == 0) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "71";
                    } else if (childPosition == 1) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "72";
                    } else if (childPosition == 2) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "73";
                    } else if (childPosition == 3) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "74";
                    } else if (childPosition == 4) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "75";
                    } else if (childPosition == 5) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "76";
                    } else if (childPosition == 6) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "77";
                    } else if (childPosition == 7) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "78";
                    } else if (childPosition==8){
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "79";
                    }else {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "710";
                    }

                }else if (groupPosition==1){
                    if (childPosition == 0) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "81";
                    } else if (childPosition == 1) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "82";
                    } else if (childPosition == 2) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "83";
                    } else if (childPosition == 3) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "84";
                    } else if (childPosition == 4) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "85";
                    } else if (childPosition == 5) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "86";
                    } else if (childPosition == 6) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "87";
                    } else if (childPosition == 7) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "88";
                    } else {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "89";
                    }
                }else if (groupPosition==2){
                    if (childPosition == 0) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "91";
                    } else if (childPosition == 1) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "92";
                    } else if (childPosition == 2) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "93";
                    } else if (childPosition == 3) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "94";
                    } else if (childPosition == 4) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "95";
                    } else if (childPosition == 5) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "96";
                    } else if (childPosition == 6) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "97";
                    } else if (childPosition == 7) {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "98";
                    } else {
                        targetActivityClass = MapelUjian.class;
                        dataToPass = "99";
                    }
                }

                // Buat Intent
                Intent intent = new Intent(getApplicationContext(), targetActivityClass);

                // Tambahkan data ke Intent
                intent.putExtra("group", group);
                intent.putExtra("child", child);
                intent.putExtra("data", dataToPass);

                // Mulai aktivitas dengan Intent yang telah dibuat
                startActivity(intent);


                // Kembalikan true karena klik telah ditangani
                return true;
            }
        });
    }


}