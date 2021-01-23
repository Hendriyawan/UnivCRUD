package com.hicoding.crud.mysqlfan.mvp.mahasiswa;

import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.hicoding.crud.mysqlfan.api.EndPoint;
import com.hicoding.crud.mysqlfan.model.MahasiswaResponse;
import com.hicoding.crud.mysqlfan.model.NidnResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class MahasiswaPresenter implements MahasiswaContract.Presenter {
    private MahasiswaContract.MainView mainView;

    public MahasiswaPresenter(MahasiswaContract.MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void loadNidn() {
        mainView.onStartProgress();
        AndroidNetworking.get(EndPoint.LOAD_NIDN)
                .setPriority(Priority.LOW)
                .build().getAsObject(NidnResponse.class, new ParsedRequestListener<NidnResponse>() {
            @Override
            public void onResponse(NidnResponse response) {
                mainView.onNidnLoaded(response.getNidn());
            }

            @Override
            public void onError(ANError anError) {

            }
        });
    }

    @Override
    public void loadData() {
        mainView.onStartProgress();
        AndroidNetworking.get(EndPoint.READ_MAHASISWA)
                .setPriority(Priority.LOW)
                .build()
                .getAsObject(MahasiswaResponse.class, new ParsedRequestListener<MahasiswaResponse>() {
                    @Override
                    public void onResponse(MahasiswaResponse response) {
                        mainView.onStopProgress();
                        if (response.getSuccess()) {
                            mainView.onMahasiswaLoaded(response.getMahasiswa());
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
    public void addData(String npm, String nama, String jenis_kelamin, String nidn, String photo) {
        mainView.onStartProgress();
        AndroidNetworking.post(EndPoint.ADD_MAHASISWA)
                .addBodyParameter("id_mahasiwa", "")
                .addBodyParameter("npm", npm)
                .addBodyParameter("nama", nama)
                .addBodyParameter("jenis_kelamin", jenis_kelamin)
                .addBodyParameter("nidn", nidn)
                .addBodyParameter("photo", photo)
                .setPriority(Priority.MEDIUM)
                /*.build().getAsString(new StringRequestListener() {
            @Override
            public void onResponse(String response) {
                Log.d("DEBUG", response);
            }

            @Override
            public void onError(ANError anError) {

            }
        });*/

                .build().getAsJSONObject(new JSONObjectRequestListener() {
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
    public void updateData(String id, String npm, String nama, String jenis_kelamin, String nidn, String photo) {
        mainView.onStartProgress();
        AndroidNetworking.post(EndPoint.UPDATE_MAHASISWA)
                .addBodyParameter("id_mahasiswa", id)
                .addBodyParameter("npm", npm)
                .addBodyParameter("nama", nama)
                .addBodyParameter("jenis_kelamin", jenis_kelamin)
                .addBodyParameter("nidn", nidn)
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
        AndroidNetworking.post(EndPoint.DELETE_MAHASISWA)
                .addBodyParameter("id_mahasiswa", id)
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
        AndroidNetworking.get(EndPoint.READ_MAHASISWA)
                .addQueryParameter("nidn", nidn)
                .setPriority(Priority.MEDIUM)
                .build().getAsObject(MahasiswaResponse.class, new ParsedRequestListener<MahasiswaResponse>() {
            @Override
            public void onResponse(MahasiswaResponse response) {
                mainView.onStopProgress();
                if (response.getSuccess() && response.getMahasiswa().size() > 0) {
                    mainView.onMahasiswaLoaded(response.getMahasiswa());
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
