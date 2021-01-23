package com.hicoding.crud.mysqlfan.mvp.login;

public class LoginContract {

    public interface Presenter {
        void login(String username, String password);
    }

    public interface MainView {
        void onStartProgress();

        void onStopProgress();

        void onSuccessLogin(String message, String level);

        void onFailed(String message);

    }
}
