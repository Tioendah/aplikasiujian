package com.tioprasetioa.www.mtsn3rohul.Ui.admin;

import static com.tioprasetioa.www.mtsn3rohul.Utils.Constans.TOPIC;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.tioprasetioa.www.mtsn3rohul.Api.Api;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Berita;
import com.tioprasetioa.www.mtsn3rohul.Model.notifikasifcm.NotificationMessage;
import com.tioprasetioa.www.mtsn3rohul.Model.notifikasifcm.PushNotification;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Berita;
import com.tioprasetioa.www.mtsn3rohul.Server.Server;
import com.tioprasetioa.www.mtsn3rohul.Ui.MainActivity;
import com.tioprasetioa.www.mtsn3rohul.Utils.Accesstoken;
import com.tioprasetioa.www.mtsn3rohul.Utils.Constans;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Admin_Berita extends AppCompatActivity implements Metode_Interface {
    public static final String KEY_Admin = "Admin";
    private TextView tx_judul;
    private EditText ed_link;
    private Button button,btn_link;
    private ImageView img_paste;
    private MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_berita);
        FindViewById();
        Logic();
    }
    @Override
    public void FindViewById() {
        tx_judul = findViewById(R.id.rv_judul);
        ed_link = findViewById(R.id.rv_updateJudul);
        button = findViewById(R.id.btn_updateLink);
        img_paste = findViewById(R.id.icon_paste);
        btn_link = findViewById(R.id.btn_getLink);
    }
    public static String cutString(String teks, int panjang){
        if (teks.length()>panjang){
            return teks.substring(0, panjang)+"...";
        }else {
            return teks;

        }
    }
    @Override
    public void Logic() {
        Object clipboard = getSystemService(CLIPBOARD_SERVICE);
        final ClipboardManager clipboardManager =  (ClipboardManager)clipboard;
        Model_data_Berita modelDataBerita = getIntent().getParcelableExtra(KEY_Admin);
        String judul = modelDataBerita.getTulisan_judul();
        int cut = 38;
        String getJudul = cutString(judul,cut);
        int id = modelDataBerita.getTulisan_id();
        tx_judul.setText(getJudul);
        img_paste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipData clipData = clipboardManager.getPrimaryClip();
                int item = clipData.getItemCount();
                if (item>0){
                    ClipData.Item item1 = clipData.getItemAt(0);
                    String data = item1.getText().toString();
                    ed_link.setText(data);
                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getLink = ed_link.getText().toString();
                updateData(id,getLink);
                Toast.makeText(Admin_Berita.this, Constans.KEY_BaseUrl, Toast.LENGTH_SHORT).show();
                String title = "M3R NEWS";
                String body = tx_judul.getText().toString();
//                String body = "Berhasil Kodenya Wak";
             // Ganti dengan nama topik yang sesuai

// Pastikan title dan body tidak kosong
                if (!title.isEmpty() && !body.isEmpty()) {
                    NotificationMessage data = new NotificationMessage(title, body, TOPIC); // Ganti dengan topik yang sesuai
                    PushNotification pushNotification = new PushNotification(data); // Buat PushNotification

// Kirim notifikasi
                    sendNotification(pushNotification);
                } else {
                    Log.e("Notifikasi", "Title atau body kosong");
                }


            }
        });
        btn_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = null, chooser = null;
                String url = "https://mtsn3rokanhulu.sch.id/artikel/";
                z = new Intent(Intent.ACTION_VIEW);
                z.setData(Uri.parse(url));
                chooser = Intent.createChooser(z,"Pilih Chrome!");
                startActivity(chooser);
            }
        });
    }
    private void sendNotification(PushNotification pushNotification) {
        Accesstoken accessToken = new Accesstoken();
        String token = accessToken.getAccessToken();
        Api api = Server.getFirebase().create(Api.class);

        Call<PushNotification> call = api.sendNotification(pushNotification, "Bearer " + token);

        call.enqueue(new Callback<PushNotification>() {
            @Override
            public void onResponse(Call<PushNotification> call, Response<PushNotification> response) {
                if (response.isSuccessful()) {
                    Log.i("Notifikasi", "Notifikasi berhasil dikirim");
                } else {
                    Log.e("Error", "Gagal mengirim notifikasi: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<PushNotification> call, Throwable t) {
                Log.e("Error", "Gagal mengirim notifikasi: " + t.getMessage());
            }
        });
    }


    private void updateData(int id, String getLink) {
        Api api = Server.koneksiretrofit().create(Api.class);
        Call<Response_Berita>call = api.update_link(id,getLink);
        call.enqueue(new Callback<Response_Berita>() {
            @Override
            public void onResponse(Call<Response_Berita> call, Response<Response_Berita> response) {
                Toast.makeText(Admin_Berita.this, "Tersimpan", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Response_Berita> call, Throwable throwable) {
                Toast.makeText(Admin_Berita.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}