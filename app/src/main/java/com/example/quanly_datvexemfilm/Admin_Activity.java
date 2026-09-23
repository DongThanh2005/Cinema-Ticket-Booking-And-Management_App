package com.example.quanly_datvexemfilm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import QuanLy_Movie.MovieManagement_Activity;
import QuanLy_TaiKhoan.QuanLyTaiKhoan_Activity;
import database.DatabaseHelper;

public class Admin_Activity extends AppCompatActivity {
    CardView cardQlyPhim, cardQlyLichChieu, cardQuanLyTaiKhoan;
    ImageView imgQlyPhim, imgQlyLichChieu, imgQlyTaiKhoan;
    SharedPreferences preferences;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        cardQlyPhim = findViewById(R.id.cardQuanLyPhim);
        cardQlyLichChieu = findViewById(R.id.cardQuanLyLichChieu);
        cardQuanLyTaiKhoan = findViewById(R.id.cardQuanLyTaiKhoan);
        imgQlyPhim = findViewById(R.id.imgQlyPhim);
        imgQlyLichChieu = findViewById(R.id.imgQlyLichChieu);
        imgQlyTaiKhoan = findViewById(R.id.imgQlyTaiKhoan);

        ImageButton imgBackAD = findViewById(R.id.imgBackAD);
        imgBackAD.setOnClickListener(v -> finish());
        db = new DatabaseHelper(this);
        preferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        String vaitro = preferences.getString("VaiTro","");
        if(!"Admin".equals(vaitro)){
            finish();
        }
        cardQlyPhim.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_Activity.this, MovieManagement_Activity.class);
            startActivity(intent);
        });
        cardQuanLyTaiKhoan.setOnClickListener(v -> {
            Intent intent = new Intent(Admin_Activity.this, QuanLyTaiKhoan_Activity.class);
            startActivity(intent);
        });
    }
}