package com.rizalhimself.pmo23_praktikum;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class recyclerAdapterMahasiswa extends FirebaseRecyclerAdapter<content_recycler_mahasiswa,
        recyclerAdapterMahasiswa.mahasiswaViewHolder> {

    public recyclerAdapterMahasiswa(
            @NonNull FirebaseRecyclerOptions<content_recycler_mahasiswa> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull mahasiswaViewHolder holder, int position,
                                    @NonNull content_recycler_mahasiswa model) {
        holder.ivMhsRv.setImageURI(Uri.parse(model.getIvMahasiswa()));
        holder.rvNim.setText(model.getNimMahasiswa());
        holder.rvNama.setText(model.getNamaMahasiswa());
        holder.rvEmail.setText(model.getEmailMahasiswa());
    }

    @NonNull
    @Override
    public mahasiswaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_recycler_mahasiswa, parent, false);
        return new recyclerAdapterMahasiswa.mahasiswaViewHolder(view);
    }

    class mahasiswaViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMhsRv;
        TextView rvNim, rvNama, rvEmail;

        public mahasiswaViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMhsRv = itemView.findViewById(R.id.ivMhsRv);
            rvNim = itemView.findViewById(R.id.rvNim);
            rvNama = itemView.findViewById(R.id.rvNama);
            rvEmail = itemView.findViewById(R.id.rvEmail);
        }
    }
}
