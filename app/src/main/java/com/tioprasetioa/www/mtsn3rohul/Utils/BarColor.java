package com.tioprasetioa.www.mtsn3rohul.Utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

public class BarColor {

    public static void setBarColor(Context context, int statusBarColorResId, int navigationBarColorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Pastikan context adalah instance dari Activity
            if (context instanceof Activity) {
                Window window = ((Activity) context).getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(context, statusBarColorResId));
                window.setNavigationBarColor(ContextCompat.getColor(context, navigationBarColorResId));
            }
        }
    }
}
