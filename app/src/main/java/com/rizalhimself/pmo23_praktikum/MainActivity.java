package com.rizalhimself.pmo23_praktikum;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rizalhimself.pmo23_praktikum.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private FirebaseAuth mAuth;

    private String mhsNIM, mhsNama, mhsAlamat, mhsJenisKel, mhsGolDarah, mhsEmail, mhsNoHP,
            mhsTglLahir, mhsProdi, mhsFakultas, mhsIvUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

        com.rizalhimself.pmo23_praktikum.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);



        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });


        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_mahasiswa).setOpenableLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Dapatkan headerview
        View navHeaderView = navigationView.getHeaderView(0);
        TextView tvNmUser = navHeaderView.findViewById(R.id.tvNamaUser);
        TextView tvEmUser = navHeaderView.findViewById(R.id.tvEmailUser);
        ImageView profilePic = navHeaderView.findViewById(R.id.ivAccount);

        // Masukkan Gambar ke header
        Picasso.get().load("https://3.bp.blogspot.com/-JqX8r-P6nZg/U2PIEPC9P2I/AAAAAAAAApc/3LBxUwC2kX8/s118/").into(profilePic);

        // Ambil data user dari database
        String uid = mAuth.getUid();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference dbUser = databaseReference.child("RegistInfo").child(uid);
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mhsNama = Objects.requireNonNull(snapshot.child("mhsNama").getValue()).toString();
                mhsEmail = Objects.requireNonNull(snapshot.child("mhsEmail").getValue()).toString();
                mhsNIM = Objects.requireNonNull(snapshot.child("mhsNIM").getValue()).toString();
                mhsAlamat = Objects.requireNonNull(snapshot.child("mhsAlamat").getValue()).toString();
                mhsFakultas = snapshot.child("mhsFakultas").getValue().toString();
                mhsGolDarah = snapshot.child("mhsGolDarah").getValue().toString();
                mhsJenisKel = snapshot.child("mhsJenisKel").getValue().toString();
                mhsNoHP = snapshot.child("mhsNoHp").getValue().toString();
                mhsProdi = snapshot.child("mhsProdi").getValue().toString();
                mhsTglLahir = snapshot.child("mhsTglLahir").getValue().toString();

                tvNmUser.setText(mhsNama);
                tvEmUser.setText(mhsEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_profile, null);
                ImageView ivProfile = popupView.findViewById(R.id.ivProfile);
                Picasso.get().load("https://3.bp.blogspot.com/-JqX8r-P6nZg/U2PIEPC9P2I/AAAAAAAAApc/3LBxUwC2kX8/s118/").into(ivProfile);

                ((TextView) popupView.findViewById(R.id.tvNamaProfile)).setText(mhsNama);
                ((TextView) popupView.findViewById(R.id.tvEmailProfile)).setText(mhsEmail);
                ((TextView) popupView.findViewById(R.id.tvNimProfile)).setText(mhsNIM);
                ((TextView) popupView.findViewById(R.id.tvAlamatProfile)).setText(mhsAlamat);
                ((TextView) popupView.findViewById(R.id.tvFakultasProfile)).setText(mhsFakultas);
                ((TextView) popupView.findViewById(R.id.tvProdiProfile)).setText(mhsProdi);
                ((TextView) popupView.findViewById(R.id.tvNoHPProfile)).setText(mhsNoHP);


                final PopupWindow popupWindow = new PopupWindow(getApplicationContext());
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                popupWindow.setWidth(popupView.getMeasuredWidth());
                popupWindow.setContentView(popupView);


                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

                popupView.findViewById(R.id.btOkProfile).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.edit_profil):
                startActivity(new Intent(MainActivity.this, crud_activity.class));
                return true;
            case (R.id.action_logout):
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}