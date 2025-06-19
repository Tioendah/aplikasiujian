package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Berita;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Galeri_Home;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_data_Image_Slider;
import com.tioprasetioa.www.mtsn3rohul.R;

public class WebViewBerita extends Activity implements Metode_Interface {
    public static final String KEY_Web = "Web";
    public static final String KEY_key = "key";
    public static final String KEY_key2 = "key2";
    private WebView webView;
    private ImageView imageView;
//    private RelativeLayout adContainerView;
    private AdView adView;
    private RelativeLayout adContainerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        FindViewById();
        Logic();
    }

    @Override
    public void FindViewById() {
        webView = findViewById(R.id.webview);
        imageView = findViewById(R.id.loadingwebview);
        Glide.with(WebViewBerita.this)
                .load(R.raw.ic_splashgif)
                .centerCrop()
                .into(imageView);
        imageView.setVisibility(View.VISIBLE);
        adView = findViewById(R.id.adView);
        adContainerView = findViewById(R.id.adContainerView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void Logic() {
        iklanBanner();

        String key = getIntent().getStringExtra(KEY_key);
        if (key.equals("1")){
            Model_data_Galeri_Home model_data_galeri_home = getIntent().getParcelableExtra(KEY_Web);
            String url2 = model_data_galeri_home.getUrl();
            webView.loadUrl(url2);
        }else if (key.equals("2")){
            Model_data_Image_Slider model_data_image_slider = getIntent().getParcelableExtra(KEY_Web);
            String url = model_data_image_slider.getUrl();
            webView.loadUrl(url);
        }else if (key.equals("3")){
            Model_data_Berita model_data_berita = getIntent().getParcelableExtra(KEY_Web);
            String url = model_data_berita.getUrl();
            webView.loadUrl(url);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowContentAccess(true);
        webSettings.getCacheMode();
        webSettings.getDefaultFixedFontSize();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                imageView.setVisibility(View.GONE);
            }
        });
    }

    private void iklanBanner() {
        AdSize adSize = getAdSize();
        loadBanner(adSize);

    }

    private AdSize getAdSize() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = outMetrics.density;
        float adWidthPixels = adView.getWidth();

        if (adWidthPixels == 0) {
            adWidthPixels = outMetrics.widthPixels;
        }

        int adWidth = (int) (adWidthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private void loadBanner(AdSize adSize) {
        // Tidak mengatur ulang adSize karena sudah diatur di XML
        adView.setAdListener(new AdListener() {
            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                Log.e("banner", "onAdFailedToLoad: " + adError.getMessage());
            }

            @Override
            public void onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        });

        adContainerView.removeAllViews();
        adContainerView.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
}