package com.hicoding.crud.mysqlfan.view.mahasiswa;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hicoding.crud.mysqlfan.R;
import com.hicoding.crud.mysqlfan.Utils;
import com.hicoding.crud.mysqlfan.adapter.MahasiswaAdapter;
import com.hicoding.crud.mysqlfan.model.Mahasiswa;
import com.hicoding.crud.mysqlfan.model.Nidn;
import com.hicoding.crud.mysqlfan.mvp.mahasiswa.MahasiswaContract;
import com.hicoding.crud.mysqlfan.mvp.mahasiswa.MahasiswaPresenter;
import com.hicoding.crud.mysqlfan.view.login.LoginActivity;
import com.iamhabib.easy_preference.EasyPreference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MahasiswaActivity extends AppCompatActivity implements MahasiswaContract.MainView {
    public static final int REQUEST_CODE_GALLERY = 999;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view_mahasiswa)
    RecyclerView recyclerView;
    @BindView(R.id.fab_add_data_mahasiswa)
    FloatingActionButton fabAddMahasiswa;
    @BindView(R.id.edit_text_mahasiswa)
    AppCompatEditText editTextSearchMahasiswa;
    @BindView(R.id.image_view_search)
    AppCompatImageView imageViewSearch;
    private MahasiswaPresenter presenter;
    private AppCompatImageView photo;
    private ArrayAdapter<Nidn> spinnerAdapter;
    private String jenisKelamin;
    private List<Nidn> listNidn;
    private String inputNidn;
    private String nidn;
    private Boolean nidnEmpty = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahasiswa);
        //bind view
        ButterKnife.bind(this);

        presenter = new MahasiswaPresenter(this);
        //load data mahasiswa
        presenter.loadData();
        presenter.loadNidn();

        //floating action button add data
        fabAddMahasiswa.setOnClickListener(view -> showDialogInputMahasiswa(false, "", "", "", "", ""));


        editTextSearchMahasiswa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nidnEmpty = s.toString().isEmpty();
                nidn = s.toString();
            }
        });

        imageViewSearch.setOnClickListener(v -> {
            if (!nidnEmpty) {
                presenter.search(nidn);
            } else {
                Toast.makeText(this, "Masukan NIDN untuk pencarian", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @OnClick(R.id.logout)
    public void logout() {
        EasyPreference.with(this)
                .remove("level").save();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(this, "Dont have access to File Location", Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
            if (data != null) {
                Uri imageUri = data.getData();
                CropImage.activity(imageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(this);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                if (result != null) {
                    Uri resultUri = result.getUri();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), resultUri);
                        photo.setImageBitmap(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                assert result != null;
                result.getError().printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStartProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStopProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMahasiswaLoaded(List<Mahasiswa> data) {
        initRecyclerView(data);
    }

    @Override
    public void onNidnLoaded(List<Nidn> nidns) {
        listNidn = nidns;
        spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listNidn);

    }

    @Override
    public void onDataAdded(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        presenter.loadData();
        presenter.loadNidn();
    }

    @Override
    public void onDataUpdated(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        presenter.loadData();
        presenter.loadNidn();
    }

    @Override
    public void onDataDeleted(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        presenter.loadData();
        presenter.loadNidn();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    private void initRecyclerView(List<Mahasiswa> mahasiswaList) {
        if(mahasiswaList.size() >0){
            MahasiswaAdapter mahasiswaAdapter = new MahasiswaAdapter(this, mahasiswaList);
            mahasiswaAdapter.setOnMahasiswaClick(mahasiswa -> {
                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Pilih Aksi");
                dialog.setItems(items, ((dialogInterface, i) -> {
                    if (i == 0) {
                        //showDialogInputDosen(true, dosen.getId_dosen(), dosen.getNama(), dosen.getNidn(), dosen.getTanggal_lahir());
                        showDialogInputMahasiswa(true, mahasiswa.getId_mahasiswa(), mahasiswa.getNpm(), mahasiswa.getNama(), mahasiswa.getNidn(), mahasiswa.getPhoto());
                    }

                    if (i == 1) {
                        presenter.delete(mahasiswa.getId_mahasiswa());
                    }
                }));
                dialog.show();
            });

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(mahasiswaAdapter);
        } else {
            Toast.makeText(this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    //show dialog input data mahasiswa
    private void showDialogInputMahasiswa(boolean isUpdate, String id_mahasiswa, String npm, String nama, String nidn, String photo_url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View contentView = getLayoutInflater().inflate(R.layout.layout_dialog_input_data_mahasiswa, null, false);
        builder.setView(contentView);
        Dialog dialog = builder.create();

        photo = contentView.findViewById(R.id.image_view_photo);
        AppCompatEditText editNpm = contentView.findViewById(R.id.edit_npm);
        AppCompatEditText editNama = contentView.findViewById(R.id.edit_nama);
        AppCompatEditText editNidn = contentView.findViewById(R.id.edit_nidn);

        if (isUpdate) {
            Glide.with(this).load(photo_url).into(photo);
        }

        editNpm.setText(npm);
        editNama.setText(nama);
        editNidn.setText(nidn);

        photo.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
        });

        //Spinner Jenis Kelamin
        String[] gender = {"L", "P"};
        AppCompatSpinner spinnerJenisKelamin = contentView.findViewById(R.id.spinner_jenis_kelamin);
        spinnerJenisKelamin.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gender));
        spinnerJenisKelamin.setSelection(0);
        spinnerJenisKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jenisKelamin = gender[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Spinner NIDN
        AppCompatSpinner spinnerNidn = contentView.findViewById(R.id.spinner_jenis_nidn);
        spinnerNidn.setAdapter(spinnerAdapter);
        //spinnerNidn.setOnItemClickListener(((parent, view, position, id) -> inputNidn = listNidn.get(position).getNidn()));
        spinnerNidn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                inputNidn = listNidn.get(position).getNidn();
                editNidn.setText(inputNidn);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        MaterialButton buttonSimpan = contentView.findViewById(R.id.button_simpan);
        buttonSimpan.setText(isUpdate ? "Update" : "Simpan");
        MaterialButton buttonBatal = contentView.findViewById(R.id.button_batal);
        buttonBatal.setOnClickListener(view -> dialog.dismiss());

        buttonSimpan.setOnClickListener(view -> {
            String newNpm = Objects.requireNonNull(editNpm.getText()).toString();
            String newNama = Objects.requireNonNull(editNama.getText()).toString();
            String newNidn = editNidn.getText().toString();

            if (isUpdate) {
                //update
                presenter.updateData(id_mahasiswa, newNpm, newNama, jenisKelamin, newNidn, Utils.decodeImageView(photo));
            } else {
                //add
                presenter.addData(newNpm, newNama, jenisKelamin, newNidn, Utils.decodeImageView(photo));
            }
            dialog.dismiss();
        });
        dialog.show();
    }
}