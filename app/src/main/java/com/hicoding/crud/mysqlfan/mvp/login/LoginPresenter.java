package com.hicoding.crud.mysqlfan.mvp.login;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.hicoding.crud.mysqlfan.api.EndPoint;
import com.hicoding.crud.mysqlfan.model.LoginResponse;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.MainView mainView;

    public LoginPresenter(LoginContract.MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void login(String username, String password) {
        mainView.onStartProgress();
        AndroidNetworking.post(EndPoint.LOGIN)
                .setPriority(Priority.LOW)
                .addBodyParameter("username", username)
                .addBodyParameter("password", password)
                .build()
                .getAsObject(LoginResponse.class, new ParsedRequestListener<LoginResponse>() {
                    @Override
                    public void onResponse(LoginResponse response) {
                        mainView.onStopProgress();
                        if (response.getSuccess()) {
                            mainView.onSuccessLogin(response.getMessage(), response.getLevel());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        mainView.onStopProgress();
                        mainView.onFailed(anError.getErrorDetail());
                    }
                });
    }
}
