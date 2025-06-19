package com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen;

import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Data_RekapAbsen;
import com.tioprasetioa.www.mtsn3rohul.R;

import java.util.Calendar;

public class Update extends AppCompatActivity implements Metode_Interface, View.OnClickListener {
    public static final String KEY_UPDATE = "UPDATE";
    private EditText editText;
    private TextView tes;
    private Button button;
    private DbAbsen dbAbsen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        FindViewById();
        Logic();
    }
    @Override
    public void FindViewById() {
        editText = findViewById(R.id.Exupdate);
        button = findViewById(R.id.btn_update);
        dbAbsen = new DbAbsen(getBaseContext());
    }
    @Override
    public void Logic() {
        button.setOnClickListener(this);
        editText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Exupdate:
                SetJam();
                break;
            case R.id.btn_update:
                Model_Data_RekapAbsen Key_Id = getIntent().getParcelableExtra(KEY_UPDATE);
                String EditText = editText.getText().toString();//menangkap data yang ingin dirubah

                String MenangkapId = Key_Id.getId();
                SQLiteDatabase sqLiteDatabase = dbAbsen.getReadableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put(DbAbsen.KolomDb.KEY_Pulang,EditText);//ini untuk mrubah data

                String s = DbAbsen.KolomDb.KEY_IdTgl+" LIKE ?";
                String[] sarray = {MenangkapId};

                sqLiteDatabase.update(DbAbsen.KolomDb.KEY_Namatabel,contentValues,s,sarray);
                Toast.makeText(Update.this,"Berhasil",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Update.this,View_Rekap_Absen.class);
                startActivity(intent);
                finish();
                break;


        }
    }
    private void SetJam() {
        Calendar waktu = Calendar.getInstance();
        int Jam = waktu.get(Calendar.HOUR);
        int Minute = waktu.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(Update.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if ((minute<10)&&(hourOfDay<10)){
                    editText.setText("0"+hourOfDay+":0"+minute);
                }else {
                    editText.setText(hourOfDay+":"+minute);
                }
            }
        },Jam,Minute,true);
        timePickerDialog.show();
    }
}