package QuanLy_TaiKhoan;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_datvexemfilm.R;
import com.example.quanly_datvexemfilm.ThongTinCaNhan_Activity;

import java.util.ArrayList;

import database.DatabaseHelper;

public class QuanLyTaiKhoan_Activity extends AppCompatActivity {
    DatabaseHelper db;
    RecyclerView rvTaiKhoan;
    Button btnThemTaiKhoan;
    SearchView searchTaiKhoan;
    ArrayList<TaiKhoan> listtaikhoan;
    QuanLy_TaiKhoanAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_tai_khoan);
        btnThemTaiKhoan= findViewById(R.id.btnThemTaiKhoan);
        searchTaiKhoan = findViewById(R.id.searchTaiKhoan);

        ImageView imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> finish());
        // Cài màu icon
        if (searchTaiKhoan != null) {
            // Fix text color for better visibility - using identifier lookup to avoid R resolution issues
            int searchTextId = getResources().getIdentifier("search_src_text", "id", getPackageName());
            if (searchTextId == 0) {
                // Try appcompat package if not found in app package
                searchTextId = getResources().getIdentifier("search_src_text", "id", "androidx.appcompat");
            }

            TextView textView = searchTaiKhoan.findViewById(searchTextId);
            if (textView != null) {
                textView.setTextColor(Color.BLACK);
                textView.setHintTextColor(Color.GRAY);
            }

            // Fix icons color safely
            int searchIconId = getResources().getIdentifier("search_mag_icon", "id", getPackageName());
            if (searchIconId == 0) searchIconId = getResources().getIdentifier("search_mag_icon", "id", "androidx.appcompat");
            ImageView searchIcon = searchTaiKhoan.findViewById(searchIconId);
            if (searchIcon != null) {
                searchIcon.setColorFilter(Color.BLACK);
            }

            int closeIconId = getResources().getIdentifier("search_close_btn", "id", getPackageName());
            if (closeIconId == 0) closeIconId = getResources().getIdentifier("search_close_btn", "id", "androidx.appcompat");
            ImageView closeIcon = searchTaiKhoan.findViewById(closeIconId);
            if (closeIcon != null) {
                closeIcon.setColorFilter(Color.BLACK);
            }
        }
        rvTaiKhoan = findViewById(R.id.rvTaiKhoan);
        db = new DatabaseHelper(this);
        listtaikhoan = db.getAllTaiKhoan();
        adapter = new QuanLy_TaiKhoanAdapter(
                this,
                listtaikhoan,
                new QuanLy_TaiKhoanAdapter.OnItemClickListener() {
                    @Override
                    public void onXem(TaiKhoan taiKhoan) {
                        Intent intent =
                                new Intent(QuanLyTaiKhoan_Activity.this,
                                        EditTaiKhoan_Activity.class);

                        intent.putExtra("MaTK", taiKhoan.getMaTK());

                        startActivity(intent);
                    }

                    @Override
                    public void onSua(TaiKhoan taiKhoan) {
                        Intent intent =
                                new Intent(QuanLyTaiKhoan_Activity.this,
                                        ThongTinCaNhan_Activity.class);

                        intent.putExtra("MaTK", taiKhoan .getMaTK());

                        startActivity(intent);
                    }

                    @Override
                    public void onKhoa(TaiKhoan taiKhoan) {
                        String message = taiKhoan.getTrangThai() == 1
                                ? "Bạn có muốn khóa tài khoản này không?"
                                : "Bạn có muốn mở khóa tài khoản này không?";

                        new AlertDialog.Builder(QuanLyTaiKhoan_Activity.this)
                                .setTitle("Xác nhận")
                                .setMessage(message)
                                .setPositiveButton("Đồng ý", (dialog, which) -> {

                                    boolean success;

                                    if (taiKhoan.getTrangThai() == 1) {
                                        success = db.khoaTaiKhoan(taiKhoan.getMaTK());
                                    } else {
                                        success = db.moKhoaTaiKhoan(taiKhoan.getMaTK());
                                    }

                                    if (success) {
                                        Toast.makeText(QuanLyTaiKhoan_Activity.this,
                                                "Cập nhật thành công",
                                                Toast.LENGTH_SHORT).show();

                                        listtaikhoan.clear();
                                        listtaikhoan.addAll(db.getAllTaiKhoan());
                                        adapter.notifyDataSetChanged();

                                    } else {
                                        Toast.makeText(QuanLyTaiKhoan_Activity.this,
                                                "Cập nhật thất bại",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("Hủy", null)
                                .show();
                    }
                });
        rvTaiKhoan.setLayoutManager(new LinearLayoutManager(this));

        rvTaiKhoan.setAdapter(adapter);


    }
}