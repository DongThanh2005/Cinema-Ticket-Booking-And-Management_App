package QuanLy_TaiKhoan;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanly_datvexemfilm.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import database.DatabaseHelper;

public class DangKy_Activity extends AppCompatActivity {
    ImageView imgHienMK, imgHienMK2;
    CheckBox ckCamKet;
    Button btnDangKy;
    Boolean IsPassWord = false;
    Spinner spnGioiTinh;
    EditText edtHoVaTen, edtEmail, edtPassWord, edtNhapLaiPassWord, edtSDT, edtCCCD, edtNgaySinh;
    DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);

        edtNgaySinh  = findViewById(R.id.edtNgaySinh);
        spnGioiTinh = findViewById(R.id.spnGioiTinh);
        edtCCCD = findViewById(R.id.edtCCCD);
        edtEmail = findViewById(R.id.edtEmail);
        edtSDT = findViewById(R.id.edtSDT);
        edtHoVaTen = findViewById(R.id.edtHoVaTen);
        edtPassWord = findViewById(R.id.edtPassWord);
        edtNhapLaiPassWord = findViewById(R.id.edtNhapLaiPassWord);
        btnDangKy = findViewById(R.id.btnDangKy);
        ckCamKet = findViewById(R.id.ckCamKet);
        imgHienMK = findViewById(R.id.imgHienMK);
        imgHienMK2 = findViewById(R.id.imgHienMK2);
        ImageButton imgpack = findViewById(R.id.imgpach);
        imgpack.setOnClickListener(v -> finish());

        // Hiện mật khẩu
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
        imgHienMK2.setOnClickListener(v -> {
            if(IsPassWord){
                edtNhapLaiPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imgHienMK2.setImageResource(R.drawable.ic_anmk);
            }
            else {
                edtNhapLaiPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imgHienMK2.setImageResource(R.drawable.ic_hienmk);
            }
            IsPassWord = !IsPassWord;
            edtNhapLaiPassWord.setSelection(edtNhapLaiPassWord.getText().length());
        });
        // thêm giới tính
        String[] DSgioitinh = {"Chọn giới tính", "Nam", "Nữ", "Khác"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, DSgioitinh);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGioiTinh.setAdapter(adapter);
        String gioitinh = spnGioiTinh.getSelectedItem().toString();
        edtNgaySinh.setOnClickListener(v -> LayNgay());

        spnGioiTinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) view).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        db = new DatabaseHelper(this);
        btnDangKy.setOnClickListener(v -> {
            DangKy();
        });

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
    //Hiển thị ngày sinh
    private void LayNgay() {

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                DangKy_Activity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {

                    String date = String.format("%02d/%02d/%04d",
                            selectedDay,
                            selectedMonth + 1,
                            selectedYear);
                    edtNgaySinh.setText(date);
                },
                year,
                month,
                day
        );
        datePickerDialog.show();
    }
    private Boolean validateInput(){
        String hoTen = edtHoVaTen.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String matKhau = edtPassWord.getText().toString();
        String nhapLaiMK = edtNhapLaiPassWord.getText().toString();
        String sdt = edtSDT.getText().toString().trim();
        String cccd = edtCCCD.getText().toString().trim();
        String ngaySinh = edtNgaySinh.getText().toString().trim();
        String gioitinh = spnGioiTinh.getSelectedItem().toString();
        if (hoTen.isEmpty()) {
            edtHoVaTen.setError("Vui lòng nhập họ tên");
            edtHoVaTen.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            edtEmail.setError("Vui lòng nhập Email");
            edtEmail.requestFocus();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            edtEmail.setError("Email không hợp lệ");
            edtEmail.requestFocus();
            return false;
        }

        if (db.checkEmail(email)) {
            edtEmail.setError("Email đã tồn tại");
            edtEmail.requestFocus();
            return false;
        }
        if (matKhau.isEmpty()) {
            edtPassWord.setError("Vui lòng nhập mật khẩu");
            edtPassWord.requestFocus();
            return false;
        }

        if (matKhau.length() < 6) {
            edtPassWord.setError("Mật khẩu phải có ít nhất 6 ký tự");
            edtPassWord.requestFocus();
            return false;
        }

        // Nhập lại mật khẩu
        if (!matKhau.equals(nhapLaiMK)) {
            edtNhapLaiPassWord.setError("Mật khẩu nhập lại không khớp");
            edtNhapLaiPassWord.requestFocus();
            return false;
        }

        // Số điện thoại
        if (sdt.isEmpty()) {
            edtSDT.setError("Vui lòng nhập số điện thoại");
            edtSDT.requestFocus();
            return false;
        }

        if (!sdt.matches("\\d{10}")) {
            edtSDT.setError("Số điện thoại phải gồm 10 chữ số");
            edtSDT.requestFocus();
            return false;
        }

        if (db.checkSoDienThoai(sdt)) {
            edtSDT.setError("Số điện thoại đã tồn tại");
            edtSDT.requestFocus();
            return false;
        }
        if (!cccd.isEmpty()) {
            if (!cccd.matches("\\d{12}")) {
                edtCCCD.setError("CCCD phải gồm 12 chữ số");
                edtCCCD.requestFocus();
                return false;
            }
            if (db.checkCCCD(cccd)) {
                edtCCCD.setError("CCCD đã tồn tại");
                edtCCCD.requestFocus();
                return false;
            }
        }
        if (!ckCamKet.isChecked()) {
            Toast.makeText(this,
                    "Bạn phải đồng ý điều khoản sử dụng",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    private void DangKy(){
        if(!validateInput()){
            return;
        }
        TaiKhoan tk = new TaiKhoan();
        tk.setHoTen(edtHoVaTen.getText().toString().trim());
        tk.setEmail(edtEmail.getText().toString().trim());
        tk.setSoDienThoai(edtSDT.getText().toString().trim());
        tk.setMatKhau(edtPassWord.getText().toString().trim());
        String cccd = edtCCCD.getText().toString().trim();
        tk.setCCCD(cccd.isEmpty() ? null : cccd);
        String ngaySinh = edtNgaySinh.getText().toString().trim();
        tk.setNgaySinh(ngaySinh.isEmpty() ? null : ngaySinh);
        if (spnGioiTinh.getSelectedItemPosition() == 0) {
            tk.setGioiTinh(null);
        } else {
            tk.setGioiTinh(spnGioiTinh.getSelectedItem().toString());
        }
        tk.setAnhDaiDien(null);
        tk.setVaiTro("User");
        tk.setTrangThai(1);
        String ngayTao = new SimpleDateFormat(
                "dd/MM/yyyy",
                Locale.getDefault()
        ).format(new Date());

        tk.setNgayTao(ngayTao);
        boolean result = db.insertTaiKhoan(tk);
        if(result){
            Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
        }

    }
}