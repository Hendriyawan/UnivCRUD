package com.hicoding.crud.mysqlfan.mvp.register;

public class RegisterContract {

    public interface Presenter {
        void register(String username, String password, String level);
    }

    public interface MainView {
        void onStartProgress();

        void onStopProgress();

        void onSuccessRegister(String message);

        void onFailed(String message);
    }
}
