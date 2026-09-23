package QuanLy_TaiKhoan;

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

public class QuanLy_TaiKhoanAdapter extends RecyclerView.Adapter<QuanLy_TaiKhoanAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TaiKhoan> list;
    private OnItemClickListener listener;

    public QuanLy_TaiKhoanAdapter(Context context,
                           ArrayList<TaiKhoan> list,
                           OnItemClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_taikhoan, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuanLy_TaiKhoanAdapter.ViewHolder holder, int position) {
        TaiKhoan tk = list.get(position);
        holder.txtHoTen.setText(tk.getHoTen());
        holder.txtTaiKhoan.setText(tk.getEmail());
        holder.txtVaiTro.setText(tk.getVaiTro());
        if (tk.getTrangThai() == 1) {
            holder.txtTrangThai.setText("Hoạt động");
            holder.btnKhoa.setText("Khóa");
        } else {
            holder.txtTrangThai.setText("Đã khóa");
            holder.btnKhoa.setText("Mở");
        }
        if (tk.getAnhDaiDien() != null && !tk.getAnhDaiDien().isEmpty()) {

            try {
                holder.imgAnhDaiDien.setImageURI(Uri.parse(tk.getAnhDaiDien()));
            } catch (Exception e) {
                holder.imgAnhDaiDien.setImageResource(R.drawable.ic_profile);
            }

        } else {
            holder.imgAnhDaiDien.setImageResource(R.drawable.ic_profile);
        }
        holder.btnXem.setOnClickListener(v ->
                listener.onXem(tk));
        holder.btnSua.setOnClickListener(v ->
                listener.onSua(tk));
        holder.btnKhoa.setOnClickListener(v ->
                listener.onKhoa(tk));
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgAnhDaiDien;

        TextView txtHoTen;
        TextView txtTaiKhoan;
        TextView txtVaiTro;
        TextView txtTrangThai;

        MaterialButton btnXem;
        MaterialButton btnSua;
        MaterialButton btnKhoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgAnhDaiDien = itemView.findViewById(R.id.imgAnhDaiDien);

            txtHoTen = itemView.findViewById(R.id.txtHoTen);
            txtTaiKhoan = itemView.findViewById(R.id.txtTaiKhoan);
            txtVaiTro = itemView.findViewById(R.id.txtVaiTro);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);

            btnXem = itemView.findViewById(R.id.btnXem);
            btnSua = itemView.findViewById(R.id.btnSua);
            btnKhoa = itemView.findViewById(R.id.btnKhoa);
        }
    }
    public interface OnItemClickListener {
        void onXem(TaiKhoan taiKhoan);
        void onSua(TaiKhoan taiKhoan);
        void onKhoa(TaiKhoan taiKhoan);

    }
}

