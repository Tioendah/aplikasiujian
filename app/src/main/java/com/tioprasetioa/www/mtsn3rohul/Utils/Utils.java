package com.tioprasetioa.www.mtsn3rohul.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

public class Utils extends AppCompatActivity {
    public static final String KEY_UJIAN = "Login2025";
    public static final String KEY_VALUE = "TIOPRASETIO36";

    public interface VolleyCallback {
        void onSuccess(String result);

    }

    public interface VolleyDataString {
        void onError(String errorMesage);
        void onSuccess(String result);
    }
    public interface DeleteCallback {
        void onSuccess();
        void onFailure();
    }

    RequestQueue requestQueue;
    StringRequest stringRequest;

    public static void Alert(Context context, String title, String string) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder.setMessage("Lihat file?\n" + string);
        alertDialogBuilder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent();
                i.setAction(DownloadManager.ACTION_VIEW_DOWNLOADS); // Intent untuk membuka directory download
                context.startActivity(i);
            }
        });
        alertDialogBuilder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialogBuilder.show();
    }

    public static void tapview(Context context, int i, String title, String desc) {
        MaterialTapTargetPrompt.Builder builder = new MaterialTapTargetPrompt.Builder((Activity) context);
        builder.setTarget(i);
        builder.setPrimaryText(title);
        builder.setSecondaryText(desc);
        builder.show();
    }

    public static void Glide(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .fitCenter()
                .into(view);
    }

    public static void Toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void Volley(Context context, String url, TextView textView, String ObjekKey, VolleyCallback callback) {
        requestQueue = Volley.newRequestQueue(context);
        ArrayList<HashMap<String, String>> listdata = new ArrayList<>();
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("tambah");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<>();
                        map.put(ObjekKey, json.getString(ObjekKey));
                        listdata.add(map);
                    }
                    if (!listdata.isEmpty()) {
                        textView.setText(listdata.get(0).get(ObjekKey));
                    } else {
                        textView.setText("Data tidak tersedia");
                    }
                    callback.onSuccess(textView.getText().toString());
                } catch (JSONException e) {
                    textView.setText("Kesalahan parsing data");
                    Log.e("Volley", "JSON Parsing error: " + (e.getMessage() != null ? e.getMessage() : "Unknown error"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("Kesalahan jaringan atau server. Silakan coba lagi.");
                Log.e("Volley", "Volley error: " + (error != null && error.getMessage() != null ? error.getMessage() : "Unknown error"));

            }
        });

        requestQueue.add(stringRequest);
    }

    public void VolleyString(Context context, String Url, String ObjekKey, VolleyDataString volleyDataString) {
        requestQueue = Volley.newRequestQueue(context);
        ArrayList<HashMap<String, String>> listdata = new ArrayList<>();
        stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("tambah");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> map = new HashMap<>();
                        map.put(ObjekKey, json.getString(ObjekKey));
                        listdata.add(map);
                    }
                    if (!listdata.isEmpty()) {
                        String getDataFinal = listdata.get(0).get(ObjekKey);
                        volleyDataString.onSuccess(getDataFinal);
                    } else {
                        volleyDataString.onSuccess(null);
                    }
                } catch (JSONException e) {

                    Log.e("Volley", "Json Parsing Error: " + (e.getMessage() != null ? e.getMessage() : "Unknown error"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyDataString.onError("Gangguan Jaringan");
//                SweetAlert.AlertOK("Gangguan Jaringan!","Silahkan klik refresh");
                Log("ErrorVoley", volleyError != null && volleyError.getMessage() != null ? volleyError.getMessage() : "Unknown error");
            }
        });
        requestQueue.add(stringRequest);
    }

    public static void Log(String tag, String message) {
        Log.e(tag, message != null ? message : "No message provided");
    }

    public static String Date() {
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", new Locale("id", "ID")); // Mengambil nama hari dalam bahasa Indonesia
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()); // Mengambil waktu dalam format HH:mm:ss

        int tahun = calendar1.get(Calendar.YEAR);
        int bulan = calendar1.get(Calendar.MONTH);
        Date tanggal = calendar1.getTime();
        int bulana = bulan + 1;
        String stanggal = simpleDateFormat.format(tanggal);
        String Shari = dayFormat.format(tanggal);
        String currentTime = timeFormat.format(tanggal); // Mengambil waktu saat ini

        String date = Shari + ", " + stanggal + "-" + bulana + "-" + tahun + " " + currentTime;
        return date;
    }
    public void deleteAllData(Context context, String url, String successMessage, String failMessage, DeleteCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show();
                        if (callback != null) {
                            callback.onSuccess(); // Panggil callback jika sukses
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, failMessage, Toast.LENGTH_SHORT).show();
                        if (callback != null) {
                            callback.onFailure(); // Panggil callback jika gagal
                        }
                    }
                });

        queue.add(stringRequest);
    }


}
