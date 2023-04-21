package com.rizalhimself.pmo23_praktikum;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    String nim, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        EditText etNim = findViewById(R.id.etNIMReg);
        EditText etEmail = findViewById(R.id.etEmailReg);
        EditText etPassword = findViewById(R.id.etPasswordReg);
        EditText etPasswordU = findViewById(R.id.etPasswordUlangReg);
        Button btnDaftar = findViewById(R.id.btDaftarReg);
        CheckBox cbAgree = findViewById(R.id.cbAgreement);

        etNim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etNim.getText().toString().length() <= 0) {
                    etNim.setError("NIM Harus Diisi");
                } else {
                    etNim.setError(null);
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

        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nim = etNim.getText().toString();
                email = etEmail.getText().toString();
                password = etPasswordU.getText().toString();
                if (!cbAgree.isChecked()) {
                    Toast.makeText(getApplicationContext(), "Anda belum menyetujui peraturan aplikasi ini!", Toast.LENGTH_SHORT).show();
                } else {
                    //nanti eksekusi method send data to firebase
                    Toast.makeText(getApplicationContext(), "Selamat Datang" + nim + email + password, Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}