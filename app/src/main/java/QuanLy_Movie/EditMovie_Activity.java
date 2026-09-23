package QuanLy_Movie;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Date;
import java.util.Locale;

import database.DatabaseHelper;

public class EditMovie_Activity extends AppCompatActivity {
    int movieId;
    Movie movie;
    CardView cardPoster;
    DatabaseHelper db;
    LinearLayout layoutPlaceholder;
    ImageView imgPoster;
    Button btnCapNhat;
    Uri selectedImageUri;
    boolean isImageChanged = false;
    EditText edtTuoi, edtTheLoai, edtTenPhim, edtNgayKhoiChieu, edtThoiLuong, edtDaoDien,edtDienVien, edtMotaPhim,edtTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_movie);

        edtTuoi = findViewById(R.id.edtTuoi);
        edtTenPhim = findViewById(R.id.edtTenPhim);
        edtMotaPhim = findViewById(R.id.edtMoTaPhim);
        edtDaoDien = findViewById(R.id.edtDaoDien);
        edtDienVien = findViewById(R.id.edtDienVien);
        edtThoiLuong = findViewById(R.id.edtThoiLuong);
        edtTheLoai = findViewById(R.id.edtTheLoai);
        edtTrailer = findViewById(R.id.edtTrailer);
        btnCapNhat = findViewById(R.id.btnCapNhat);
        cardPoster = findViewById(R.id.cardPoster);
        imgPoster = findViewById(R.id.imgPoster);
        edtNgayKhoiChieu = findViewById(R.id.edtNgayKhoiChieu);
        layoutPlaceholder = findViewById(R.id.layoutPlaceholder);
        ImageButton imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> finish());
        db = new DatabaseHelper(this);
        movieId = getIntent().getIntExtra("movie_id",-1);
        if(movieId == -1){
            Toast.makeText(this,"Không tìm thấy phim!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        LoadMovieData(movieId);
        View.OnClickListener pickImageListener = v -> pickImage.launch(new String[]{"image/*"});

        cardPoster.setOnClickListener(pickImageListener);
        imgPoster.setOnClickListener(pickImageListener);

        btnCapNhat.setOnClickListener(v -> {
            String tenPhim=edtTenPhim.getText().toString().trim();
            String kiemDuyet=edtTuoi.getText().toString().trim();
            String theLoai=edtTheLoai.getText().toString().trim();
            String ngayKhoiChieu=edtNgayKhoiChieu.getText().toString().trim();
            String daoDien=edtDaoDien.getText().toString().trim();
            String dienVien=edtDienVien.getText().toString().trim();
            String noiDung=edtMotaPhim.getText().toString().trim();
            String trailer=edtTrailer.getText().toString().trim();
            int thoiLuong=Integer.parseInt(
                    edtThoiLuong.getText().toString()
            );
            String status = getStatusFromDate(ngayKhoiChieu);
            int poster = movie.getPoster();
            String posterPath = movie.getPosterUri();
            if (isImageChanged && selectedImageUri != null) {
                String savedPath = saveImageToInternalStorage(selectedImageUri);
                if (savedPath != null) {
                    posterPath = savedPath;
                    poster = 0;
                } else {
                    Toast.makeText(this, "Lỗi lưu ảnh!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            Boolean check = db.UpdateMovie(movieId,
                    tenPhim,
                    thoiLuong,
                    status,
                    poster,
                    posterPath,
                    ngayKhoiChieu,
                    theLoai,
                    daoDien,
                    dienVien,
                    noiDung,
                    kiemDuyet,
                    trailer);
            if(check){
                Toast.makeText(this,"Cập nhật thành công", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
            else {
                Toast.makeText(this,"Cập nhật thất bại!",Toast.LENGTH_SHORT).show();
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

                            isImageChanged = true;
                        }
                    });
    private void LoadMovieData(int movieId){
        movie = db.getMovieById(movieId);
        if(movie == null){
            Toast.makeText(this, "Không tìm thấy dữ liệu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        edtTenPhim.setText(movie.getName());
        edtTuoi.setText(movie.getKiemDuyet());
        edtTheLoai.setText(movie.getTheLoai());
        edtNgayKhoiChieu.setText(movie.getNgayKhoiChieu());
        edtThoiLuong.setText(String.valueOf(movie.getDuration()));
        edtDaoDien.setText(movie.getDaoDien());
        edtDienVien.setText(movie.getDienVien());
        edtMotaPhim.setText(movie.getNoiDung());
        edtTrailer.setText(movie.getTrailer());
        if(movie.getPosterUri() != null && !movie.getPosterUri().isEmpty()){
            try {
                selectedImageUri = Uri.parse(movie.getPosterUri());
                imgPoster.setImageURI(selectedImageUri);
                layoutPlaceholder.setVisibility(View.GONE);
            } catch (Exception e) {
                Log.e("EditMovie_Activity", "Error loading image", e);
                layoutPlaceholder.setVisibility(View.VISIBLE);
            }
        }
        else {
            imgPoster.setImageResource(movie.getPoster());
        }
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

}