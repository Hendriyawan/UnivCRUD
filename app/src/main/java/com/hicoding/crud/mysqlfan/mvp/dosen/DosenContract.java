package com.hicoding.crud.mysqlfan.mvp.dosen;

import com.hicoding.crud.mysqlfan.model.Dosen;

import java.util.List;

public class DosenContract {

    public interface Presenter {
        void loadData();

        void addData(String nidn, String nama, String tanggal_lahir, String photo);

        void updateData(String id, String nama, String nidn, String tanggal_lahir, String photo);

        void delete(String id);

        void search(String nidn);
    }

    public interface MainView {
        void onStartProgress();

        void onStopProgress();

        void onDosenLoaded(List<Dosen> dosenList);

        void onDataAdded(String message);

        void onDataUpdated(String message);

        void onDataDeleted(String message);

        void onFailed(String message);
    }
}
