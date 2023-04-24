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

public class SignUpActivity extends AppCompatActivity {
    String email, password;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private EditText etEmail, etPassword, etPasswordU;
    private CheckBox cbAgree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = findViewById(R.id.etEmailReg);
        etPassword = findViewById(R.id.etPasswordReg);
        etPasswordU = findViewById(R.id.etPasswordUlangReg);
        Button btDaftar = findViewById(R.id.btDaftarReg);
        cbAgree = findViewById(R.id.cbAgreement);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    kirimEmailVerifikasi();
                }
            }
        };


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
                email = etEmail.getText().toString();
                password = etPasswordU.getText().toString();
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
                            Toast.makeText(getApplicationContext(),
                                    "Registrasi Berhasil",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthInvalidCredentialsException malformedEmail) {
                                Toast.makeText(getApplicationContext(),
                                        "Registrasi Gagal : Format Email Salah",
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

    private void kirimEmailVerifikasi() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            mAuth.signOut();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());
                        }
                    }
                });
    }

}