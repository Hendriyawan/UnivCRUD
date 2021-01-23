package com.hicoding.crud.mysqlfan.mvp.dosen;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.hicoding.crud.mysqlfan.api.EndPoint;
import com.hicoding.crud.mysqlfan.model.DosenResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class DosenPresenter implements DosenContract.Presenter {

    private DosenContract.MainView mainView;

    public DosenPresenter(DosenContract.MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void loadData() {
        mainView.onStartProgress();
        AndroidNetworking.get(EndPoint.READ_DOSEN)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(DosenResponse.class, new ParsedRequestListener<DosenResponse>() {
                    @Override
                    public void onResponse(DosenResponse response) {
                        mainView.onStopProgress();
                        if (response.getSuccess()) {
                            mainView.onDosenLoaded(response.getData());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mainView.onStopProgress();
                        int errorCode = anError.getErrorCode();
                        String message = anError.getErrorDetail();
                        String error = errorCode + " " + message;
                        mainView.onFailed(error);
                    }
                });
    }

    @Override
    public void addData(String nidn, String nama, String tgl_lahir, String photo) {
        mainView.onStartProgress();
        AndroidNetworking.post(EndPoint.ADD_DOSEN)
                .addBodyParameter("id_dosen", "")
                .addBodyParameter("nidn", nidn)
                .addBodyParameter("nama", nama)
                .addBodyParameter("tanggal_lahir", tgl_lahir)
                .addBodyParameter("photo", photo)
                .setPriority(Priority.MEDIUM).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mainView.onStopProgress();
                        Log.d("DEBUG", "response " + response);
                        try {
                            boolean success = response.getBoolean("success");
                            String message = response.getString("message");
                            if (success) {
                                mainView.onDataAdded(message);
                            } else {
                                mainView.onFailed(message);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mainView.onStopProgress();
                        Log.d("DEBUG", "error " + anError);
                        mainView.onFailed("error " + anError);
                    }
                });
    }

    @Override
    public void updateData(String id, String nama, String nidn, String tanggal_lahir, String photo) {
        mainView.onStartProgress();
        AndroidNetworking.post(EndPoint.UPDATE_DOSEN)
                .addBodyParameter("id_dosen", id)
                .addBodyParameter("nidn", nidn)
                .addBodyParameter("nama", nama)
                .addBodyParameter("tanggal_lahir", tanggal_lahir)
                .addBodyParameter("photo", photo)
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                mainView.onStopProgress();
                Log.d("DEBUG", "response " + response);
                try {

                    boolean success = response.getBoolean("success");
                    String message = response.getString("message");
                    if (success) {
                        //updated
                        mainView.onDataUpdated(message);
                    } else {
                        mainView.onFailed(message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                mainView.onStopProgress();
                Log.d("DEBUG", "error " + anError);
                mainView.onFailed(anError.getErrorDetail());
            }
        });
    }

    @Override
    public void delete(String id) {
        mainView.onStartProgress();
        AndroidNetworking.post(EndPoint.DELETE_DOSEN)
                .addBodyParameter("id_dosen", id)
                .setPriority(Priority.MEDIUM)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                mainView.onStopProgress();
                Log.d("DEBUG", "response " + response);
                try {
                    boolean success = response.getBoolean("success");
                    String message = response.getString("message");
                    if (success) {
                        mainView.onDataDeleted(message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(ANError anError) {
                mainView.onStopProgress();
                Log.d("DEBUG", "error " + anError);
                mainView.onFailed(anError.getErrorDetail());
            }
        });
    }

    @Override
    public void search(String nidn) {
        mainView.onStartProgress();
        AndroidNetworking.get(EndPoint.READ_DOSEN)
                .addQueryParameter("nidn", nidn)
                .setPriority(Priority.MEDIUM).build()
                .getAsObject(DosenResponse.class, new ParsedRequestListener<DosenResponse>() {
                    @Override
                    public void onResponse(DosenResponse response) {
                        mainView.onStopProgress();
                        if (response.getSuccess()) {
                            mainView.onDosenLoaded(response.getData());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mainView.onStopProgress();
                        int errorCode = anError.getErrorCode();
                        String message = anError.getErrorDetail();
                        String error = errorCode + " " + message;
                        mainView.onFailed(error);
                    }
                });
    }
}