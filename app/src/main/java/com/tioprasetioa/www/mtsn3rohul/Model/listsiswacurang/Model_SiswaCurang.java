package com.tioprasetioa.www.mtsn3rohul.Model.listsiswacurang;

public class Model_SiswaCurang {
    private String nama;
    private String kelas;
    private String mapel;
    private String guru;
    private String waktu;
    private String kategori;
    private String konsekuensi;

    public Model_SiswaCurang(String nama, String kelas, String mapel, String guru, String waktu, String kategori, String konsekuensi) {
        this.nama = nama;
        this.kelas = kelas;
        this.mapel = mapel;
        this.guru = guru;
        this.waktu = waktu;
        this.kategori = kategori;
        this.konsekuensi = konsekuensi;
    }

    // Getters and setters
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getMapel() {
        return mapel;
    }

    public void setMapel(String mapel) {
        this.mapel = mapel;
    }

    public String getGuru() {
        return guru;
    }

    public void setGuru(String guru) {
        this.guru = guru;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getKonsekuensi() {
        return konsekuensi;
    }

    public void setKonsekuensi(String konsekuensi) {
        this.konsekuensi = konsekuensi;
    }
}
