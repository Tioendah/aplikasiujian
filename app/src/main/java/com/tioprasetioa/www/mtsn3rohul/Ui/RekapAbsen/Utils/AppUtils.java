package com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_Data_RekapAbsen;
import com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.Adapter_RekapAbsen;

import java.io.File;
import java.util.List;

public class AppUtils extends AppCompatActivity {
    private Context context;

    public static Bitmap findViewBitmap(final List<Model_Data_RekapAbsen> currentPDFModels, int deviceWidth, int deviceHeight, Adapter_RekapAbsen pdfRootAdapter, RecyclerView mPDFCreationRV, View mPDFCreationView) {
        pdfRootAdapter.setListData(currentPDFModels);
        mPDFCreationRV.setAdapter(pdfRootAdapter);
        return getViewBitmap(mPDFCreationView, deviceWidth, deviceHeight);
    }

    private static Bitmap getViewBitmap(View view, int deviceWidth, int deviceHeight) {
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(deviceHeight, View.MeasureSpec.EXACTLY);
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        Bitmap b = Bitmap.createBitmap(deviceWidth, deviceHeight, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        view.draw(c);
        //bukan
        return getResizedBitmap(b, (measuredWidth * 80) / 100, (measuredHeight * 80) / 100);
    }

    private static Bitmap getResizedBitmap(Bitmap image, int width, int height) {

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            height = (int) (width / bitmapRatio);
        } else {
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }

    public  String createPDFPath() {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageDirectory())){
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "Rekap Absen.pdf");
            return file.getPath();
        }else {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),"Rekap Absen.pdf");
            return file.getPath();
        }
    }

}
