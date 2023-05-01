package com.rizalhimself.pmo23_praktikum;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;

public class crud_activity extends AppCompatActivity {
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REUEST_CODE_GALLERY = 2;
    public Uri imageUrl, uri;
    public Bitmap bitmap;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormat;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseStorage firebaseStorage;
    private EditText etNIM, etNama, etEmail, etNoHp, etIPK, etAlamat;
    private Spinner spFakultas, spProdi;
    private CheckBox cbGolA, cbGolB, cbGolAB, cbO;
    private ImageView ivUser;
    private RadioButton rbPria, rbWanita;
    private RadioGroup radioGroup;
    private Button btPilihGambar, btSimpan;
    private ProgressBar progressBar;
    private StorageReference reference;
    private String uid;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        //inisialisasi edittext
        etNama = findViewById(R.id.etNamaCrud);
        etNIM = findViewById(R.id.etNimCrud);
        etAlamat = findViewById(R.id.etAlamatCrud);
        etIPK = findViewById(R.id.etIPKCrud);
        etEmail = findViewById(R.id.etEmailCrud);
        etNoHp = findViewById(R.id.etNoHPCrud);
        spFakultas = findViewById(R.id.spFakultasCrud);
        spProdi = findViewById(R.id.spProdiCrud);

        //Binding data ke spinner
        ArrayAdapter<CharSequence> adapterFakultas = ArrayAdapter.createFromResource(
                this, R.array.list_fakultas, android.R.layout.simple_spinner_item);
        adapterFakultas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFakultas.setAdapter(adapterFakultas);

        ArrayAdapter<CharSequence> adapterProdi = ArrayAdapter.createFromResource(
                this, R.array.list_prodi, android.R.layout.simple_spinner_item);
        adapterProdi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProdi.setAdapter(adapterProdi);

        //dapatkan akses data tersimpan user aktif
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        uid = mAuth.getUid();
        databaseReference = firebaseDatabase.getReference();
        DatabaseReference dbUser = databaseReference.child("RegistInfo").child(uid);
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String nmUser = snapshot.child("mhsNama").getValue(String.class);
                String nimUser = snapshot.child("mhsNim").getValue(String.class);
                String emUser = snapshot.child("mhsEmail").getValue(String.class);
                etNama.setText(nmUser);
                etNIM.setText(nimUser);
                etEmail.setText(emUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void rbClick(View view) {
    }
}