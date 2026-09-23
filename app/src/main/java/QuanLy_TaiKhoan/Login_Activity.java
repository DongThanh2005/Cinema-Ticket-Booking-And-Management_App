package QuanLy_TaiKhoan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanly_datvexemfilm.Profile_Activity;
import com.example.quanly_datvexemfilm.R;

import database.DatabaseHelper;

public class Login_Activity extends AppCompatActivity {
    Button btnDangNhap, btnDangNhapGG;
    TextView txtDangKy;
    EditText edtUserName, edtPassWord;
    DatabaseHelper db;
    ImageView imgHienMK;
    Boolean IsPassWord = false;
    SharedPreferences preferences;
    CheckBox ckLuuMK;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        txtDangKy = findViewById(R.id.txtDangKy);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangNhapGG = findViewById(R.id.btnDangNhapGG);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassWord = findViewById(R.id.edtPassWord);
        imgHienMK= findViewById(R.id.imgHienMK);
        ckLuuMK = findViewById(R.id.ckLuuMK);

        imgHienMK.setOnClickListener(v -> {
            if(IsPassWord){
                edtPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imgHienMK.setImageResource(R.drawable.ic_anmk);
            }
            else {
                edtPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imgHienMK.setImageResource(R.drawable.ic_hienmk);
            }
            IsPassWord = !IsPassWord;
            edtPassWord.setSelection(edtPassWord.getText().length());

        });
        ImageButton imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> finish());

        txtDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login_Activity.this, DangKy_Activity.class);
                startActivity(intent);
            }
        });
        db = new DatabaseHelper(this);
        preferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        btnDangNhap.setOnClickListener(v -> {
            DangNhap();
        });
        CheckLogin();

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
    private boolean validateLogin() {

        String taiKhoan = edtUserName.getText().toString().trim();
        String matKhau = edtPassWord.getText().toString();

        if (taiKhoan.isEmpty()) {
            edtUserName.setError("Vui lòng nhập Email hoặc SĐT");
            edtPassWord.requestFocus();
            return false;
        }

        if (matKhau.isEmpty()) {
            edtPassWord.setError("Vui lòng nhập mật khẩu");
            edtPassWord.requestFocus();
            return false;
        }

        return true;
    }
    private void DangNhap(){
        if(!validateLogin()){
            return;
        }
        String taikhoan = edtUserName.getText().toString().trim();
        String matkhau = edtPassWord.getText().toString().trim();
        TaiKhoan tk = db.Login(taikhoan, matkhau);
        if(tk == null){
            Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(tk.getTrangThai() == 0){
            Toast.makeText(this, "Tài khoản của bạn đã bị khóa! Vui lòng nộp đủ 100k để mở khóa tài khoản",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            SharedPreferences.Editor editor = preferences.edit();
            if(ckLuuMK.isChecked()){
                editor.putBoolean("islogin", true);
            }
            editor.putInt("MaTK",tk.getMaTK());
            editor.putString("VaiTro", tk.getVaiTro());
            editor.apply();

            Toast.makeText(this,"Đăng nhập thành công!",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Login_Activity.this, Profile_Activity.class);
            startActivity(intent);
            finish();
        }
    }
    private void CheckLogin(){
        boolean islogin = preferences.getBoolean("islogin", false);
        if(!islogin){
            return;
        }
        Intent intent = new Intent(Login_Activity.this, Profile_Activity.class);
        startActivity(intent);
        finish();

    }
}