package com.tioprasetioa.www.mtsn3rohul.Ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.tioprasetioa.www.mtsn3rohul.Interface.Metode_Interface;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Utils.Sharef_Pref_Intro;

public class Intro extends AppCompatActivity implements ViewPager.OnPageChangeListener, Metode_Interface, View.OnClickListener {

    public ViewPager viewPager;
    public MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout linearLayout;
    private TextView[] dots;
    private int[] layoutsint;
    private Button btnskip, btnnext;
    private Sharef_Pref_Intro sharef_pref_intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //pengecekan apakah
        sharef_pref_intro = new Sharef_Pref_Intro(this);
        if (!sharef_pref_intro.jikapertamakalimuncul()){
            launchhome();
            finish();
        }
        if (Build.VERSION.SDK_INT>=21){
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_intro);
        FindViewById();


        //untuk menampilkan layout ketika button di klik
        layoutsint = new int[]{
                R.layout.intro_1,
                R.layout.intro_2,
                R.layout.intro_3,
                R.layout.intro_4,
        };
        addbottomdots(0);
        Logic();


    }
    @Override
    public void FindViewById() {
        viewPager = findViewById(R.id.view_pager);
        linearLayout = findViewById(R.id.layoutDots);
        btnnext = findViewById(R.id.btn_next);
        btnskip = findViewById(R.id.btn_skip);
    }

    @Override
    public void Logic() {
        changeStatusbar();
        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        btnnext.setOnClickListener(this);
        btnskip.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                int sekarang = getItem(+1);
                if (sekarang < layoutsint.length){
                    viewPager.setCurrentItem(sekarang);
                }else {
                    launchhome();

                }
                break;
            case R.id.btn_skip:
                launchhome();
                break;
        }
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem()+i;
    }


    private void changeStatusbar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void addbottomdots(int posisi) {
        dots = new TextView[layoutsint.length];

        int[] coloractiv= getResources().getIntArray(R.array.array_dot_active);
        int[] colortidakactiv= getResources().getIntArray(R.array.array_dot_inactive);

        linearLayout.removeAllViews();
        for (int i = 0; i<dots.length;i++){
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colortidakactiv[posisi]);
            linearLayout.addView(dots[i]);
        }
        if (dots.length>0){
            dots[posisi].setTextColor(coloractiv[posisi]);
        }

    }

    private void launchhome() {
        sharef_pref_intro.setPertamakali(false);
        startActivity(new Intent(Intro.this,Splash_Screen.class));
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        addbottomdots(position);
        if (position ==  layoutsint.length -1){
            btnnext.setText(getString(R.string.lanjut));
            btnskip.setVisibility(View.GONE);
        }else {
            btnnext.setText(getString(R.string.lanjut));
            btnskip.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }



    ///////////////////////////////////////////////////////
    //ADAPTER VIEW PAGER
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        @Override
        public int getCount() {
            return layoutsint.length;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(layoutsint[position],container,false);
            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}
