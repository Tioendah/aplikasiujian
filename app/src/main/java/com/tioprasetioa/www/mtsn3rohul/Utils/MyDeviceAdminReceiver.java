package com.tioprasetioa.www.mtsn3rohul.Utils;

import android.app.admin.DeviceAdminReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyDeviceAdminReceiver extends DeviceAdminReceiver {
    private static final String TAG = MyDeviceAdminReceiver.class.getSimpleName();

    public static ComponentName getComponentName(Context context) {
        return new ComponentName(context.getApplicationContext(), MyDeviceAdminReceiver.class);
    }

    @Override
    public void onLockTaskModeEntering(@NonNull Context context, @NonNull Intent intent, String pkg) {
        super.onLockTaskModeEntering(context, intent, pkg);
        Toast.makeText(context, "Berhasil", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onLockTaskModeEntering");
    }

    @Override
    public void onLockTaskModeExiting(@NonNull Context context, @NonNull Intent intent) {
        super.onLockTaskModeExiting(context, intent);
        Log.d(TAG, "onLockTaskModeExiting");
    }
}
