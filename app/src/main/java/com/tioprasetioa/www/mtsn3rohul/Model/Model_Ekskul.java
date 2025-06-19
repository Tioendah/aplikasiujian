package com.tioprasetioa.www.mtsn3rohul.Model;

public class Model_Ekskul {
    private String Nama;
    private String Image;
    private String ImageS;
    private String Judul;
    private String Desc;
    private String Jadwal;

    public Model_Ekskul(String nama, String image, String imageS, String judul, String desc, String jadwal) {
        Nama = nama;
        Image = image;
        ImageS = imageS;
        Judul = judul;
        Desc = desc;
        Jadwal = jadwal;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getImageS() {
        return ImageS;
    }

    public void setImageS(String imageS) {
        ImageS = imageS;
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getJadwal() {
        return Jadwal;
    }

    public void setJadwal(String jadwal) {
        Jadwal = jadwal;
    }
}