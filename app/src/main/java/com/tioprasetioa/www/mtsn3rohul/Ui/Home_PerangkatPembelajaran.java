package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;

public class Home_PerangkatPembelajaran extends AppCompatActivity implements Metode_Interface, View.OnClickListener {

    private CardView cardView, cardView2, cardView3, cardView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_perangkat_pembelajaran);
        FindViewById();
        Logic();
    }

    @Override
    public void FindViewById() {
//        cardView = findViewById(R.id.cardpos1);
//        cardView2 = findViewById(R.id.cardpos2);
//        cardView3 = findViewById(R.id.cardpos3);
//        cardView4 = findViewById(R.id.cardpos5);
    }

    @Override
    public void Logic() {
        cardView.setOnClickListener(this);
        cardView2.setOnClickListener(this);
        cardView3.setOnClickListener(this);
        cardView4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
//            case R.id.cardpos1:
//                startActivity(new Intent(Home_PerangkatPembelajaran.this, Perangkat_Pembelajaran.class));
//                break;
//            case R.id.cardpos2:
//                startActivity(new Intent(Home_PerangkatPembelajaran.this, PeranglkatPbmVIII.class));
//                break;
//            case R.id.cardpos3:
//                startActivity(new Intent(Home_PerangkatPembelajaran.this, PerangkatPbmIX.class));
//                break;
//            case R.id.cardpos5:
//                startActivity(new Intent(Home_PerangkatPembelajaran.this,Modul.class));
        }
    }
}