package com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.Actiivity_Identitas;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Rekap_Absen extends AppCompatActivity implements Metode_Interface, View.OnClickListener {
    private EditText exidtgl,exmasuk, exvdeletall,expulang;
    private Button btn_send, btn_lihatdta;
    private DbAbsen dbAbsen;
    private String Exmasuk, Shari, IdTgl, ExVdeletAll, ExPulang;
    private Spinner vhari;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar mycalender;
    private ImageView imageView;
    private TextView blmlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap_absen);

        FindViewById();
        Logic();
        spninner();
    }


    @Override
    public void FindViewById() {
        exidtgl = findViewById(R.id.id_tgl);
        exmasuk = findViewById(R.id.etMasuk);
        btn_send = findViewById(R.id.btn_sendDt);
        btn_lihatdta = findViewById(R.id.btn_rekap);
        exvdeletall = findViewById(R.id.valueDeleteAll);
        vhari = findViewById(R.id.hari);
        expulang = findViewById(R.id.etPulang);
        imageView = findViewById(R.id.profildb);
        blmlogin = findViewById(R.id.id_db);
    }
    @Override
    public void Logic() {
        dbAbsen = new DbAbsen(getBaseContext());
        btn_send.setOnClickListener(this);
        btn_lihatdta.setOnClickListener(this);
        exidtgl.setOnClickListener(this);
        exmasuk.setOnClickListener(this);
        imageView.setOnClickListener(this);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Myuser", Context.MODE_PRIVATE);
        String Nama = sp.getString("nama","");
        blmlogin.setText(Nama);
        expulang.setText("EDIT!");
        mycalender = Calendar.getInstance();
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mycalender.set(Calendar.YEAR,year);
                mycalender.set(Calendar.MONTH,month);
                mycalender.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                formatkalender();
            }
        };
        //SPINNER
        spninner();
    }
    private void spninner() {
        String [] hari = new String[]{
                "Pilih Hari",
                "Senin",
                "Selasa",
                "Rabu",
                "Kamis",
                "Jum'at",
                "Sabtu"
        };
        final List<String> listHari = new ArrayList<>(Arrays.asList(hari));
        final ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this, R.layout.adapter_spinner,listHari){
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position,convertView,parent);
                TextView textView = (TextView) view;
                if (position == 0){
                    textView.setTextColor(Color.GRAY);
                }else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        stringArrayAdapter.setDropDownViewResource(R.layout.adapter_spinner);
        vhari.setAdapter(stringArrayAdapter);
        vhari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pilih = (String)parent.getItemAtPosition(position);
                if (position>0){
                    Toast.makeText(Rekap_Absen.this,"Hari : "+pilih,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_sendDt:
                Utils.Toast(Rekap_Absen.this,"Absen Tersimpan");
                StringGetData();
                SendData();
                clearData();
                break;
            case R.id.btn_rekap:
                Intent intent = new Intent(Rekap_Absen.this,View_Rekap_Absen.class);
                startActivity(intent);
                break;
            case R.id.id_tgl:
                new DatePickerDialog(Rekap_Absen.this, dateSetListener, mycalender.get(Calendar.YEAR),mycalender.get(Calendar.MONTH),mycalender.get(Calendar.DAY_OF_MONTH)).show();
                break;
            case R.id.etMasuk:
                SetJam();
                break;
            case R.id.profildb:
                Intent i = new Intent(Rekap_Absen.this, Actiivity_Identitas.class);
                startActivity(i);
                finish();
                break;

        }

    }
    private void SetJam() {
        Calendar waktusekarang = Calendar.getInstance();
        int jam = waktusekarang.get(Calendar.HOUR);
        int menit = waktusekarang.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog;
        timePickerDialog = new TimePickerDialog(Rekap_Absen.this, new  TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int jam, int menit) {
                if ((menit<10)&&(jam<10)){
                    exmasuk.setText("0"+jam+":0"+menit);
                }else {
                    exmasuk.setText(jam+":"+menit);
                }
            }
        },jam,menit, true);
        timePickerDialog.show();
    }

    private void formatkalender() {
        String format = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat= new SimpleDateFormat(format, Locale.US);
        exidtgl.setText(simpleDateFormat.format(mycalender.getTime()));
    }

    public void StringGetData() {
        IdTgl = exidtgl.getText().toString();
        Exmasuk = exmasuk.getText().toString();
        Shari = vhari.getSelectedItem().toString();
        ExVdeletAll = exvdeletall.getText().toString();
        ExPulang = expulang.getText().toString();
    }
    private void SendData() {
        SQLiteDatabase sqLiteDatabase = dbAbsen.getWritableDatabase();
        //untuk mendapatkan repository dengan metode menulis
        ContentValues values = new ContentValues();
        values.put(DbAbsen.KolomDb.KEY_IdTgl,IdTgl);
        values.put(DbAbsen.KolomDb.Key_Masuk,Exmasuk);
        values.put(DbAbsen.KolomDb.KEY_Pulang,ExPulang);
        values.put(DbAbsen.KolomDb.KEY_DeletAll,ExVdeletAll);
        values.put(DbAbsen.KolomDb.KEY_HARI,Shari);
        sqLiteDatabase.insert(DbAbsen.KolomDb.KEY_Namatabel, null,values);

    }
    private void clearData() {
        exidtgl.setText("");
        exmasuk.setText("");
    }



}