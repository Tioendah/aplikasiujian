package com.tioprasetioa.www.mtsn3rohul.Model.Data;

import java.util.ArrayList;
import java.util.Collection;

public class Model_Button_Kategori {
    public static String [] kategori = {
            "Semua",
            "Karya Umum",
            "Filsafat",
            "Agama",
            "Ilmu Sosial",
            "Bahasa",
            "Ilmu Murni",
            "Ilmu Terapan",
            "Kesenian dan Hiburan",
            "Kesusastraan",
            "Geografi dan Sejarah"

    };
    public static Collection<? extends Parcel_Btn_Kategori> menampilkandata(){
        ArrayList<Parcel_Btn_Kategori>list = new ArrayList<>();
        for (int i =0; i<kategori.length;i++){
            Parcel_Btn_Kategori parcel_btn_kategori = new Parcel_Btn_Kategori();
            parcel_btn_kategori.setKategori(kategori[i]);
            list.add(parcel_btn_kategori);
        }
        return list;
    }
}
