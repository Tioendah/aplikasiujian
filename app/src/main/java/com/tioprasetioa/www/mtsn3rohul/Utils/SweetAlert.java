package com.tioprasetioa.www.mtsn3rohul.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.tioprasetioa.www.mtsn3rohul.Ui.ujian.BerandaUjian;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetAlert {

    public void AlertBerandaUjian(String title, String desc, String confirm, String cancel, Context context, AppCompatActivity activity){
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(title);
        pDialog.setContentText(desc);
        pDialog.setConfirmText(confirm);
        pDialog.setCancelText(cancel);
        pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog.cancel();
            }
        });
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                context.startActivity(new Intent(context, BerandaUjian.class));
                activity.finish();
            }
        });
        pDialog.show();
    }
    public void AlerUpdateVersion(String title, String desc, String confirm, String cancel, Context context, AppCompatActivity activity){
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(title);
        pDialog.setContentText(desc);
        pDialog.setCancelable(false);
        pDialog.setConfirmText(confirm);
        pDialog.setCancelText(cancel);
        pDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                activity.finish();
            }
        });
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.tioprasetioa.www.mtsn3rohul")));
                } catch (android.content.ActivityNotFoundException e) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.tioprasetioa.www.mtsn3rohul")));
                }
            }
        });
        pDialog.show();
    }
    public static void AlertOK(String title, String desc, String confirm, Context context){
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(title);
        pDialog.setContentText(desc);
        pDialog.setConfirmText(confirm);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                pDialog.dismissWithAnimation();
            }
        });
        pDialog.show();
    }
    public static void Loading(String title, Context context, View view){
        SweetAlertDialog alerLoaading = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        alerLoaading.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        alerLoaading.setTitleText(title);
        alerLoaading.setCancelable(true);
        alerLoaading.show();
    }
    public void customimage(Context context, int image, String title, String desc, String confirm) {
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        sweetAlertDialog.setTitle(title);
        sweetAlertDialog.setContentText(desc);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCustomImage(image);
        sweetAlertDialog.setConfirmText(confirm);
        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        sweetAlertDialog.show();

    };


}
