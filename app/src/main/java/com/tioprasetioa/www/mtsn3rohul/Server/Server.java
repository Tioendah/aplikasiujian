package com.tioprasetioa.www.mtsn3rohul.Server;

import static com.tioprasetioa.www.mtsn3rohul.Utils.Constans.KEY_BaseUrl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class Server {
    private static Retrofit retrofitfirebase = null;
    private static final String baseurl = "https://mtsn3rokanhulu.sch.id/api/";//http://192.168.43.61/peronpintar/”
    private static Retrofit retrofit;
    public static Retrofit koneksiretrofit(){
        Gson gson = new GsonBuilder().setLenient().create();
        if (retrofit==null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseurl)
                    .client(new OkHttpClient.Builder().addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }return retrofit;
    }
    public static Retrofit getFirebase(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        retrofitfirebase = new Retrofit.Builder()
                .baseUrl(KEY_BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        return retrofitfirebase;
    }

}
