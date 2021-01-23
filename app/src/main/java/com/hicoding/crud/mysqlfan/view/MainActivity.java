package com.hicoding.crud.mysqlfan.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.hicoding.crud.mysqlfan.R;
import com.hicoding.crud.mysqlfan.view.dosen.DosenActivity;
import com.hicoding.crud.mysqlfan.view.mahasiswa.MahasiswaActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button_data_dosen)
    MaterialButton buttonDosen;

    @BindView(R.id.button_data_mahasiswa)
    MaterialButton buttonMahasiswa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bind view
        ButterKnife.bind(this);

        buttonDosen.setOnClickListener(view -> startActivity(new Intent(this, DosenActivity.class)));
        buttonMahasiswa.setOnClickListener(view -> startActivity(new Intent(this, MahasiswaActivity.class)));
    }
}
