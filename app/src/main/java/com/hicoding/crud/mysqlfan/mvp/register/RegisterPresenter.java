package com.hicoding.crud.mysqlfan.mvp.register;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.hicoding.crud.mysqlfan.api.EndPoint;
import com.hicoding.crud.mysqlfan.model.RegisterResponse;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.MainView mainView;

    public RegisterPresenter(RegisterContract.MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void register(String username, String password, String level) {
        mainView.onStartProgress();
        AndroidNetworking.post(EndPoint.REGISTER)
                .setPriority(Priority.LOW)
                .addBodyParameter("username", username)
                .addBodyParameter("password", password)
                .addBodyParameter("level", level)
                .build()
                .getAsObject(RegisterResponse.class, new ParsedRequestListener<RegisterResponse>() {
                    @Override
                    public void onResponse(RegisterResponse response) {
                        mainView.onStopProgress();
                        if(response.getSuccess()){
                            mainView.onSuccessRegister(response.getMessage());
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
