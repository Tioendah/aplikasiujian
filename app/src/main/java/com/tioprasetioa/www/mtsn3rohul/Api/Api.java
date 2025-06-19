package com.tioprasetioa.www.mtsn3rohul.Api;

import com.tioprasetioa.www.mtsn3rohul.Generic.BaseResponse;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_Katalog;
import com.tioprasetioa.www.mtsn3rohul.Model.Model_statuspdate.Response_statusupdate;
import com.tioprasetioa.www.mtsn3rohul.Model.SiswaCurang.Response;
import com.tioprasetioa.www.mtsn3rohul.Model.ekskul.Item;
import com.tioprasetioa.www.mtsn3rohul.Model.listsiswacurang.Response_SiswaCurang;
import com.tioprasetioa.www.mtsn3rohul.Model.notifikasifcm.PushNotification;
import com.tioprasetioa.www.mtsn3rohul.Model.pengunjung.Response_pengunjung;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Data;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Response_MapelAdmin;
import com.tioprasetioa.www.mtsn3rohul.Model.ujian.Response_MapelUjian;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Berita;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Create_Member;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_DetailBuku;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_DetailListLoans;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_DetailListLoanss;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Galeri_Home;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_ImageSlider;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Kategori;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_ListLoan;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Login;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Modul;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_PerangkatPBM;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_ProfilGuru;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_TampilData;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_UpdateLoan;
import com.tioprasetioa.www.mtsn3rohul.Respon.Response_Youtube;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {


    @GET("webservis.php")
    Call<Response_ImageSlider>image_sliderCall();

    @GET("recyclehome.php")
    Call<Response_Galeri_Home>galeri_home();

    @GET("api_websekolah/api_berita.php")
    Call<Response_Berita>berita_metode();

    @FormUrlEncoded
    @POST("Login_Api.php")
    Call<Response_Login>LoginResponse(
            @Field("member_id2") String member_id2,
            @Field("mpasswd2") String mpasswd2
    );

    @FormUrlEncoded
    @POST("get_loans.php")
    Call<BaseResponse<Response_TampilData>>MenampilkanData(
            @Field("member_id")String member_id
    );
    @GET("search_library.php")
    Call<List<Model_Katalog>>getKatalog(
            @Query("item_type") String item_type,
            @Query("key") String key

    );
    @FormUrlEncoded
    @POST("DetailBuku2.php")
    Call<BaseResponse<Response_DetailBuku>>getdetail(
            @Field("biblio_id") String biblio_id
    );

    @GET("ProfilGuru.php")
    Call<Response_ProfilGuru>profilguru();

    @GET("webservis_modul.php")
    Call<Response_Modul>metodModul();

    @GET("webservis_perangkatpbm.php")
    Call<Response_PerangkatPBM>metodPbm();

    @GET("webservis_perangkatpbmviii.php")
    Call<Response_PerangkatPBM>metodPbmviii();

    @GET("webservis_perangkatpbmix.php")
    Call<Response_PerangkatPBM>metodPbmix();

    @GET("webservisYoutube.php")
    Call<Response_Youtube>metodyoutube();

    @GET("kategori/Kategori99.php")
    Call<Response_Kategori>kategori99();

    @GET("kategori/Kategori100.php")
    Call<Response_Kategori>kategori100();

    @GET("kategori/Kategori200.php")
    Call<Response_Kategori>kategori200();

    @GET("kategori/Kategori300.php")
    Call<Response_Kategori>kategori300();

    @GET("kategori/Kategori400.php")
    Call<Response_Kategori>kategori400();

    @GET("kategori/Kategori500.php")
    Call<Response_Kategori>kategori500();

    @GET("kategori/Kategori600.php")
    Call<Response_Kategori>kategori600();

    @GET("kategori/Kategori700.php")
    Call<Response_Kategori>kategori700();

    @GET("kategori/Kategori800.php")
    Call<Response_Kategori>kategori800();

    @GET("kategori/Kategori900.php")
    Call<Response_Kategori>kategori900();

    @GET("ListLoan.php")
    Call<Response_ListLoan>LIST_LOAN_CALL();

    @POST("DetailLisLoan.php")
    @FormUrlEncoded
    Call<BaseResponse<Response_DetailListLoans>>getListLoans(@Field("member_id")String member_id);

    @POST("DetailLisLoan2.php")
    @FormUrlEncoded
    Call<BaseResponse<Response_DetailListLoanss>>getListLoanss(
            @Field("member_id")String member_id);

    @FormUrlEncoded
    @POST("backbook.php")
    Call<Response_UpdateLoan>update(
            @Field("loan_id")String loan_id,
            @Field("is_return")String is_return,
            @Field("return_date")String return_date);

    @FormUrlEncoded
    @POST("create_Loan.php")
    Call<Response_ListLoan>createdata(
            @Field("item_code") String item_code,
            @Field("member_id") String member_id,
            @Field("loan_date") String loan_date,
            @Field("return_date") String return_date,
            @Field("is_return") String is_return,
            @Field("mapel") String mapel,
            @Field("due_date") String due_date
    );
    @FormUrlEncoded
    @POST("create_member.php")
    Call<Response_Create_Member>create_member(
    @Field("member_id") String member_id,
    @Field("member_id2") String member_id2,
    @Field("member_name") String member_name,
    @Field("gender") String gender,
    @Field("birth_date") String birth_date,
    @Field("member_type_id") String member_type_id,
    @Field("inst_name") String inst_name,
    @Field("pin") String pin,
    @Field("member_phone") String member_phone,
    @Field("register_date") String register_date,
    @Field("expire_date") String expire_date,
    @Field("mpasswd") String mpasswd,
    @Field("mpasswd2") String mpasswd2
    );

    @POST("api_ujian.php")
    @FormUrlEncoded
    Call<Response_MapelUjian<Data>> getMapelUjian(@Field("kelas") String kelas);

    @FormUrlEncoded
    @POST("api_updatestatusujian.php")
    Call<Response_statusupdate>updateStatusUjian(
            @Field("id")int id,
            @Field("statusujian")int status);

    @FormUrlEncoded
    @POST("api_updatestatussoal.php")
    Call<Response_statusupdate>updateStatusSoal(
            @Field("id")int id,
            @Field("status_soal")int status_soal);

    @GET("api_pengunjung.php")
    Call<Response_pengunjung>getData();

    @GET("api_ekskul.php")
    Call<List<Item>> getItems();

    @FormUrlEncoded
    @POST("api_websekolah/update_linkberita.php")
    Call<Response_Berita>update_link(
            @Field("tulisan_id")int tulisan_id,
            @Field("url")String url);

    @GET("api_admin/api_mapeladmin.php")
    Call<Response_MapelAdmin>getDataMapelAdmin();

    @FormUrlEncoded
    @POST("api_admin/api_statusmapel.php")
    Call<Response_MapelUjian>updatemapelujian(
            @Field("mapel") String mapel,
            @Field("status") int status
    );
    @FormUrlEncoded
    @POST("api_listsiswacurang.php")
    Call<Response_SiswaCurang>postData(
            @Field("nama") String nama,
            @Field("kelas") String kelas,
            @Field("mapel") String mapel,
            @Field("guru") String guru,
            @Field("waktu") String waktu,
            @Field("kategori") String kategori,
            @Field("konsekuensi") String konsekuensi

    );
    @Headers({
            "Content-Type: application/json"
    })
    @POST("v1/projects/peronpintar/messages:send")
    Call<PushNotification> sendNotification(
            @Body PushNotification pushNotification,
            @retrofit2.http.Header("Authorization") String authorizationHeader
    );

    @GET("daftarsiswacurang.php")
    Call<Response>getDataSiswaCurang();



}

