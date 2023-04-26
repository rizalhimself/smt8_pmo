package com.rizalhimself.pmo23_praktikum;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    String email, password, nim, nama;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth.AuthStateListener mAuthListener;
    RegistInfo registInfo;
    private EditText etNim, etEmail, etPassword, etPasswordU, etNama;
    private CheckBox cbAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etNama = findViewById(R.id.etNamaReg);
        etNim = findViewById(R.id.etNIMReg);
        etEmail = findViewById(R.id.etEmailReg);
        etPassword = findViewById(R.id.etPasswordReg);
        etPasswordU = findViewById(R.id.etPasswordUlangReg);
        Button btDaftar = findViewById(R.id.btDaftarReg);
        cbAgree = findViewById(R.id.cbAgreement);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("RegistInfo");

        registInfo = new RegistInfo();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    kirimEmailVerifikasi();
                }
            }
        };

        etNim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etNim.getText().toString().length() == 0) {
                    etNim.setError("NIM Harus Diisi");
                } else {
                    etNim.setError(null);
                }
            }
        });

        etNama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etNama.getText().toString().length() == 0) {
                    etNama.setError("Nama Harus Diisi");
                } else {
                    etNama.setError(null);
                }
            }
        });

        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etEmail.getText().toString().length() == 0) {
                    etEmail.setError("Email Harus Diisi");
                } else {
                    etEmail.setError(null);
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPassword.getText().toString().length() == 0) {
                    etPassword.setError("Password Harus Diisi");
                } else {
                    etPassword.setError(null);
                }
            }
        });

        etPasswordU.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPasswordU.getText().toString().length() == 0) {
                    etPasswordU.setError("Password Harus Diisi");
                } else if (!etPasswordU.getText().toString().equals(etPassword.getText().toString())) {
                    etPasswordU.setError("Password Tidak Sama");
                } else {
                    etPasswordU.setError(null);
                }
            }
        });

        btDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!cbAgree.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Anda belum menyetujui peraturan aplikasi ini!", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(getApplicationContext(), "Selamat Datang" + nim + email + password, Toast.LENGTH_LONG).show();
                    registerBaru();
                }
            }
        });
    }

    private void registerBaru() {
        nama = etNama.getText().toString();
        nim = etNim.getText().toString();
        email = etEmail.getText().toString();
        password = etPasswordU.getText().toString();

        mAuth
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("TAG", "createUserWithEmail:onComplete" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            mAuth.addAuthStateListener(mAuthListener);
                            addDataToRTDB(nim, nama, email);
                            Toast.makeText(getApplicationContext(),
                                    "Registrasi Berhasil : Silakan Check Email Anda",
                                    Toast.LENGTH_LONG).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                Toast.makeText(getApplicationContext(),
                                        "Registrasi Gagal : Format Email/Password Salah",
                                        Toast.LENGTH_LONG).show();
                            } catch (FirebaseAuthUserCollisionException existEmail) {
                                Toast.makeText(getApplicationContext(),
                                        "Registrasi Gagal : Email Sudah Terdaftar",
                                        Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                Log.d("TAG", "onComplete: " + e.getMessage());
                            }
                        }
                    }
                });
    }

    private void addDataToRTDB(String nim, String nama, String email) {
        registInfo.setMhsNim(nim);
        registInfo.setMhsNama(nama);
        registInfo.setMhsEmail(email);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userUID = mAuth.getUid();
                databaseReference.child(userUID).setValue(registInfo);
                Toast.makeText(getApplicationContext(),
                        "Data Anda Telah Tersimpan",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),
                        "Gagal Menyimpan Data",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void kirimEmailVerifikasi() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mAuth.signOut();
                        } else {
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                            overridePendingTransition(0, 0);
                        }
                    }
                });
    }

}