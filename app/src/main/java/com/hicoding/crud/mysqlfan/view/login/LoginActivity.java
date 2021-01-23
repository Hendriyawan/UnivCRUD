package com.hicoding.crud.mysqlfan.view.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.hicoding.crud.mysqlfan.R;
import com.hicoding.crud.mysqlfan.mvp.login.LoginContract;
import com.hicoding.crud.mysqlfan.mvp.login.LoginPresenter;
import com.hicoding.crud.mysqlfan.view.dosen.DosenActivity;
import com.hicoding.crud.mysqlfan.view.mahasiswa.MahasiswaActivity;
import com.hicoding.crud.mysqlfan.view.register.RegisterActivity;
import com.iamhabib.easy_preference.EasyPreference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginContract.MainView {
    @BindView(R.id.edit_username)
    AppCompatEditText editUsername;
    @BindView(R.id.edit_password)
    AppCompatEditText editPassword;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        presenter = new LoginPresenter(this);
    }

    @OnClick(R.id.button_login)
    public void login() {
        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        presenter.login(username, password);
    }

    @OnClick(R.id.text_register)
    public void register(){
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @Override
    public void onStartProgress() {

    }

    @Override
    public void onStopProgress() {

    }

    @Override
    public void onSuccessLogin(String message, String level) {
        EasyPreference.with(this)
                .addString("level", level)
                .save();

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        if (level.equals("admin")) {
            startActivity(new Intent(this, DosenActivity.class));
            finish();
        } else {
            startActivity(new Intent(this, MahasiswaActivity.class));
            finish();
        }
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}