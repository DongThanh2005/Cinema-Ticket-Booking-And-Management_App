package QuanLy_Movie;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.quanly_datvexemfilm.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import database.DatabaseHelper;

public class AddMovie_Activity extends AppCompatActivity {

    CardView cardPoster;
    DatabaseHelper db;
    LinearLayout layoutPlaceholder;
    ImageView imgPoster;
    Button btnLuuPhim;
    Uri selectedImageUri;
    EditText edtTuoi, edtTheLoai, edtTenPhim, edtNgayKhoiChieu, edtThoiLuong, edtDaoDien,edtDienVien, edtMotaPhim,edtTrailer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_movie);
        edtTuoi = findViewById(R.id.edtTuoi);
        edtTenPhim = findViewById(R.id.edtTenPhim);
        edtMotaPhim = findViewById(R.id.edtMoTaPhim);
        edtDaoDien = findViewById(R.id.edtDaoDien);
        edtDienVien = findViewById(R.id.edtDienVien);
        edtThoiLuong = findViewById(R.id.edtThoiLuong);
        edtTheLoai = findViewById(R.id.edtTheLoai);
        edtTrailer = findViewById(R.id.edtTrailer);
        btnLuuPhim = findViewById(R.id.btnLuuPhim);
        cardPoster = findViewById(R.id.cardPoster);
        imgPoster = findViewById(R.id.imgPoster);
        edtNgayKhoiChieu = findViewById(R.id.edtNgayKhoiChieu);
        layoutPlaceholder = findViewById(R.id.layoutPlaceholder);
        edtNgayKhoiChieu.setOnClickListener(v -> LayNgay());
        ImageButton imgback = findViewById(R.id.imgBack);
        imgback.setOnClickListener(v -> finish());
        db = new DatabaseHelper(this);

        View.OnClickListener pickImageListener =
                v -> pickImage.launch(new String[]{"image/*"});
        cardPoster.setOnClickListener(pickImageListener);
        imgPoster.setOnClickListener(pickImageListener);
        btnLuuPhim.setOnClickListener(v -> {
            String tenPhim = edtTenPhim.getText().toString().trim();
            String kiemDuyet = edtTuoi.getText().toString().trim();
            String theLoai = edtTheLoai.getText().toString().trim();
            String ngayKhoiChieu = edtNgayKhoiChieu.getText().toString().trim();
            String daoDien = edtDaoDien.getText().toString().trim();
            String dienVien = edtDienVien.getText().toString().trim();
            String noiDung = edtMotaPhim.getText().toString().trim();
            String trailer = edtTrailer.getText().toString().trim();
            String ThoiLuong = edtThoiLuong.getText().toString().trim();
            if (tenPhim.isEmpty()
                    || kiemDuyet.isEmpty()
                    || theLoai.isEmpty()
                    || ngayKhoiChieu.isEmpty()
                    || ThoiLuong.isEmpty()
                    || daoDien.isEmpty()
                    || noiDung.isEmpty()
                    || dienVien.isEmpty()
                    || trailer.isEmpty()) {

                Toast.makeText(this,
                        "Vui lòng nhập đầy đủ thông tin!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (!Patterns.WEB_URL.matcher(trailer).matches()) {
                edtTrailer.setError("Link không hợp lệ");
                return;
            }
            if(selectedImageUri == null){
                Toast.makeText(this,
                        "Vui lòng chọn poster!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            int thoiluong = Integer.parseInt(ThoiLuong);
            String posterPath = saveImageToInternalStorage(selectedImageUri);
             String status = getStatusFromDate(ngayKhoiChieu);
             Boolean check = db.insertMovie(tenPhim,
                     thoiluong,
                     status,
                     0,
                     posterPath,
                     ngayKhoiChieu,
                     theLoai,
                     daoDien,
                     dienVien,
                     noiDung,
                     kiemDuyet,
                     trailer);
             if(check){
                 Toast.makeText(this,"Thêm phim thành công!", Toast.LENGTH_SHORT).show();
                 setResult(RESULT_OK);
                 finishAffinity();
             }
             else {
                 Toast.makeText(this,"Thêm phim không thành công!",Toast.LENGTH_SHORT).show();
             }

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
    private void LayNgay() {

        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                AddMovie_Activity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {

                    String date = String.format("%02d/%02d/%04d",
                            selectedDay,
                            selectedMonth + 1,
                            selectedYear);
                    edtNgayKhoiChieu.setText(date);
                },
                year,
                month,
                day
        );

        datePickerDialog.show();
    }
    private final ActivityResultLauncher<String[]> pickImage =
            registerForActivityResult(
                    new ActivityResultContracts.OpenDocument(),
                    uri -> {
                        if (uri != null) {

                            getContentResolver().takePersistableUriPermission(
                                    uri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );

                            selectedImageUri = uri;
                            imgPoster.setImageURI(uri);
                            layoutPlaceholder.setVisibility(View.GONE);
                        }
                    });
    private String getStatusFromDate(String ngayKhoiChieu) {

        SimpleDateFormat sdf =
                new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        try {

            Date ngayKC = sdf.parse(ngayKhoiChieu);
            Date homNay = new Date();

            if (homNay.before(ngayKC)) {
                return "sap_chieu";
            } else {
                return "dang_chieu";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "sap_chieu";
    }
    private void clearForm() {

        edtTenPhim.setText("");
        edtTuoi.setText("");
        edtTheLoai.setText("");
        edtNgayKhoiChieu.setText("");
        edtThoiLuong.setText("");
        edtDaoDien.setText("");
        edtDienVien.setText("");
        edtMotaPhim.setText("");
        edtTrailer.setText("");

        selectedImageUri = null;

        imgPoster.setImageDrawable(null);

        layoutPlaceholder.setVisibility(View.VISIBLE);

        edtTenPhim.requestFocus();
    }
    private String saveImageToInternalStorage(Uri uri) {

        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            String fileName = "poster_" + System.currentTimeMillis() + ".jpg";

            File file = new File(getFilesDir(), fileName);

            FileOutputStream fos = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);

            fos.flush();
            fos.close();

            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}