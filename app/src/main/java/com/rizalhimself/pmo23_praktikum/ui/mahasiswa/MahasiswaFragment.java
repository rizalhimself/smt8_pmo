package com.rizalhimself.pmo23_praktikum.ui.mahasiswa;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rizalhimself.pmo23_praktikum.R;
import com.rizalhimself.pmo23_praktikum.content_recycler_mahasiswa;
import com.rizalhimself.pmo23_praktikum.recyclerAdapterMahasiswa;

public class MahasiswaFragment extends Fragment {

    recyclerAdapterMahasiswa adapterMahasiswa;
    DatabaseReference databaseReference;
    View view;
    private RecyclerView recyclerView;
    private FirebaseAuth mAuth;

    //private FragmentGalleryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        view = inflater.inflate(R.layout.fragment_mahasiswa, container, false);
        String uid = mAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("RegistInfo").child(uid).child("content_recycler_mahasiswa");
        recyclerView = view.findViewById(R.id.rvMahasiswa);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapterMahasiswa);

        FirebaseRecyclerOptions<content_recycler_mahasiswa> options =
                new FirebaseRecyclerOptions.Builder<content_recycler_mahasiswa>()
                        .setQuery(databaseReference, content_recycler_mahasiswa.class).build();
        adapterMahasiswa = new recyclerAdapterMahasiswa(options);
        adapterMahasiswa.startListening();
        checkEmpty();


        return view;


        /*MahasiswaViewModel mahasiswaViewModel =
                new ViewModelProvider(this).get(MahasiswaViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        mahasiswaViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapterMahasiswa.stopListening();
        //binding = null;
    }

    private void checkEmpty() {
        if (adapterMahasiswa.getItemCount() == 0) {
            Toast.makeText(getContext(), "Database Kosong!", Toast.LENGTH_LONG).show();
        }
    }
}