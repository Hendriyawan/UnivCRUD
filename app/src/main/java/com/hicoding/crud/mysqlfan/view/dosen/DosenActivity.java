package com.hicoding.crud.mysqlfan.view.dosen;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hicoding.crud.mysqlfan.R;
import com.hicoding.crud.mysqlfan.Utils;
import com.hicoding.crud.mysqlfan.adapter.DosenAdapter;
import com.hicoding.crud.mysqlfan.model.Dosen;
import com.hicoding.crud.mysqlfan.mvp.dosen.DosenContract;
import com.hicoding.crud.mysqlfan.mvp.dosen.DosenPresenter;
import com.hicoding.crud.mysqlfan.view.login.LoginActivity;
import com.iamhabib.easy_preference.EasyPreference;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DosenActivity extends AppCompatActivity implements DosenContract.MainView {

    public static final int REQUEST_CODE_GALLERY = 999;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.recycler_view_dosen)
    RecyclerView recyclerView;
    @BindView(R.id.fab_add_data_dosen)
    FloatingActionButton fabAddDosen;
    @BindView(R.id.edit_text_dosen)
    AppCompatEditText editTextSearchDosen;
    @BindView(R.id.image_view_search)
    AppCompatImageView imageViewSearch;
    private DosenPresenter presenter;
    private AppCompatImageView photo;
    private Boolean nidnEmpty = false;
    private String nidn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen);
        //bind view
        ButterKnife.bind(this);

        presenter = new DosenPresenter(this);
        presenter.loadData();


        //floating action button add data
        fabAddDosen.setOnClickListener(view -> showDialogInputDosen(false, "", "", "", "", ""));

        editTextSearchDosen.addTextChangedListener(new TextWatcher() {
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
    public void onDosenLoaded(List<Dosen> dosenList) {
        Log.d("DEBUG", "size " + dosenList.size());
        initRecyclerView(dosenList);
    }

    @Override
    public void onDataAdded(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        presenter.loadData();
    }

    @Override
    public void onDataUpdated(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        presenter.loadData();
    }

    @Override
    public void onDataDeleted(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        presenter.loadData();
    }

    @Override
    public void onFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    /**
     * initialize RecyclerView
     *
     * @param dosenList dosen
     */
    private void initRecyclerView(List<Dosen> dosenList) {
        if (dosenList.size() > 0) {
            DosenAdapter dosenAdapter = new DosenAdapter(this, dosenList);
            dosenAdapter.setOnDosenClick(dosen -> {
                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Pilih Aksi");
                dialog.setItems(items, ((dialogInterface, i) -> {
                    if (i == 0) {
                        showDialogInputDosen(true, dosen.getId_dosen(), dosen.getNama(), dosen.getNidn(), dosen.getTanggal_lahir(), dosen.getPhoto());
                    }
                    if (i == 1) {
                        presenter.delete(dosen.getId_dosen());
                    }
                }));
                dialog.show();
            });
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(dosenAdapter);
        } else {
            Toast.makeText(this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    //show dialog input data dosen
    private void showDialogInputDosen(boolean isUpdate, String id, String nama, String nidn, String date, String photo_url) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View contentView = getLayoutInflater().inflate(R.layout.layout_dialog_input_data_dosen, null, false);
        builder.setView(contentView);
        Dialog dialog = builder.create();

        photo = contentView.findViewById(R.id.image_view_photo);
        AppCompatEditText editNama = contentView.findViewById(R.id.edit_nama);
        AppCompatEditText editNidn = contentView.findViewById(R.id.edit_nidn);
        AppCompatEditText editTanggalLahir = contentView.findViewById(R.id.edit_tanggal_lahir);

        if (isUpdate) {
            Glide.with(this).load(photo_url).into(photo);
        }

        editNama.setText(nama);
        editNidn.setText(nidn);
        editTanggalLahir.setText(date);

        photo.setOnClickListener(view -> {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
        });

        editTanggalLahir.setOnClickListener(view12 -> {
            Calendar calendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {

                Calendar newDate = Calendar.getInstance(Locale.getDefault());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                newDate.set(year, monthOfYear, dayOfMonth);

                editTanggalLahir.setText(sdf.format(newDate.getTime()));

            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        MaterialButton buttonSimpan = contentView.findViewById(R.id.button_simpan);
        buttonSimpan.setText(isUpdate ? "Update" : "Simpan");
        MaterialButton buttonBattal = contentView.findViewById(R.id.button_batal);

        //button battal
        buttonBattal.setOnClickListener(view1 -> {
            dialog.dismiss();
        });

        //button simpan
        buttonSimpan.setOnClickListener(view2 -> {
            String newNama = Objects.requireNonNull(editNama.getText()).toString();
            String newNidn = Objects.requireNonNull(editNidn.getText()).toString();
            String newTanggalLahir = Objects.requireNonNull(editTanggalLahir.getText()).toString();
            if (isUpdate) {
                //update
                presenter.updateData(id, newNama, newNidn, newTanggalLahir, Utils.decodeImageView(photo));
            } else {
                //add
                presenter.addData(newNidn, newNama, newTanggalLahir, Utils.decodeImageView(photo));

            }
            dialog.dismiss();
        });
        dialog.show();
    }
}