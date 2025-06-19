package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;

public class Profil_Sekolah extends AppCompatActivity implements Metode_Interface, View.OnClickListener {
    public static final String KEY_Url = "Url";
    private WebView webView;
    private ProgressBar progressBar;
    private Button button;
    private TextView tvWa, tvGmail, tvInstagram;
    String data;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_sekolah);
        FindViewById();
        Logic();

        data = getIntent().getStringExtra(KEY_Url);
    }
    @Override
    public void FindViewById() {
        webView = findViewById(R.id.wvprofil_sekolah);
        progressBar = findViewById(R.id.pcprofil_sekolah);
        button = findViewById(R.id.btn_profilsekolah);
        tvWa = findViewById(R.id.fb);
        tvGmail = findViewById(R.id.ig);
        tvInstagram = findViewById(R.id.tiktok);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void Logic() {
        //progressBar.setBackgroundColor(R.color.white);
        button.setOnClickListener(this);
        tvWa.setOnClickListener(this);
        tvGmail.setOnClickListener(this);
        tvInstagram.setOnClickListener(this);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDefaultFontSize(18);
        //webView.setOnClickListener(this);

    }
    private void playVideo(String kodeYt) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        String s = String.valueOf(height);
        int width = displayMetrics.widthPixels;
//        String kode ="<head></head><body>" +
//                "<iframe width=\"380\" height=\"240\" src=\"https://www.youtube.com/embed/" +
//                kodeYt +
//                "\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>" +
//                "</body>";
        String kode ="<head>" +
                "<style>" +
                "body { margin: 0; padding: 0; }" +
                ".video-container { position: relative; padding-bottom: 56.25%; height: 0; overflow: hidden; max-width: 100%; }" +
                ".video-container iframe { position: absolute; top: 0; left: 0; width: 100%; height: 100%; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"video-container\">" +
                "<iframe src=\"https://www.youtube.com/embed/" + kodeYt + "\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe>" +
                "</div>" +
                "</body>";

        webView.loadData(kode, "text/html; charset=utf-8", "UTF-8");

    }

    @Override
    protected void onResume() {
        super.onResume();
        playVideo(data);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_profilsekolah:
                youtube(data);
                break;
            case R.id.fb:
                openFacebookPage();
                break;
            case R.id.tiktok:
                openTikTokProfile();
                break;
            case R.id.ig:
                openIg();
                break;
        }
    }
    private void openIg(){
        Uri uri = Uri.parse("https://www.instagram.com/mtsn3rokanhulu/");
        Intent i = new Intent(Intent.ACTION_VIEW, uri);
        i.setPackage("com.instagram.android");
        try{
            startActivity(i);
        }catch (ActivityNotFoundException e){
            startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/mtsn3rokanhulu/?hl=id")));
        }
        Toast.makeText(this, "Instagram", Toast.LENGTH_SHORT).show();
    }
    private void openFacebookPage() {
        String facebookUrl = "https://web.facebook.com/mtsn3rokanhulu";
        Intent intent;
        try {
            // Coba buka aplikasi Facebook menggunakan URL khusus
            getPackageManager().getPackageInfo("com.facebook.katana", 0);
            String facebookAppUrl = "fb://facewebmodal/f?href=" + facebookUrl;
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookAppUrl));
        } catch (Exception e) {
            // Jika aplikasi Facebook tidak ada, buka di browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl));
        }
        startActivity(intent);
    }

    private void gmailmetod() {
        Intent intent = null, chooser=null;
        intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto"));
        String[] to = {"mtsnegeri3.rokanhulu@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL,to);
        intent.putExtra(Intent.EXTRA_SUBJECT,"From Application MTsN 3 Rokan Hulu");
        intent.putExtra(Intent.EXTRA_TEXT,"Assalamualaikum Admin. ");
        intent.setType("message/rfc822");
        chooser=Intent.createChooser(intent,"Send Mail");
        startActivity(chooser);
        Toast.makeText(this, "Pilih Gmail !", Toast.LENGTH_SHORT).show();
    }
    private void openTikTokProfile() {
        String tiktokUrl = "https://www.tiktok.com/@mtsn3rokanhulu.official";  // Ganti [username] dengan username profil TikTok
        Intent intent;
        try {
            // Coba buka aplikasi TikTok dengan URL khusus
            getPackageManager().getPackageInfo("com.zhiliaoapp.musically", 0);
            String tiktokAppUrl = "tiktok://user?screen_name=" + "mtsn3rokanhulu.official";  // Ganti [username] dengan username
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tiktokAppUrl));
        } catch (Exception e) {
            // Jika aplikasi TikTok tidak ada, buka di browser
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tiktokUrl));
        }
        startActivity(intent);
    }



    private void youtube(String id) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"+id));
        Intent webintent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+id));
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e){
            startActivity(webintent);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}