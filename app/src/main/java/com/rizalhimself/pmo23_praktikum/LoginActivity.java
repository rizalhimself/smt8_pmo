package com.rizalhimself.pmo23_praktikum;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn_Login = findViewById(R.id.btLogin);
        TextView tvDaftar = findViewById(R.id.tvDaftar);
        etEmail = findViewById(R.id.etEmailLog);
        etPassword = findViewById(R.id.etPasswordLog);
        mAuth = FirebaseAuth.getInstance();
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUserAkun();
                //startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        tvDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    private void loginUserAkun() {
        String email, password;
        email = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email Harus Diisi");
            return;
        } else {
            etEmail.setError(null);
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password Harus Diisi");
            return;
        } else {
            etPassword.setError(null);
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    checkEmailDiverifikasi();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "Login Gagal : Akun Tidak Terdaftar",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                );
    }

    private void checkEmailDiverifikasi() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user.isEmailVerified()) {
            Toast.makeText(getApplicationContext(),
                    "Login Berhasil : Selamat Datang " + user,
                    Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Login Gagal : Email Belum Terverifikasi",
                    Toast.LENGTH_LONG).show();
            mAuth.signOut();
            finish();
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);

        }
    }
}
