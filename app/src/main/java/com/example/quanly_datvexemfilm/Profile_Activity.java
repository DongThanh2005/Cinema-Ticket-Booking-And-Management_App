package com.example.quanly_datvexemfilm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import QuanLy_TaiKhoan.DoiMatKhau_Activity;
import QuanLy_TaiKhoan.Login_Activity;
import QuanLy_TaiKhoan.TaiKhoan;
import database.DatabaseHelper;

public class Profile_Activity extends AppCompatActivity {
    DatabaseHelper db;
    SharedPreferences preferences;
    CardView cardTTTaiKhoan, cardDoiMK, cardAdmin;
    TextView txtName, txtVaiTro;
    Button btnDangXuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_profile);

        cardTTTaiKhoan = findViewById(R.id.cardTTTaiKhoan);
        cardAdmin = findViewById(R.id.cardAdmin);
        cardDoiMK = findViewById(R.id.cardDoiMK);
        txtName = findViewById(R.id.txtName);
        txtVaiTro = findViewById(R.id.txtVaiTro);
        btnDangXuat = findViewById(R.id.btnDangXuat);
        ImageButton imgback = findViewById(R.id.imgback);
        imgback.setOnClickListener(v -> {
            HienDialogDangXuat();
        });


        db = new DatabaseHelper(this);
        preferences = getSharedPreferences("USER_SESSION",MODE_PRIVATE);
        int matk = preferences.getInt("MaTK", -1);
        String vaitro = preferences.getString("VaiTro","User");
        TaiKhoan tk = db.getTaiKhoanById(matk);

        if (tk == null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();
            Intent intent = new Intent(Profile_Activity.this, Login_Activity.class);
            startActivity(intent);
            finish();
            return;
        }
        if(vaitro.equals("User")){
            cardAdmin.setVisibility(View.GONE);
            txtVaiTro.setText("User" + " CineGo");
        }
        else {
            txtVaiTro.setText("Admin "+"Cinego");
        }

        txtName.setText("Xin chào "+ tk.getHoTen());

        btnDangXuat.setOnClickListener(v -> {
            HienDialogDangXuat();
        });
        cardDoiMK.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Activity.this, DoiMatKhau_Activity.class);
            startActivity(intent);
        });
        cardAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Activity.this, Admin_Activity.class);
            startActivity(intent);
        });
        cardTTTaiKhoan.setOnClickListener(v -> {
            Intent intent = new Intent(Profile_Activity.this, ThongTinCaNhan_Activity.class);
            startActivity(intent);
        });
    }
    private void HienDialogDangXuat() {

        new AlertDialog.Builder(this)
                .setTitle("Đăng xuất")
                .setMessage("Bạn có muốn đăng xuất không?")
                .setPositiveButton("Có", (dialog, which) -> {
                    preferences.edit().clear().apply();
                    Intent intent = new Intent(Profile_Activity.this, Login_Activity.class);
                    startActivity(intent);
                    finish();
                })
                .setNegativeButton("Không", (dialog, which) -> {
                    dialog.dismiss();
                })
                .setCancelable(true)
                .show();
    }
}