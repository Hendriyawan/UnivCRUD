package com.hicoding.crud.mysqlfan.model;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class Nidn {
    @SerializedName("nidn") private String nidn;

    public String getNidn() {
        return nidn;
    }

    public void setNidn(String nidn) {
        this.nidn = nidn;
    }

    @NotNull
    @Override
    public String toString() {
        return nidn;
    }
}
