package com.hicoding.crud.mysqlfan.view.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;

import com.hicoding.crud.mysqlfan.R;
import com.hicoding.crud.mysqlfan.mvp.register.RegisterContract;
import com.hicoding.crud.mysqlfan.mvp.register.RegisterPresenter;
import com.hicoding.crud.mysqlfan.view.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.MainView {
    @BindView(R.id.spinner_register)
    AppCompatSpinner spinner;
    @BindView(R.id.edit_username)
    AppCompatEditText editUsername;
    @BindView(R.id.edit_password)
    AppCompatEditText editPassword;

    private String level;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        initViews();
    }

    @OnClick(R.id.button_register)
    public void clickRegister() {

        String username = editUsername.getText().toString();
        String password = editPassword.getText().toString();
        presenter.register(username, password, level);
    }

    @OnClick(R.id.text_login)
    public void login(){
        startActivity(new Intent(this, LoginActivity.class));
    }

    private void initViews() {
        initSpinner();

        presenter = new RegisterPresenter(this);
    }

    private void initSpinner() {
        String[] data = {"admin", "user"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, data));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                level = data[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onStartProgress() {

    }

    @Override
    public void onStopProgress() {

    }

    @Override
    public void onSuccessRegister(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}