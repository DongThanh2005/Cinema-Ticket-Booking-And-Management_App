package QuanLy_Movie;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly_datvexemfilm.R;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Context context;
    private ArrayList<Movie> movieList;
    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        this.context = context;

        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(
                        R.layout.item_movie_admin,
                        parent,
                        false
                );
        return new MovieViewHolder(view);
    }
    @Override
    public void onBindViewHolder(
            @NonNull MovieViewHolder holder,
            int position) {
        Movie movie = movieList.get(position);
        Log.d("POSTER", "Poster ID = " + movie.getPoster());

        holder.imgMovie.setImageResource(
                movie.getPoster()
        );
        holder.txtName.setText(
                movie.getName()
        );
        holder.txtDuration.setText(
                movie.getDuration()
                        + " phút"
        );
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(
                    context,
                    Movie_Detail.class
            );
            intent.putExtra(
                    "movie",
                    movie
            );
            context.startActivity(intent);
        });
    }
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMovie;
        TextView txtName;
        TextView txtDuration;
        public MovieViewHolder(@NonNull View itemView) {

            super(itemView);

            imgMovie = itemView.findViewById(
                    R.id.imgQlyLichChieu
            );
            txtName = itemView.findViewById(
                    R.id.txtName
            );
            txtDuration = itemView.findViewById(
                    R.id.txtDaoDien
            );
        }
    }
}
