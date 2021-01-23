package com.hicoding.crud.mysqlfan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hicoding.crud.mysqlfan.R;
import com.hicoding.crud.mysqlfan.model.Mahasiswa;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {
    private List<Mahasiswa> mahasiswaList;
    private OnMahasiswaClick onMahasiswaClick;
    private Context context;

    public MahasiswaAdapter(Context context, List<Mahasiswa> mahasiswaList) {
        this.context = context;
        this.mahasiswaList = mahasiswaList;
    }

    public void setOnMahasiswaClick(OnMahasiswaClick onMahasiswaClick) {
        this.onMahasiswaClick = onMahasiswaClick;
    }

    public interface OnMahasiswaClick {
        void onClick(Mahasiswa mahasiswa);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mahasiswa, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mahasiswa mahasiswa = mahasiswaList.get(position);
        Glide.with(context).load(mahasiswa.getPhoto()).into(holder.circleImageViewPhoto);
        holder.textViewNpm.setText(mahasiswa.getNpm());
        holder.textViewNama.setText(mahasiswa.getNama());
        holder.textViewJenisKelamin.setText(mahasiswa.getJenis_kelamin());
        holder.textViewNidn.setText(mahasiswa.getNidn());
        holder.imageViewAction.setOnClickListener(view -> onMahasiswaClick.onClick(mahasiswa));
    }

    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_photo)
        CircleImageView circleImageViewPhoto;
        @BindView(R.id.item_npm)
        AppCompatTextView textViewNpm;
        @BindView(R.id.item_nama_mahasiswa)
        AppCompatTextView textViewNama;
        @BindView(R.id.item_jenis_kelamin)
        AppCompatTextView textViewJenisKelamin;
        @BindView(R.id.item_nidn)
        AppCompatTextView textViewNidn;
        @BindView(R.id.image_view_action)
        AppCompatImageView imageViewAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
