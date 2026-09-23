package QuanLy_Movie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_datvexemfilm.R;

import java.util.ArrayList;
import java.util.List;

import database.DatabaseHelper;

public class MovieManagement_Activity extends AppCompatActivity {
    private RecyclerView rvMovie;
    private QuanLy_MovieAdapter adapter;
    private ArrayList<Movie> listMovie;
    private DatabaseHelper db;
    Button btnThemPhim;
    List<Movie> fullList = new ArrayList<>();
    List<Movie> filterList = new ArrayList<>();
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie_management);

        db = new DatabaseHelper(this);
        rvMovie = findViewById(R.id.rvMovie);
        rvMovie.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuanLy_MovieAdapter(
                this,
                filterList,
                new QuanLy_MovieAdapter.OnMovieClickListener() {

                    @Override
                    public void onViewClick(Movie movie) {

                        Intent intent =
                                new Intent(
                                        MovieManagement_Activity.this,
                                        Movie_Detail.class
                                );

                        intent.putExtra("movie_id", movie.getId());

                        startActivity(intent);
                    }

                    @Override
                    public void onEditClick(Movie movie) {

                        Intent intent =
                                new Intent(
                                        MovieManagement_Activity.this,
                                        EditMovie_Activity.class
                                );

                        intent.putExtra("movie_id", movie.getId());

                        movieLauncher.launch(intent);
                    }

                    @Override
                    public void onDeleteClick(Movie movie) {

                        showDeleteDialog(movie);

                    }
                }
        );
        rvMovie.setAdapter(adapter);
        btnThemPhim = findViewById(R.id.btnThemPhim);
        imgBack = findViewById(R.id.imgBack);

        btnThemPhim.setOnClickListener(v -> {
            Intent intent = new Intent(MovieManagement_Activity.this, AddMovie_Activity.class);
            movieLauncher.launch(intent);
        });

        android.widget.ImageButton imgBack = findViewById(R.id.imgBack);
        if (imgBack != null) {
            imgBack.setOnClickListener(v -> finish());
        }
        SearchView searchPhim = findViewById(R.id.searchPhim);
        if (searchPhim != null) {
            // Fix text color for better visibility - using identifier lookup to avoid R resolution issues
            int searchTextId = getResources().getIdentifier("search_src_text", "id", getPackageName());
            if (searchTextId == 0) {
                // Try appcompat package if not found in app package
                searchTextId = getResources().getIdentifier("search_src_text", "id", "androidx.appcompat");
            }

            TextView textView = searchPhim.findViewById(searchTextId);
            if (textView != null) {
                textView.setTextColor(Color.BLACK);
                textView.setHintTextColor(Color.GRAY);
            }

            // Fix icons color safely
            int searchIconId = getResources().getIdentifier("search_mag_icon", "id", getPackageName());
            if (searchIconId == 0) searchIconId = getResources().getIdentifier("search_mag_icon", "id", "androidx.appcompat");
            ImageView searchIcon = searchPhim.findViewById(searchIconId);
            if (searchIcon != null) {
                searchIcon.setColorFilter(Color.BLACK);
            }

            int closeIconId = getResources().getIdentifier("search_close_btn", "id", getPackageName());
            if (closeIconId == 0) closeIconId = getResources().getIdentifier("search_close_btn", "id", "androidx.appcompat");
            ImageView closeIcon = searchPhim.findViewById(closeIconId);
            if (closeIcon != null) {
                closeIcon.setColorFilter(Color.BLACK);
            }

        }
        initSearch();
        loadMovies();
    }
    private void showDeleteDialog(Movie movie){
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Xóa phim")
                .setMessage(
                        "Bạn có chắc muốn xóa phim\n\n"
                                + movie.getName() + " ?"
                )
                .setPositiveButton("Xóa", (dialog, which) -> {
                    boolean check =
                            db.deleteMovie(movie.getId());
                    if(check){
                        Toast.makeText(
                                this,
                                "Xóa thành công",
                                Toast.LENGTH_SHORT
                        ).show();
                        loadMovies();
                    }else{
                        Toast.makeText(
                                this,
                                "Xóa thất bại",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void filterMovies(String text) {

        filterList.clear();

        if (text.isEmpty()) {
            filterList.addAll(fullList);
        } else {
            for (Movie m : fullList) {
                if (m.getName().toLowerCase().contains(text.toLowerCase())) {
                    filterList.add(m);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
    private void loadMovies() {
        fullList.clear();
        fullList.addAll(db.getAllMovie());

        filterList.clear();
        filterList.addAll(fullList);

        adapter.notifyDataSetChanged();
    }
    private final ActivityResultLauncher<Intent> movieLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        loadMovies();
                    });
    private void initSearch() {

        SearchView searchPhim = findViewById(R.id.searchPhim);

        searchPhim.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterMovies(newText);
                return true;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText || v instanceof SearchView || (v != null && v.getClass().getName().contains("SearchView"))) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}