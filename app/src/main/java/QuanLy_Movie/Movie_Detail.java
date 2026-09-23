package QuanLy_Movie;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanly_datvexemfilm.R;

import java.io.File;

import database.DatabaseHelper;

public class Movie_Detail extends AppCompatActivity {

    int MovieID;
    DatabaseHelper db;
    Movie movie;
    TextView txtTenPhim, txtKiemDuyet, txtTheLoai, txtNgayKhoiChieu, txtThoiLuong, txtDaoDien, txtDienVien,txtTrailer, txtMoTa;
    ImageView imgPoster;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_detail);

        txtTenPhim = findViewById(R.id.txtTenPhim);
        txtKiemDuyet = findViewById(R.id.txtKiemDuyet);
        txtTheLoai = findViewById(R.id.txtTheLoai);
        txtNgayKhoiChieu = findViewById(R.id.txtNgaykhoiChieu);
        txtThoiLuong = findViewById(R.id.txtThoiLuong);
        txtDaoDien = findViewById(R.id.txtDaoDien);
        txtDienVien = findViewById(R.id.txtDienVien);
        txtTrailer = findViewById(R.id.txtTrailer);
        txtMoTa = findViewById(R.id.txtMoTa);
        imgPoster = findViewById(R.id.imgPoster);
        db = new DatabaseHelper(this);
        MovieID = getIntent().getIntExtra("movie_id",-1);
        if(MovieID == -1){
            Toast.makeText(this,"Không tìm thấy phim!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        LoadDataFilm();


    }
    private void LoadDataFilm(){
        movie = db.getMovieById(MovieID);
        if(movie == null){
            Toast.makeText(this,"Không tìm thấy dữ liệu!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        txtTenPhim.setText(movie.getName());
        txtKiemDuyet.setText(movie.getKiemDuyet() + "Phim chỉ dành cho người có đủ "+movie.getKiemDuyet()+" tuổi trở lên");
        txtTheLoai.setText(movie.getTheLoai());
        txtNgayKhoiChieu.setText(movie.getNgayKhoiChieu());
        txtThoiLuong.setText(movie.getDuration() + " phút");
        txtDaoDien.setText(movie.getDaoDien());
        txtDienVien.setText(movie.getDienVien());
        txtTrailer.setText(movie.getTrailer());
        txtMoTa.setText(movie.getNoiDung());
        if (movie.getPosterUri() != null && !movie.getPosterUri().isEmpty()) {
            File imgFile = new File(movie.getPosterUri());
            if (imgFile.exists()) {
                imgPoster.setImageURI(Uri.fromFile(imgFile));
            } else {
                imgPoster.setImageResource(movie.getPoster());
            }
        } else {

            imgPoster.setImageResource(movie.getPoster());
        }
        txtTrailer.setText("▶ Xem Trailer");
        txtTrailer.setTextColor(Color.BLUE);

        txtTrailer.setOnClickListener(v -> {
            Intent intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(movie.getTrailer())
            );
            startActivity(intent);
        });
    }
}