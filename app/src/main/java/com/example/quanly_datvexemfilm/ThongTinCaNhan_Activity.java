package com.example.quanly_datvexemfilm;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import QuanLy_TaiKhoan.TaiKhoan;
import database.DatabaseHelper;

public class ThongTinCaNhan_Activity extends AppCompatActivity {
    SharedPreferences preferences;
    DatabaseHelper db;

    TextView txtEmail, txtHoTen, txtSDT, txtCCCD, txtGioiTinh, txtNgaySinh, txtTinh, txtQuan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_tin_ca_nhan);

        txtEmail = findViewById(R.id.txtEmail);
        txtHoTen = findViewById(R.id.txtHoVaTen);
        txtSDT = findViewById(R.id.txtSDT);
        txtCCCD = findViewById(R.id.txtCCCD);
        txtGioiTinh = findViewById(R.id.txtGioiTinh);
        txtNgaySinh = findViewById(R.id.txtNgaySinh);
        txtTinh =findViewById(R.id.txtTinh);
        txtQuan = findViewById(R.id.txtQuan);

        ImageButton imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> finish());
        db = new DatabaseHelper(this);
        preferences = getSharedPreferences("USER_SESSION",MODE_PRIVATE);
        int matk = preferences.getInt("MaTK", -1);
        TaiKhoan tk = db.getTaiKhoanById(matk);
        if(tk != null){
            txtHoTen.setText(tk.getHoTen());
            txtCCCD.setText(tk.getCCCD());
            txtEmail.setText(tk.getEmail());
            txtSDT.setText(tk.getSoDienThoai());
            txtGioiTinh.setText(tk.getGioiTinh());
            txtNgaySinh.setText(tk.getNgaySinh());
            txtTinh.setText("Chưa có");
            txtQuan.setText("Chưa có");
        }

    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        View view = getCurrentFocus();

        if (view instanceof EditText) {

            Rect outRect = new Rect();
            view.getGlobalVisibleRect(outRect);

            if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {

                view.clearFocus();

                InputMethodManager imm =
                        (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }

        return super.dispatchTouchEvent(ev);
    }
}