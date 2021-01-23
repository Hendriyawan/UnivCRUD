package com.hicoding.crud.mysqlfan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NidnResponse {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    private List<Nidn> nidn;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Nidn> getNidn() {
        return nidn;
    }

    public void setNidn(List<Nidn> nidn) {
        this.nidn = nidn;
    }
}
