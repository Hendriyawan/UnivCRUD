package com.hicoding.crud.mysqlfan.mvp.mahasiswa;

import com.hicoding.crud.mysqlfan.model.Mahasiswa;
import com.hicoding.crud.mysqlfan.model.Nidn;

import java.util.List;

public class MahasiswaContract {

    public interface Presenter {

        void loadNidn();
        void loadData();

        void addData(String npm, String nama, String jenis_kelamin, String nidn, String photo);

        void updateData(String id, String npm, String nama, String jenis_kelamin, String nidn, String photo);

        void delete(String id);

        void search(String nidn);
    }

    public interface MainView {
        void onStartProgress();

        void onStopProgress();

        void onMahasiswaLoaded(List<Mahasiswa> data);
        void onNidnLoaded(List<Nidn> nidns);

        void onDataAdded(String message);

        void onDataUpdated(String message);

        void onDataDeleted(String message);

        void onFailed(String message);
    }
}
