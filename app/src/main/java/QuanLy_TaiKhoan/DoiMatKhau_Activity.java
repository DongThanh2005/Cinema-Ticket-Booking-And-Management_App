package QuanLy_TaiKhoan;

import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanly_datvexemfilm.R;

import database.DatabaseHelper;

public class DoiMatKhau_Activity extends AppCompatActivity {
    EditText edtPassWord, edtPassWordMoi, edtNhapLaiPassWordMoi;
    ImageView imgHienMkCu, imgHienMKMoi, imgHienMKMoi2;
    Button btnDoiMK;
    Boolean IsPassWord = false;
    DatabaseHelper db;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doi_mat_khau);

        edtPassWord = findViewById(R.id.edtPassWord);
        edtPassWordMoi = findViewById(R.id.edtPassWordMoi);
        edtNhapLaiPassWordMoi = findViewById(R.id.edtNhapLaiPassWordMoi);
        imgHienMkCu = findViewById(R.id.imgHienMKCu);
        imgHienMKMoi = findViewById(R.id.imgHienMKMoi);
        imgHienMKMoi2 = findViewById(R.id.imgHienMKMoi2);
        btnDoiMK = findViewById(R.id.btnDoiMatKhau);

        ImageButton imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> finish());

        // Hiện mật khẩu
        imgHienMkCu.setOnClickListener(v -> {
            if(IsPassWord){
                edtPassWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imgHienMkCu.setImageResource(R.drawable.ic_anmk);
            }
            else {
                edtPassWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imgHienMkCu.setImageResource(R.drawable.ic_hienmk);
            }
            IsPassWord = !IsPassWord;
            edtPassWord.setSelection(edtPassWord.getText().length());

        });
        imgHienMKMoi.setOnClickListener(v -> {
            if(IsPassWord){
                edtPassWordMoi.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imgHienMKMoi.setImageResource(R.drawable.ic_anmk);
            }
            else {
                edtPassWordMoi.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imgHienMKMoi.setImageResource(R.drawable.ic_hienmk);
            }
            IsPassWord = !IsPassWord;
            edtPassWordMoi.setSelection(edtPassWordMoi.getText().length());
        });
        imgHienMKMoi2.setOnClickListener(v -> {
            if(IsPassWord){
                edtNhapLaiPassWordMoi.setTransformationMethod(PasswordTransformationMethod.getInstance());
                imgHienMKMoi2.setImageResource(R.drawable.ic_anmk);
            }
            else {
                edtNhapLaiPassWordMoi.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                imgHienMKMoi2.setImageResource(R.drawable.ic_hienmk);
            }
            IsPassWord = !IsPassWord;
            edtNhapLaiPassWordMoi.setSelection(edtNhapLaiPassWordMoi.getText().length());
        });
        db = new DatabaseHelper(this);
        preferences = getSharedPreferences("USER_SESSION", MODE_PRIVATE);
        int matk = preferences.getInt("MaTK", -1);
        btnDoiMK.setOnClickListener(v -> {
            DoiMatKhau();
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
    private boolean validateDoiMatKhau() {

        String mkCu = edtPassWord.getText().toString().trim();
        String mkMoi = edtPassWordMoi.getText().toString().trim();
        String nhapLai = edtNhapLaiPassWordMoi.getText().toString().trim();

        if (mkCu.isEmpty()) {
           edtPassWord.setError("Vui lòng nhập mật khẩu cũ");
            edtPassWord.requestFocus();
            return false;
        }

        if (mkMoi.isEmpty()) {
            edtPassWordMoi.setError("Vui lòng nhập mật khẩu mới");
            edtPassWordMoi.requestFocus();
            return false;
        }

        if (mkMoi.length() < 6) {
            edtPassWordMoi.setError("Mật khẩu phải có ít nhất 6 ký tự");
            edtPassWordMoi.requestFocus();
            return false;
        }

        if (mkCu.equals(mkMoi)) {
            edtPassWordMoi.setError("Mật khẩu mới phải khác mật khẩu cũ");
            edtPassWordMoi.requestFocus();
            return false;
        }

        if (nhapLai.isEmpty()) {
            edtNhapLaiPassWordMoi.setError("Vui lòng nhập lại mật khẩu");
            edtNhapLaiPassWordMoi.requestFocus();
            return false;
        }

        if (!mkMoi.equals(nhapLai)) {
            edtNhapLaiPassWordMoi.setError("Mật khẩu nhập lại không khớp");
            edtNhapLaiPassWordMoi.requestFocus();
            return false;
        }

        return true;
    }
    private void DoiMatKhau() {

        if (!validateDoiMatKhau()) {
            return;
        }

        SharedPreferences preferences =
                getSharedPreferences("USER_SESSION", MODE_PRIVATE);

        int maTK = preferences.getInt("MaTK", -1);

        String mkCu = edtPassWord.getText().toString().trim();
        String mkMoi = edtPassWordMoi.getText().toString().trim();

        if (!db.checkMatKhau(maTK, mkCu)) {

            edtPassWord.setError("Mật khẩu cũ không đúng");
            edtPassWord.requestFocus();
            return;
        }

        boolean kq = db.doiMatKhau(maTK, mkMoi);

        if (kq) {

            Toast.makeText(this,
                    "Đổi mật khẩu thành công",
                    Toast.LENGTH_SHORT).show();

            finish();

        } else {

            Toast.makeText(this,
                    "Đổi mật khẩu thất bại",
                    Toast.LENGTH_SHORT).show();
        }

    }
}