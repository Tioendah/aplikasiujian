package com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Data_RekapAbsen;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.Utils.PDFCreationUtils;
import com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.Utils.PdfBitmapCache;
import com.tioprasetioa.www.mtsn3rohul.Utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.BuildConfig;

public class View_Rekap_Absen extends AppCompatActivity implements Metode_Interface, View.OnClickListener {
    TextView textView;
    private DbAbsen dbAbsen;
    private RecyclerView recyclerView;
    private Adapter_RekapAbsen adapter_RekapAbsen;
    private final ArrayList<Model_Data_RekapAbsen>list = new ArrayList<>();

    //IDENTITAS DB
    private TextView vnama, vnip, vunit, vbulan;
    private String NAMA, NIP, UNIT, BULAN;

    //PDF
    private boolean IS_MANY_PDF_FILE;
    private final int SECTOR = 100;
    private  int START;
    private  int END = SECTOR;
    private  int NO_0F_PDF_FILE =1;
    private  int NO_0F_FILE;
    private  int LIST_SIZE;
    private ProgressDialog progressDialog;

    private TextView txPath;
    private FloatingActionButton btnCreate, btn_Share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_rekap_absen);
        FindViewById();
        Logic();
    }
    @Override
    public void FindViewById() {
        dbAbsen = new DbAbsen(getBaseContext());
        recyclerView = findViewById(R.id.recycle_viewAbsen);
        adapter_RekapAbsen = new Adapter_RekapAbsen(list);
        recyclerView.setAdapter(adapter_RekapAbsen);
//        textView.setText("tes");
        //pdf
        btn_Share = findViewById(R.id.btn_share);
        btnCreate = findViewById(R.id.createPdf);
        txPath = findViewById(R.id.btn_path);


        //identitas
        vnama = findViewById(R.id.Vnama);
        vnip = findViewById(R.id.Vnip);
        vunit = findViewById(R.id.Vunit);
        vbulan = findViewById(R.id.Vbulan);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("Myuser", Context.MODE_PRIVATE);
        NAMA = sp.getString("nama","");
        NIP = sp.getString("nip","");
        UNIT = sp.getString("unitkerja","");
        BULAN = sp.getString("bulan","");

    }
    @Override
    public void Logic() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setHasFixedSize(true);
        //menangkap Data
        SQLiteDatabase viewdata = dbAbsen.getReadableDatabase();
        Cursor cursor = viewdata.rawQuery("SELECT * FROM "+DbAbsen.KolomDb.KEY_Namatabel,null);
        cursor.moveToFirst();
        for (int a = 0; a<cursor.getCount();a++){
            cursor.moveToPosition(a);
            list.add(new Model_Data_RekapAbsen(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4)));
        }

        //identitas
        vnama.setText(NAMA);
        vnip.setText(NIP);
        vunit.setText(UNIT);
        vbulan.setText(BULAN);

        //PDF
        btnCreate.setOnClickListener(this);
        btn_Share.setOnClickListener(this);
        btn_Share.setVisibility(View.GONE);
    }
    @SuppressLint({"NotifyDataSetChanged", "NonConstantResourceId"})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createPdf:
                btnCreate.setVisibility(View.GONE);
                btn_Share.setVisibility(View.VISIBLE);
                try {
                    requestpermisi();
                }catch (Exception e){
                    Utils.Toast(View_Rekap_Absen.this,"File sudah ada,Aplikasi tidak bisa menimpa file tersebut, silahkan hapus dulu file terdahulu");
                }
                break;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 111 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            generatePDFReport();
        }
    }
    private void requestpermisi() {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},111);
        }else {
            generatePDFReport();
        }
    }
    private void generatePDFReport() {
        LIST_SIZE = list.size();
        NO_0F_FILE = LIST_SIZE / SECTOR;
        if (LIST_SIZE % SECTOR != 0){
            NO_0F_FILE++;
        }if (LIST_SIZE > SECTOR){
            IS_MANY_PDF_FILE = true;
        }else {
            END = LIST_SIZE;
        }
        try {
            createPDFFile();
        }
        catch (Exception e){
            e.getMessage();
        }
        finally {
            Utils.Alert(this,"Selesai","Nama file= 'Rekap Absen.pdf'");
        }


    }
    private void createProgressbarForPDFCreation(int maxProgress){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(String.format("XML LAYOUT IS IN CREATED. PLEASE WAT", maxProgress));
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(maxProgress);
        progressDialog.show();
    }
    private void createProgressBarForMergePDF(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(getString(R.string.pdf));
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
    public void createPDFFile() {
        List<Model_Data_RekapAbsen>pdfDataList = list.subList(START,END);
        PdfBitmapCache.clearMemory();
        PdfBitmapCache.initBitmapCache(getApplicationContext());
        final PDFCreationUtils pdfCreationUtils = new PDFCreationUtils(View_Rekap_Absen.this,pdfDataList,LIST_SIZE, NO_0F_PDF_FILE);

        if (NO_0F_PDF_FILE == 1){
            createProgressbarForPDFCreation(PDFCreationUtils.TOTAL_PROGRESS_BAR);
        }
        pdfCreationUtils.createPDF(new PDFCreationUtils.PDFCallback() {
            @Override
            public void onProgress(final int progress) {
                progressDialog.setProgress(progress);
            }

            @Override
            public void onCreateEveryPdfFile() {
                if (IS_MANY_PDF_FILE){
                    NO_0F_PDF_FILE++;
                    if (NO_0F_FILE == NO_0F_PDF_FILE -1){
                        progressDialog.dismiss();
                        createProgressBarForMergePDF();
                        pdfCreationUtils.downloadAndCombinePDFs();
                    }
                    else {
                        START = END;
                        if (LIST_SIZE % SECTOR != 0){
                            if (NO_0F_FILE == NO_0F_PDF_FILE){
                                END = (START - SECTOR)+LIST_SIZE % SECTOR;
                            }
                        }
                        END = SECTOR + END;
                        createPDFFile();
                    }
                }else {
                    progressDialog.dismiss();
                    createProgressBarForMergePDF();
                    pdfCreationUtils.downloadAndCombinePDFs();
                }
            }

            @Override
            public void onComplete(final String filePath) {
                progressDialog.dismiss();
                if (filePath != null){
                    txPath.setVisibility(View.VISIBLE);
                    txPath.setText("PDF path"+ filePath);
                    Toast.makeText(View_Rekap_Absen.this,"pdf lokasi"+filePath, Toast.LENGTH_LONG).show();
                    btn_Share.setVisibility(View.VISIBLE);
                    btn_Share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            btnCreate.setVisibility(View.VISIBLE);
                            btn_Share.setVisibility(View.GONE);
                            sharedPdf(filePath);
                        }
                    });
                }
            }
            @Override
            public void onError(Exception e) {
                Toast.makeText(View_Rekap_Absen.this,"Error"+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void sharedPdf(String fileName) {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        emailIntent.setType("text/plain");
        emailIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        ArrayList<Uri>uris = new ArrayList<Uri>();
        File fileIn = new File(fileName);
        Uri u = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID,fileIn);
        uris.add(u);

        emailIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM,uris);
        try {
            startActivity(Intent.createChooser(emailIntent,getString(R.string.send_to)));
        }catch (ActivityNotFoundException e){
            Toast.makeText(View_Rekap_Absen.this,"Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(View_Rekap_Absen.this,Rekap_Absen.class));
        finish();
    }


}