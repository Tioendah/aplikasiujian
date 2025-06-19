package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_ListLoan;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class Input_Loan extends AppCompatActivity implements Metode_Interface, View.OnClickListener {
    private AutoCompleteTextView autoCompleteTextView;
    private TextView textView;
    DatePickerDialog.OnDateSetListener dateSetListener;
    Calendar calendar;
    private TextInputEditText textInputEditText,textInputEditTexttgl;
    private Button simpan;
    String Item_code,Member_Id,Loan_Data,Return_Date,Is_Return,Mapel, Due_Date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_loan);
        FindViewById();
        Logic();


//        Mapel = autoCompleteTextView.getText().toString();

    }
    @Override
    public void FindViewById() {
        autoCompleteTextView = findViewById(R.id.atx_NamaBuku);
//        edittext.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        autoCompleteTextView.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        textView = findViewById(R.id.tx_hint);
        simpan = findViewById(R.id.btn_insert);
        textInputEditText = findViewById(R.id.mt_memberid);
        textInputEditTexttgl = findViewById(R.id.mt_expiredloan);
    }
    @Override
    public void Logic() {

        simpan.setOnClickListener(this);
        textInputEditText.setOnClickListener(this);
        textInputEditTexttgl.setOnClickListener(this);


        List<String>list = new ArrayList<>();
        list.add("IPS KELAS 3");
        list.add("IPS TERPADU KELAS 3 KELAS IX SMP");
        list.add("IPS TERPADU 2 (UNTUK KELAS VIII)");
        list.add("SENI BUDAYA UNTUK KELAS IX");
        list.add("SENI BUDAYA KELAS 3");
        list.add("MARBI MAHIR BERBAHASA INDONESIA KELAS 3");
        list.add("MAHIR BERBAHASA INDONESIA UNTUK KELAS IX");
        list.add("MATEMATIKA (UNTUK KELAS VIII");
        list.add("MATEMATIKA");
        list.add("JELAJAH MATEMATIKA SMP KELAS VIII");
        list.add("PRAKARYA UNTUK SMP KELAS IX");
        list.add("PRAKARYA UNTUK KELAS IX");
        list.add("BRIGHT AN ENGLISH SMP KELAS 9");
        list.add("IPA 2A TERPADU");
        list.add("IPA TERPADU UNTUK KELAS VII");
        list.add("IPA TERPADU KELAS IX");
        list.add("IPA TERPADU SMP KELAS VIII");
        list.add("IPA 2B");
        list.add("IPA TERPADU");
        list.add("IPA 2A");
        list.add("IPA 3A");
        list.add("PENDIDIKAN PANCASILA DAN KEWARGANEGARAAN UNTUK SMP/MTs KELAS IX");
        list.add("PPKN PENDIDIKAN PANCASILA DAN KEWARGANEGARAAN UNTUK KELAS 3");
        list.add("SEJARAH KEBUDAYAAN ISLAM KELAS IX");
        list.add("SEJARAH KEBUDAYAAN ISLAM KELAS VIII");
        list.add("BAHASA ARAB UNTUK KELAS VII");
        list.add("BAHASA ARAB KELAS VII");
        list.add("PENJASORKES");
        list.add("FIKIH KELAS VII");
        list.add("AKIDAH AKHLAK KELAS VIII");
        list.add("AKIDAH AKHLAK KELAS VII");
        list.add("AL-QUR’AN HADITS KELAS VII");
        list.add("FIKIH VIII");
        list.add("AKIDAH AKHLAK KELAS IX KEMENAG");
        list.add("BAHASA ARAB KELAS VIII KEMENAG");
        list.add("BAHASA ARAB KELAS VII KEMENAG");
        list.add("BAHASA ARAB KELAS IX KEMENAG");
        list.add("AL-QUR’AN HADIS KEMENAG");
        list.add("AKIDAH AKHLAK KELAS VII KEMENAG");
        list.add("AKIDAH AKHLAK KELAS VIII KEMENAG");
        list.add("SEJARAH KEBUDAYAAN ISLAM KELAS IX KEMENAG");
        list.add("FIKIH KELAS VII KEMENAG");
        list.add("SEJARAH KEBUDAYAAN ISLAM KELAS VIII KEMENAG");
        list.add("FIKIH KELAS VIII KEMENAG");
        list.add("BRILIAN IPS ILMU PENGETAHUAN SOSIAL UNTUK SMP/MTs  KELAS VIII");
        list.add("BAHASA INDONESIA UNTUK SMP/MTs KELAS IX");
        list.add("ALQUR’AN HADIS KELAS VII KEMENAG");
        list.add("FIKIH KELAS IX KEMENAG");
        list.add("IPA ILMU PENGETAHUAN ALAM KELAS VIII");
        list.add("BRILIANT BAHASA INGGRIS SMP/MTs KELAS VIII");
        list.add("BAHASA INGGRIS SMP/MTs KELAS IX");
        list.add("BAHASA INGGRIS SMP KELAS VII");
        list.add("SEJARAH KEBUDAYAAN ISLAM KELAS VII");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,list);
        autoCompleteTextView.setAdapter(arrayAdapter);
        calendar = Calendar.getInstance();
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int tahun, int bulan, int hari) {
                calendar.set(Calendar.YEAR,tahun);
                calendar.set(Calendar.MONTH,bulan);
                calendar.set(Calendar.DAY_OF_MONTH,hari);
                String formattanggal = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formattanggal, Locale.US);
                textInputEditTexttgl.setText(simpleDateFormat.format(calendar.getTime()));
            }
        };
    }
    public void createdata(){
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_ListLoan>call = api.createdata(Item_code,Member_Id,Loan_Data,Return_Date,Is_Return,Mapel, Due_Date);
        call.enqueue(new Callback<Response_ListLoan>() {
            @Override
            public void onResponse(Call<Response_ListLoan> call, Response<Response_ListLoan> response) {
                if (response.isSuccessful()){
                    String pesan = "Data Tersimpan";
                    Utils.Toast(Input_Loan.this,pesan);
                }
            }

            @Override
            public void onFailure(Call<Response_ListLoan> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_insert:
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDateTime sekarantg = LocalDateTime.now();
                Loan_Data = dateTimeFormatter.format(sekarantg);//tanggal buku dipinjam
                Member_Id = textInputEditText.getText().toString();//id pelanggan
                Return_Date = "0000-00-00";                         //tanggal buku dikembalikan
                Is_Return = "0";                                    //kondisi buku dikembalikan atau belum
                Mapel = autoCompleteTextView.getText().toString();  //Nama Judul Buku
                Due_Date = textInputEditTexttgl.getText().toString();//Tanggal jatuh tempo
                kondisimetod();
                cekdata();
                break;
            case R.id.mt_expiredloan:
                new DatePickerDialog(Input_Loan.this,dateSetListener,calendar.get(Calendar.
                        YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .show();
                break;


        }
    }

    private boolean cekdata() {
        boolean cek = false;
        if (Member_Id.isEmpty()){
            cek = true;
            textInputEditText.setError("Harus Diisi!");
        }else if (Mapel.isEmpty()){
            cek = true;
            autoCompleteTextView.setError("Harus Diisi!");
        }else if (Due_Date.isEmpty()){
            cek = true;
            textInputEditTexttgl.setError("Harus Diisi!");
        }else {
            cek = true;
            createdata();
            kosongkandata();
        }
        return cek;
    }

    private void kosongkandata() {
        textInputEditTexttgl.setText("");
        autoCompleteTextView.setText("");
        textInputEditText.setText("");
    }

    private void kondisimetod() {
        if (Mapel.equals("IPS KELAS 3")){
            Item_code = "IPSK001IX";
        }else if (Mapel.equals("IPS TERPADU KELAS 3 KELAS IX SMP")){
            Item_code = "IPSTK015IX";
        }else if (Mapel.equals("IPS TERPADU 2 (UNTUK KELAS VIII)")){
            Item_code = "IPST001VIII";
        }else if (Mapel.equals("SENI BUDAYA UNTUK KELAS IX")){
            Item_code = "SBK001IX";
        }else if (Mapel.equals("SENI BUDAYA KELAS 3")){
            Item_code = "SBK009IX";
        }else if (Mapel.equals("MARBI MAHIR BERBAHASA INDONESIA KELAS 3")){
            Item_code = "BIK0211X";
        }else if (Mapel.equals("MAHIR BERBAHASA INDONESIA UNTUK KELAS IX")){
            Item_code = "MRBI001IX";
        }else if (Mapel.equals("MATEMATIKA (UNTUK KELAS VIII)")){
            Item_code = "MTK001VIII";
        }else if (Mapel.equals("MATEMATIKA")){
            Item_code = "MTK011IXA";
        }else if (Mapel.equals("JELAJAH MATEMATIKA SMP KELAS VIII")){//
            Item_code = "JMSK048VIII";
        }else if (Mapel.equals("PRAKARYA UNTUK SMP KELAS IX")){
            Item_code = "PRAKRYAABD001IX";
        }else if (Mapel.equals("PRAKARYA UNTUK KELAS IX")){
            Item_code = "PRAKRYA009IX";
        }else if (Mapel.equals("BRIGHT AN ENGLISH SMP KELAS 9")){
            Item_code = "BAE009IX";
        }else if (Mapel.equals("IPA 2A TERPADU")){
            Item_code = "IPA2A001VIII";
        }else if (Mapel.equals("IPA TERPADU UNTUK KELAS VII")){
            Item_code = "IPAT001VII";
        }else if (Mapel.equals("IPA TERPADU KELAS IX")){
            Item_code ="IPAT021IX";
        }else if (Mapel.equals("IPA TERPADU SMP KELAS VIII")){
            Item_code = "IPAT001VIII";
        }else if (Mapel.equals("IPA 2B")){
            Item_code = "IPA2B001VIII";
        }else if (Mapel.equals("IPA TERPADU ")){
            Item_code = "IPAT001";
        }else if (Mapel.equals("IPA 2A")){//20
            Item_code = "IPA001VIII";
        }else if (Mapel.equals("IPA 3A")){
            Item_code = "IPA001IX";
        }else if (Mapel.equals("PENDIDIKAN PANCASILA DAN KEWARGANEGARAAN UNTUK SMP/MTs KELAS IX")){
            Item_code = "PPKNY001IX";
        }else if (Mapel.equals("PPKN PENDIDIKAN PANCASILA DAN KEWARGANEGARAAN UNTUK KELAS 3")){
            Item_code = "PPKN001IX";
        }else if (Mapel.equals("SEJARAH KEBUDAYAAN ISLAM KELAS IX")){
            Item_code = "SKI001IX";
        }else if (Mapel.equals("SEJARAH KEBUDAYAAN ISLAM KELAS VIII")){
            Item_code = "SKI001VIII";
        }else if (Mapel.equals("BAHASA ARAB UNTUK KELAS VII")){
            Item_code = "BHSARAB001VII";
        }else if (Mapel.equals("BAHASA ARAB KELAS VII")){
            Item_code = "BARB001VII";
        }else if (Mapel.equals("PENJASORKES")){
            Item_code = "PJOK049IX";
        }else if (Mapel.equals("FIKIH KELAS VII")){
            Item_code = "FKH101VII";
        }else if (Mapel.equals("AKIDAH AKHLAK KELAS VIII")){//30
            Item_code = "AKAKH001VIII";
        }else if (Mapel.equals("AKIDAH AKHLAK KELAS VII")){
            Item_code = "AKAKH001VII";
        }else if (Mapel.equals("AL-QUR’AN HADITS KELAS VII")){
            Item_code = "ALQRNHDS0101VII";
        }else if (Mapel.equals("FIKIH KELAS III")){
            Item_code = "FKH001VIII";
        }else if (Mapel.equals("AKIDAH AKHLAK KELAS IX KEMENAG")){
            Item_code = "AkidahAkhlk009IX";
        }else if (Mapel.equals("BAHASA ARAB KELAS VIII KEMENAG")){
            Item_code = "BARAB007VIII";
        }else if (Mapel.equals("BAHASA ARAB KELAS VII KEMENAG")){
            Item_code = "BARAB001VII";
        }else if (Mapel.equals("BAHASA ARAB KELAS IX KEMENAG")){
            Item_code = "BARAB009IX";
        }else if (Mapel.equals("AL-QUR’AN HADIS KEMENAG")){
            Item_code = "AKDHKMG002VIII";
        }else if (Mapel.equals("AKIDAH AKHLAK KELAS VII KEMENAG")){
            Item_code = "AAKMNG0013";
        }else if (Mapel.equals("AKIDAH AKHLAK KELAS VIII KEMENAG")){//40
            Item_code = "AA009VIII";
        }else if (Mapel.equals("SEJARAH KEBUDAYAAN ISLAM KELAS IX KEMENAG")){
            Item_code = "SJRHKI001VII";
        }else if (Mapel.equals("FIKIH KELAS VII KEMENAG")){
            Item_code = "FKHKMNG009VII";
        }else if (Mapel.equals("SEJARAH KEBUDAYAAN ISLAM KELAS VIII KEMENAG")){
            Item_code = "SKIKMNG007VIII";
        }else if (Mapel.equals("FIKIH KELAS VIII KEMENAG")){
            Item_code = "FIKIHKMNG001VIII";
        }else if (Mapel.equals("BRILIAN IPS ILMU PENGETAHUAN SOSIAL UNTUK SMP/MTs  KELAS VIII")){
            Item_code = "BIPS001VIII";
        }else if(Mapel.equals("BAHASA INDONESIA UNTUK SMP/MTs KELAS IX")){
            Item_code = "BI001IX";
        }else if (Mapel.equals("ALQUR’AN HADIS KELAS VII KEMENAG")){
            Item_code = "ALQRNHDSKA001IX";
        }else if (Mapel.equals("FIKIH KELAS IX KEMENAG")){
            Item_code = "FIKIH006IX";
        }else if (Mapel.equals("IPA ILMU PENGETAHUAN ALAM KELAS VIII")){
            Item_code = "IPA001VIII";
        }else if (Mapel.equals("BRILIANT BAHASA INGGRIS SMP/MTs KELAS VIII")){//50
            Item_code ="BBI001VIII";
        }else if (Mapel.equals("BAHASA INGGRIS SMP/MTs KELAS IX")){
            Item_code = "BBI001IX";
        }else if (Mapel.equals("BAHASA INGGRIS SMP KELAS VII")){
            Item_code = "BBI001VII";
        }else if (Mapel.equals("SEJARAH KEBUDAYAAN ISLAM KELAS VII")){
            Item_code = "SKIKMNG001VIII";
        }

    }
}