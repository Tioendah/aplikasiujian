package com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.Utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tioprasetioa.www.mtsn3rohul.Model.Model_Data_RekapAbsen;
import com.tioprasetioa.www.mtsn3rohul.R;
import com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.Adapter_RekapAbsen;
import com.tioprasetioa.www.mtsn3rohul.Ui.RekapAbsen.View_Rekap_Absen;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class PDFCreationUtils extends AppCompatActivity {


    private int deviceHeight;
    private int deviceWidth;
    private Bitmap bitmapOfView;
    private PdfDocument document;
    private int pdfModelListSize;
    private int SECTOR = 8; // default value
    private int NUMBER_OF_PAGE;
    private List<Model_Data_RekapAbsen> mCurrentPDFModels;
    private Adapter_RekapAbsen pdfRootAdapter;
    private View mPDFCreationView;
    private RecyclerView mPDFCreationRV;
    public static int TOTAL_PROGRESS_BAR;
    private TextView textView;
    private int mCurrentPDFIndex;
    public static List<String> filePath = new ArrayList<>();
    private View_Rekap_Absen activity;
    private String finalPdfFile;

    private String pathForEveryPdfFile;

    private PDFCreationUtils() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);


    }
    public PDFCreationUtils(View_Rekap_Absen activity, List<Model_Data_RekapAbsen> currentPdfModels, int totalPDFModelSize, int currentPDFIndex) {
        this.activity = activity;
        this.mCurrentPDFModels = currentPdfModels;
        this.mCurrentPDFIndex = currentPDFIndex;
        getWH();
        createForEveryPDFFilePath();
        int sizeInPixel = activity.getResources().getDimensionPixelSize(R.dimen.dp_90) +
                activity.getResources().getDimensionPixelSize(R.dimen.dp_30);

        mPDFCreationView = LayoutInflater.from(activity).inflate(R.layout.activity_pdf, null, false);


        SECTOR = deviceHeight / sizeInPixel;
        TOTAL_PROGRESS_BAR = totalPDFModelSize / SECTOR;

        mPDFCreationRV = mPDFCreationView.findViewById(R.id.recycle_pdf);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
        mPDFCreationRV.setLayoutManager(mLayoutManager);


        pdfRootAdapter = new Adapter_RekapAbsen();

        document = new PdfDocument();
        pdfModelListSize = currentPdfModels.size();


    }

    private PDFCallback callback;
    public void createPDF(PDFCallback callback) {

        this.callback = callback;


        if (pdfModelListSize <= SECTOR) {
            NUMBER_OF_PAGE = 1;
            bitmapOfView = AppUtils.findViewBitmap(mCurrentPDFModels, deviceWidth, deviceHeight, pdfRootAdapter, mPDFCreationRV, mPDFCreationView);
            PdfBitmapCache.addBitmapToMemoryCache(NUMBER_OF_PAGE, bitmapOfView);
            createPdf();
        } else {

            NUMBER_OF_PAGE = pdfModelListSize / SECTOR;
            if (pdfModelListSize % SECTOR != 0) {
                NUMBER_OF_PAGE++;
            }
            Map<Integer,List<Model_Data_RekapAbsen>>listMap= createFinalData();
            for (int PAGE_INDEX = 1; PAGE_INDEX <= NUMBER_OF_PAGE; PAGE_INDEX++) {
                List<Model_Data_RekapAbsen> list = listMap.get(PAGE_INDEX);
                bitmapOfView = AppUtils.findViewBitmap(list, deviceWidth, deviceHeight, pdfRootAdapter, mPDFCreationRV, mPDFCreationView);
                PdfBitmapCache.addBitmapToMemoryCache(PAGE_INDEX, bitmapOfView);
            }
            createPdf();
        }
    }
    private Map<Integer, List<Model_Data_RekapAbsen>> createFinalData() {
        int START = 0;
        int END = SECTOR;
        Map<Integer, List<Model_Data_RekapAbsen>> map = new LinkedHashMap<>();
        int INDEX = 1;
        for (int i = 0; i < NUMBER_OF_PAGE; i++) {
            if (pdfModelListSize % SECTOR != 0) {
                if (i == NUMBER_OF_PAGE - 1) {
                    END = START + pdfModelListSize % SECTOR;
                }
            }
            List<Model_Data_RekapAbsen> list = mCurrentPDFModels.subList(START, END);
            START = END;
            END = SECTOR + END;
            map.put(INDEX, list);
            INDEX++;
        }
        return map;
    }
    private void createPdf() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (int PAGE_INDEX = 1; PAGE_INDEX <= NUMBER_OF_PAGE; PAGE_INDEX++) {

                    final Bitmap b = PdfBitmapCache.getBitmapFromMemCache(PAGE_INDEX);
                    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(b.getWidth(), b.getHeight(), PAGE_INDEX).create();
                    PdfDocument.Page page = document.startPage(pageInfo);
                    Canvas canvas = page.getCanvas();
                    Paint paint = new Paint();
                    paint.setColor(Color.parseColor("#ffffff"));
                    canvas.drawPaint(paint);
                    canvas.drawBitmap(b, 0, 0, null);
                    document.finishPage(page);

                    File filePath = new File(pathForEveryPdfFile);
                    try {
                        document.writeTo(new FileOutputStream(filePath));
                    } catch (IOException e) {
                        if (callback != null) {
                            if (e != null) {
                                callback.onError(e);
                            } else {
                                callback.onError(new Exception("IOException"));
                            }

                        }
                    }

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onProgress(progressCount++);
                        }
                    });

                    if (PAGE_INDEX == NUMBER_OF_PAGE) {
                        document.close();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.onCreateEveryPdfFile();
                            }
                        });
                    }
                }
            }
        });


    }

    public static int progressCount = 1;

    public void downloadAndCombinePDFs() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (mCurrentPDFIndex == 1) {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onComplete(pathForEveryPdfFile);
                            }
                        }
                    });

                } else {
                    try {

                        PDFMergerUtility ut = new PDFMergerUtility();
                        for (String s : filePath) {
                            ut.addSource(s);
                        }

                        final FileOutputStream fileOutputStream = new FileOutputStream(new File(createFinalPdfFilePath()));
                        try {
                            ut.setDestinationStream(fileOutputStream);
                            ut.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

                        } finally {
                            fileOutputStream.close();
                        }

                    } catch (Exception e) {

                    }
                    for (String s : filePath) {
                        new File(s).delete();
                    }

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (callback != null) {
                                callback.onComplete(finalPdfFile);
                            }
                        }
                    });
                }
            }
        }).start();
    }

    private void createForEveryPDFFilePath() {

        AppUtils appUtils = new AppUtils();
        pathForEveryPdfFile = appUtils.createPDFPath();
        filePath.add(pathForEveryPdfFile);
    }

    private String createFinalPdfFilePath() {
        AppUtils appUtils = new AppUtils();
        finalPdfFile = appUtils.createPDFPath();
        return finalPdfFile;
    }

    private void getWH() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        deviceHeight = displayMetrics.heightPixels;
        deviceWidth = displayMetrics.widthPixels;
    }


    public interface PDFCallback {

        void onProgress(int progress);

        void onCreateEveryPdfFile();

        void onComplete(String filePath);

        void onError(Exception e);
    }

}
