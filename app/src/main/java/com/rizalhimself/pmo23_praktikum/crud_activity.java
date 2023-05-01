package com.rizalhimself.pmo23_praktikum;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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
import java.util.Calendar;

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
    private EditText etNIM, etNama, etEmail, etNoHp, etIPK, etAlamat, etTanggalLahir;
    private Spinner spFakultas, spProdi;
    private CheckBox cbGolA, cbGolB, cbGolAB, cbO;
    private ImageView ivUser;
    private RadioButton rbPria, rbWanita;
    private RadioGroup radioGroup;
    private Button btPilihGambar, btSimpan;
    private ProgressBar progressBar;
    private StorageReference reference;
    private String uid;
    private String outputGol;
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
        etTanggalLahir = findViewById(R.id.etTglLahir);

        //inisialisasi spinner
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

        //inisialisasi checkbox
        cbGolA = findViewById(R.id.cbGolA);
        cbGolAB = findViewById(R.id.cbGolAB);
        cbGolB = findViewById(R.id.cbGolB);
        cbO = findViewById(R.id.cbGolO);

        cbGolA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbGolAB.setChecked(false);
                    cbGolB.setChecked(false);
                    cbO.setChecked(false);
                    outputGol = "A";
                }
            }
        });
        cbGolB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbGolA.setChecked(false);
                    cbGolAB.setChecked(false);
                    cbO.setChecked(false);
                    outputGol = "B";
                }
            }
        });
        cbGolAB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbGolA.setChecked(false);
                    cbGolB.setChecked(false);
                    cbO.setChecked(false);
                    outputGol = "AB";
                }
            }
        });
        cbO.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbGolA.setChecked(false);
                    cbGolAB.setChecked(false);
                    cbGolB.setChecked(false);
                    outputGol = "O";
                }
            }
        });

        //inisialisasi tgl lahir
        dateFormat = new SimpleDateFormat("dd MMM yyyy");
        etTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pemanggilan fungsi date pcker
                tampilkanDialogTanggal();
            }
        });

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

    //pemanggilan method date pcker dialog
    public void tampilkanDialogTanggal() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                etTanggalLahir.setText(dateFormat.format(newDate.getTime()));
            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }
}