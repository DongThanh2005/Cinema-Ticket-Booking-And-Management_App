package QuanLy_Movie;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_datvexemfilm.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class QuanLy_MovieAdapter extends RecyclerView.Adapter<QuanLy_MovieAdapter.MovieViewHolder> {
    Context context;
    List<Movie> ListMovie;
    public QuanLy_MovieAdapter(Context context, List<Movie> ListMovie){
        this.context = context;
        this.ListMovie = ListMovie;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie_admin,parent,false);
        return new MovieViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = ListMovie.get(position);
        if (movie.getPosterUri() != null && !movie.getPosterUri().isEmpty()) {

            try {
                holder.imgPoster.setImageURI(Uri.parse(movie.getPosterUri()));
            } catch (Exception e) {
                holder.imgPoster.setImageResource(movie.getPoster());
            }

        } else {
            holder.imgPoster.setImageResource(movie.getPoster());
        }
        holder.txtTenMovie.setText(movie.getName());
        holder.txtDaoDien.setText(movie.getDaoDien());
        holder.txtTheLoai.setText(movie.getTheLoai());
        holder.txtThoiLuong.setText(movie.getDuration() + " phút");

        holder.btnXem.setOnClickListener(v -> {
            listener.onViewClick(movie);
        });
        holder.btnSua.setOnClickListener(v -> {
            listener.onEditClick(movie);
        });
        holder.btnXoa.setOnClickListener(v -> {
            listener.onDeleteClick(movie);
        });
    }

    @Override
    public int getItemCount() {
        return ListMovie.size();
    }

    public void updateList(ArrayList<Movie> newList) {
        this.ListMovie = newList;
        notifyDataSetChanged();
    }
    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPoster;

        TextView txtTenMovie;
        TextView txtDaoDien;
        TextView txtTheLoai;
        TextView txtThoiLuong;

        MaterialButton btnXem;
        MaterialButton btnSua;
        MaterialButton btnXoa;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = itemView.findViewById(R.id.imgPosster);

            txtTenMovie = itemView.findViewById(R.id.txtTenMovie);

            txtDaoDien = itemView.findViewById(R.id.txtDaoDien);

            txtTheLoai = itemView.findViewById(R.id.txtTheLoai);

            txtThoiLuong = itemView.findViewById(R.id.txtThoiLuong);

            btnXem = itemView.findViewById(R.id.btnXem);

            btnSua = itemView.findViewById(R.id.btnSua);

            btnXoa = itemView.findViewById(R.id.btnXoa);

        }

    }
    public interface OnMovieClickListener {
        void onViewClick(Movie movie);
        void onEditClick(Movie movie);
        void onDeleteClick(Movie movie);
    }
    private OnMovieClickListener listener;

    public QuanLy_MovieAdapter(Context context,
                               List<Movie> listMovie,
                               OnMovieClickListener listener) {
        this.context = context;
        this.ListMovie = listMovie;
        this.listener = listener;
    }

}
