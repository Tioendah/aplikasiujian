package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Create_Member;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Create_Member extends AppCompatActivity implements Metode_Interface {
    private TextView memberid,memberid2,member_name,birth_date,pin,member_phone,
    register_date, expire_date, password, password2;
    private Button button;
    private Spinner gender, member_type_id;
    String MemberId, MemberId2, Membername, Birth_Date, Instansi, Pin, MemberPhone, Register, Password, Password2, kategoris, Gender, Expired;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_member);
        FindViewById();
        Logic();


    }

    @Override
    public void FindViewById() {
        gender = findViewById(R.id.cm_gender);
        memberid = findViewById(R.id.cm_memberid);
        member_name = findViewById(R.id.cm_member_name);
        pin = findViewById(R.id.cm_pin);
        member_phone = findViewById(R.id.cm_member_phone);
        member_type_id = findViewById(R.id.cm_member_type_id);
        button = findViewById(R.id.cm_btn);
    }

    @Override
    public void Logic() {
        String [] jeniskelamin = new String[]{
                "Jenis Kelamin",
                "Laki",
                "Perempuan"
        };
        String[] kategori = {"Kategori","Guru", "Siswa"};
        spninner(gender,jeniskelamin,"Jenis Kelamin:");
        spninner(member_type_id,kategori,"Kategori");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekdata();
            }
        });


    }
    public boolean cekdata(){
        boolean cekvalue = false;
        MemberId = memberid.getText().toString();
        MemberId2 = memberid.getText().toString();
        Membername = member_name.getText().toString();
        Birth_Date = "1995-07-01";
        Instansi = "MTsN 3 Rokan Hulu";
        Pin = pin.getText().toString();
        MemberPhone = member_phone.getText().toString();
        Register = "2022-08-20";
        Password = "$2y$10$pnHPo0Jpp7Pz0LKAup0S5.2wwc6UNi5WFAsZiDfbVyW9T9MAKz9HW";
        Password2 = "akasia";
        Gender = gender.getSelectedItem().toString();
        kategoris= member_type_id.getSelectedItem().toString();

        int cekdataString = MemberId.length();

        if (MemberId.isEmpty()){
            cekvalue = true;
            memberid.setError("Tidak Boleh Kosong");
        }else if (cekdataString>8){
            cekvalue= true;
            memberid.setError("Angka Member Id Berlebih");
        }else if (cekdataString<8){
            cekvalue = true;
            memberid.setError("Angka Member Id Kurang");
        }
        else if (Membername.isEmpty()){
            cekvalue = true;
            member_name.setError("Tidak Boleh Kosong");
        }else if (Pin.isEmpty()){
            cekvalue = true;
            pin.setError("Tidak Boleh Kosong");
        }else if (MemberPhone.isEmpty()){
            cekvalue = true;
            member_phone.setError("Tidak Boleh Kosong");
        }else if (Gender.isEmpty()){
            cekvalue = true;
            Toast.makeText(this, "Jenis Kelamin Kosong!", Toast.LENGTH_SHORT).show();
        }else if (kategoris.isEmpty()) {
            cekvalue = true;
            Toast.makeText(this, "Kategori Tidak Boleh Kosong!", Toast.LENGTH_SHORT).show();
        }else {
//            createdata();
            Toast.makeText(this, "berhasil", Toast.LENGTH_SHORT).show();
        }
        return cekvalue;
    }
    private void createdata() {
        //tanggal expired
        Calendar date = Calendar.getInstance();
        date.add(Calendar.YEAR,2);
        Date result = date.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String Expired = simpleDateFormat.format(result);

        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Create_Member> call = api.create_member(MemberId,MemberId2,Membername,Gender,Birth_Date,kategoris,Instansi,Pin,MemberPhone,Register,Expired,Password,Password2);
        call.enqueue(new Callback<Response_Create_Member>() {
            @Override
            public void onResponse(Call<Response_Create_Member> call, Response<Response_Create_Member> response) {
                String pesan = response.body().getPesan();
                Toast.makeText(Create_Member.this, pesan, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Response_Create_Member> call, Throwable t) {
                Toast.makeText(Create_Member.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void spninner(Spinner spinner, String[] strings,String toast) {

        final List<String> listHari = new ArrayList<>(Arrays.asList(strings));
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
        spinner.setAdapter(stringArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String pilih = (String)parent.getItemAtPosition(position);
                if (position>0){
                    Toast.makeText(Create_Member.this,toast+pilih,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}