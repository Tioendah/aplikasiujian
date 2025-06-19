package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Login_Data;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Login;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Utils.SharedPreference_Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;
    private Button btn_login;
    private TextView lupasandi;
    private SharedPreference_Login session_manager;

    private String Member_id2, Mpasswd2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.reg_name_et);
        password = findViewById(R.id.reg_password_et);
        btn_login = findViewById(R.id.btn_login);
        lupasandi = findViewById(R.id.text_login);

        btn_login.setOnClickListener(this);
        lupasandi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Member_id2 = username.getText().toString();
                Mpasswd2 = password.getText().toString();
                login(Member_id2, Mpasswd2);
                break;
            case R.id.text_login:
                String uri = "Saya lupa akun perpustakaan, Bagaimana solusinya pak?";
                Intent i = null, chooser= null;
                i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://api.whatsapp.com/send?phone=6282288080369&text=Assalamulaikum Pak Tio, "+uri));
                chooser = Intent.createChooser(i, "Launch WhatsApp");
                startActivity(chooser);
                break;
        }
    }
    private void login(String member_id2, String mpasswd2) {
        Api apiInterface = Server.koneksiretrofit().create(Api.class);
        Call<Response_Login> loginCall = apiInterface.LoginResponse(member_id2, mpasswd2);
        loginCall.enqueue(new Callback<Response_Login>() {
            @Override
            public void onResponse(Call<Response_Login> call, Response<Response_Login> response) {
             try {
                 if(response.body() != null && response.isSuccessful()){

                     // Ini untuk menyimpan sesi
                     session_manager = new SharedPreference_Login(Login.this);
                     Model_Login_Data modelLoginData = response.body().getData();
                     session_manager.createLoginSesion(modelLoginData);

                     Toast.makeText(Login.this, "Silahkan Refresh!", Toast.LENGTH_SHORT).show();
                     //Ini untuk pindah
                     finish();
                 }else {
                     Toast.makeText(Login.this, "gagal", Toast.LENGTH_SHORT).show();
                 }
             }catch (Exception e){
                 Toast.makeText(Login.this, "Sandi/Password Salah", Toast.LENGTH_SHORT).show();
             }
            }


            @Override
            public void onFailure(Call<Response_Login> call, Throwable t) {
                Toast.makeText(Login.this, "error"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(Login.this, "Silahkan Refresh!", Toast.LENGTH_SHORT).show();
        //Ini untuk pindah
    }
}