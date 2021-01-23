package com.hicoding.crud.mysqlfan.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.hicoding.crud.mysqlfan.R;
import com.hicoding.crud.mysqlfan.view.dosen.DosenActivity;
import com.hicoding.crud.mysqlfan.view.login.LoginActivity;
import com.hicoding.crud.mysqlfan.view.mahasiswa.MahasiswaActivity;
import com.iamhabib.easy_preference.EasyPreference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            String level = EasyPreference.with(this).getString("level", "");
            if (!level.isEmpty()) {
                if (level.equals("admin")) {
                    startActivity(new Intent(this, DosenActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(this, MahasiswaActivity.class));
                    finish();
                }
            } else {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }
}