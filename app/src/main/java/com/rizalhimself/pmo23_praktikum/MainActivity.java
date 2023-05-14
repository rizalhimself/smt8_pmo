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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rizalhimself.pmo23_praktikum.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private FirebaseAuth mAuth;

    private String mhsNIM, mhsNama, mhsAlamat, mhsJenisKel, mhsGolDarah, mhsEmail, mhsNoHP,
            mhsTglLahir, mhsProdi, mhsFakultas, mhsIvUrl, mhsIPK;

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


        // Ambil data user dari database
        String uid = mAuth.getUid();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference dbUser = databaseReference.child("RegistInfo").child(uid);
        dbUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mhsNama = snapshot.child("mhsNama").getValue(String.class);
                mhsEmail = snapshot.child("mhsEmail").getValue(String.class);
                mhsNIM = snapshot.child("mhsNim").getValue(String.class);
                mhsAlamat = snapshot.child("mhsAlamat").getValue(String.class);
                mhsFakultas = snapshot.child("mhsFakultas").getValue(String.class);
                mhsGolDarah = snapshot.child("mhsGolDarah").getValue(String.class);
                mhsJenisKel = snapshot.child("mhsJenisKel").getValue(String.class);
                mhsNoHP = snapshot.child("mhsNoHp").getValue(String.class);
                mhsProdi = snapshot.child("mhsProdi").getValue(String.class);
                mhsTglLahir = snapshot.child("mhsTglLahir").getValue(String.class);
                mhsIPK = snapshot.child("mhsIpk").getValue(String.class);
                mhsIvUrl = snapshot.child("mhsImgUrl").getValue(String.class);


                tvNmUser.setText(mhsNama);
                tvEmUser.setText(mhsEmail);
                //Picasso.get().load(mhsIvUrl).into(profilePic);
                Glide.with(getApplicationContext()).load(mhsIvUrl).into(profilePic);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Database error", Toast.LENGTH_SHORT).show();
            }
        });
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_profile, null);
                ImageView ivProfile = popupView.findViewById(R.id.ivProfile);
                //Picasso.get().load(mhsIvUrl).into(ivProfile);
                Glide.with(getApplicationContext()).load(mhsIvUrl).into(ivProfile);

                ((TextView) popupView.findViewById(R.id.tvNamaProfile)).setText(mhsNama);
                ((TextView) popupView.findViewById(R.id.tvEmailProfile)).setText(mhsEmail);
                ((TextView) popupView.findViewById(R.id.tvNimProfile)).setText(mhsNIM);
                ((TextView) popupView.findViewById(R.id.tvAlamatProfile)).setText(mhsAlamat);
                ((TextView) popupView.findViewById(R.id.tvFakultasProfile)).setText(mhsFakultas);
                ((TextView) popupView.findViewById(R.id.tvProdiProfile)).setText(mhsProdi);
                ((TextView) popupView.findViewById(R.id.tvNoHPProfile)).setText(mhsNoHP);
                ((TextView) popupView.findViewById(R.id.tvGolDarahProfile)).setText(mhsGolDarah);
                ((TextView) popupView.findViewById(R.id.tvJenisKelaminProfile)).setText(mhsJenisKel);
                ((TextView) popupView.findViewById(R.id.tvIPKProfile)).setText(mhsIPK);
                ((TextView) popupView.findViewById(R.id.tvTglLahirProfile)).setText(mhsTglLahir);

                final PopupWindow popupWindow = new PopupWindow(getApplicationContext());
                popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
                popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
                popupWindow.setWidth(popupView.getMeasuredWidth());
                popupWindow.setContentView(popupView);
                popupWindow.setAnimationStyle(R.style.Animation);


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