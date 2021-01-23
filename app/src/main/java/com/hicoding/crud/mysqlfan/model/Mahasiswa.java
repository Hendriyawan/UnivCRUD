package com.hicoding.crud.mysqlfan.model;

import com.google.gson.annotations.SerializedName;

public class Mahasiswa {
    @SerializedName("id_mahasiswa")
    private String id_mahasiswa;
    @SerializedName("npm")
    private String npm;
    @SerializedName("nama")
    private String nama;
    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;
    @SerializedName("nidn")
    private String nidn;
    @SerializedName("photo")
    private String photo;

    public String getId_mahasiswa() {
        return id_mahasiswa;
    }

    public void setId_mahasiswa(String id_mahasiswa) {
        this.id_mahasiswa = id_mahasiswa;
    }

    public String getNpm() {
        return npm;
    }

    public void setNpm(String npm) {
        this.npm = npm;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
