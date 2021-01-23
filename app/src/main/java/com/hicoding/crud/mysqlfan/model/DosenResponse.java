package com.hicoding.crud.mysqlfan.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DosenResponse {
    @SerializedName("success")
    private Boolean success;
    @SerializedName("data")
    private List<Dosen> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Dosen> getData() {
        return data;
    }

    public void setData(List<Dosen> data) {
        this.data = data;
    }
}
