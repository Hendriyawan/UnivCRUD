package com.hicoding.crud.mysqlfan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.hicoding.crud.mysqlfan.R;
import com.hicoding.crud.mysqlfan.model.Dosen;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class DosenAdapter extends RecyclerView.Adapter<DosenAdapter.ViewHolder> {
    private List<Dosen> dosenList;
    private OnDosenClick onDosenClick;
    private Context context;

    public DosenAdapter(Context context, List<Dosen> dosenList) {
        this.context = context;
        this.dosenList = dosenList;
    }

    public void setOnDosenClick(OnDosenClick onDosenClick) {
        this.onDosenClick = onDosenClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dosen, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Dosen dosen = dosenList.get(position);
        Glide.with(context).load(dosen.getPhoto()).into(holder.circleImageViewPhoto);
        holder.textViewNama.setText(dosen.getNama());
        holder.textViewNidn.setText(dosen.getNidn());
        holder.textViewTglLahir.setText(dosen.getTanggal_lahir());
        holder.imageViewAction.setOnClickListener(view -> {
            onDosenClick.onClick(dosen);
        });
    }

    @Override
    public int getItemCount() {
        return dosenList.size();
    }

    public interface OnDosenClick {
        void onClick(Dosen dosen);
    }

    //class ViewHolder
    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_photo)
        CircleImageView circleImageViewPhoto;
        @BindView(R.id.item_nama_dosen)
        AppCompatTextView textViewNama;
        @BindView(R.id.item_nidn_dosen)
        AppCompatTextView textViewNidn;
        @BindView(R.id.item_tanggal_lahir_dosen)
        AppCompatTextView textViewTglLahir;
        @BindView(R.id.image_view_action)
        AppCompatImageView imageViewAction;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
