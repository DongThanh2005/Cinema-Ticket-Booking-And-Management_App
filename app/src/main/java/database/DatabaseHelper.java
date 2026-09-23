package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import QuanLy_Movie.Movie;
import com.example.quanly_datvexemfilm.R;
import QuanLy_TaiKhoan.TaiKhoan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Cinego.db";
    private static final int DATABASE_VERSION = 22;
    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE Movie(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "name TEXT," +
                        "duration INTEGER," +
                        "status TEXT,"+
                        "poster INTEGER," +
                        "PosterUri TEXT," +

                        "ngayKhoiChieu TEXT," +

                        "theLoai TEXT," +

                        "daoDien TEXT," +

                        "dienVien TEXT," +

                        "noiDung TEXT," +

                        "kiemDuyet TEXT," +
                        "trailer TEXT)"

        );

        db.execSQL(

                "CREATE TABLE Rap("+

                        "maRap INTEGER PRIMARY KEY AUTOINCREMENT,"+

                        "tenRap TEXT,"+

                        "diaChi TEXT)"

        );

        db.execSQL(

                "CREATE TABLE PhongChieu("+

                        "maPhong INTEGER PRIMARY KEY AUTOINCREMENT,"+

                        "maRap INTEGER,"+

                        "tenPhong TEXT,"+

                        "soLuongGhe INTEGER)"

        );

        db.execSQL(

                "CREATE TABLE SuatChieu("+

                        "maSuat INTEGER PRIMARY KEY AUTOINCREMENT,"+

                        "maPhim INTEGER,"+
                        "maRap INTEGER," +

                        "maPhong TEXT,"+

                        "ngayChieu TEXT,"+

                        "gioChieu TEXT)"

        );

        db.execSQL(

                "CREATE TABLE Ghe("+

                        "maGhe INTEGER PRIMARY KEY AUTOINCREMENT,"+

                        "maSuat INTEGER,"+

                        "tenGhe TEXT,"+

                        "trangThai INTEGER)"

        );
        db.execSQL(
                "CREATE TABLE Ticket(" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "maGD TEXT," +
                        "tenPhim TEXT," +
                        "poster INTEGER," +
                        "rap TEXT," +
                        "diaChi TEXT," +
                        "ngay TEXT," +
                        "gio TEXT," +
                        "phong TEXT," +
                        "ghe TEXT," +
                        "soVe INTEGER," +
                        "tongTien INTEGER," +
                        "tenNguoiNhan TEXT," +
                        "soDienThoai TEXT," +
                        "email TEXT," +
                        "thoiGian TEXT)"
        );
        db.execSQL("CREATE TABLE TaiKhoan(" +
                "    MaTK INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    HoTen TEXT NOT NULL," +
                "    Email TEXT UNIQUE NOT NULL," +
                "    MatKhau TEXT NOT NULL," +
                "    SoDienThoai TEXT UNIQUE NOT NULL," +
                "    CCCD TEXT UNIQUE," +
                "    NgaySinh TEXT," +
                "    GioiTinh TEXT," +
                "    AnhDaiDien TEXT," +
                "    VaiTro TEXT DEFAULT 'User'," +
                "    TrangThai INTEGER DEFAULT 1," +
                "    NgayTao TEXT)");
        insertSampleMovies(db);
        insertRap(db);
        insertSuatChieu(db);
        insertAdmin(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Movie");
        db.execSQL("DROP TABLE IF EXISTS Rap");
        db.execSQL("DROP TABLE IF EXISTS PhongChieu");
        db.execSQL("DROP TABLE IF EXISTS SuatChieu");
        db.execSQL("DROP TABLE IF EXISTS Ghe");
        db.execSQL("DROP TABLE IF EXISTS Ticket");
        db.execSQL("DROP TABLE IF EXISTS TaiKhoan");
        onCreate(db);
    }
    private void insertSampleMovies(SQLiteDatabase db){

        ContentValues values = new ContentValues();

        values.put("name","Minions");
        values.put("duration",90);
        values.put("poster", R.drawable.minions);
        values.putNull("PosterUri");
        values.put("status","sap_chieu");
        values.put("ngayKhoiChieu", "05/08/2026");
        values.put("theLoai", "Hoạt Hình");
        values.put("daoDien", " Pierre Coffin");
        values.put("dienVien", "");
        values.put("noiDung", "Minions & Quái Vật là câu chuyện vừa náo loạn, vừa ngớ ngẩn nhưng “hoàn toàn có thật” về cách Minions chinh phục Hollywood, trở thành ngôi sao điện ảnh, rồi mất tất cả, vô tình thả quái vật ra khắp thế giới và sau đó phải cùng nhau hợp sức để cứu lấy hành tinh khỏi chính mớ hỗn loạn mà mình tạo ra.");
        values.put("kiemDuyet", "T16");
        values.put("trailer", "https://youtu.be/VdDuUjKx714?si=16nZ6jqRKoU7_1nq");
        db.insert("Movie",null,values);

        values.clear();

        values.put("name","Madames Thanh Sắc");
        values.put("duration",110);
        values.put("poster",R.drawable.madames_thanh_sac);
        values.putNull("PosterUri");
        values.put("status","sap_chieu");
        values.put("ngayKhoiChieu", "05/07/2026");
        values.put("theLoai", " Tâm Lý, Tình cảm, Tội phạm");
        values.put("daoDien", " Thắng Vũ");
        values.put("dienVien", "Thanh Hằng, Hồng Ánh, Lương Thế Thành");
        values.put("noiDung", "Madames Thanh Sắc xoay quanh cuộc đời của đại mỹ nhân Cầm Thanh (Thanh Hằng) và Madame Sắc (Hồng Ánh) - bà chủ vũ trường Kim Đô vô cùng giàu có và sở hữu nhiều kim cương. Dù ở dưới trướng của bà Sắc và từng bước trở thành vũ nữ đình đám nhất Sài Gòn những năm 1960, nhưng Cầm Thanh luôn muốn nổi loạn. Từ đó, hai người phụ nữ bắt đầu cuộc giằng co căng thẳng dẫn đến những sự kiện gây rúng động.");
        values.put("kiemDuyet", "T18");
        values.put("trailer", "https://youtu.be/FIoDvHBZjUs?si=_TsVz9cgm5DwgCPs");
        db.insert("Movie",null,values);

        values.clear();

        values.put("name","Hành Trình Của Moana");
        values.put("duration",120);
        values.put("poster",R.drawable.hanh_trinh_cua_moana);
        values.putNull("PosterUri");
        values.put("status","dang_chieu");
        values.put("ngayKhoiChieu", "05/06/2026");
        values.put("theLoai", "Hoạt Hình");
        values.put("daoDien", " Pierre Coffin");
        values.put("dienVien", "");
        values.put("noiDung", " Trở thành ngôi sao điện ảnh, rồi mất tất cả, vô tình thả quái vật ra khắp thế giới và sau đó phải cùng nhau hợp sức để cứu lấy hành tinh khỏi chính mớ hỗn loạn mà mình tạo ra.");
        values.put("kiemDuyet", "T16");
        values.put("trailer", "https://youtu.be/FIoDvHBZjUs?si=_TsVz9cgm5DwgCPs");
        db.insert("Movie",null,values);

        values.clear();

        values.put("name","Trường Hè 2001");
        values.put("duration",110);
        values.put("poster",R.drawable.truong_he_2001);
        values.putNull("PosterUri");
        values.put("status","dang_chieu");
        values.put("ngayKhoiChieu", "20/05/2026");
        values.put("theLoai", "Hài hước");
        values.put("daoDien", " Dužan Duong");
        values.put("dienVien", "Bùi Thế Dương, Hoang Anh Doan, Tô Tiến Tài, Lê Quỳnh Lan, Dung Nguyen");
        values.put("noiDung", "Lấy bối cảnh mùa hè năm 2001, phim theo chân Kiên - cậu thanh niên 17 tuổi từ Việt Nam trở về đoàn tụ với gia đình tại khu chợ nhộn nhịp ở thị trấn Cheb sau 10 năm xa cách. Chuyến trở về mở ra nhiều mâu thuẫn liên thế hệ, cảm giác lạc lõng và nỗi khao khát được thấu hiểu trong gia đình nhập cư.");
        values.put("kiemDuyet", "T16");
        values.put("trailer", "https://youtu.be/FIoDvHBZjUs?si=_TsVz9cgm5DwgCPs");
        db.insert("Movie",null,values);

        values.clear();

        values.put("name","SuperGirl");
        values.put("duration",120);
        values.put("poster",R.drawable.supergirl);
        values.putNull("PosterUri");
        values.put("status","sap_chieu");
        values.put("ngayKhoiChieu", "20/08/2026");
        values.put("theLoai", "Hài hước");
        values.put("daoDien", " Dužan Duong");
        values.put("dienVien", "Milly Alcock, Matthias Schoenaerts, Eve Ridley, David Krumholtz, Emily Beecham, Jason Momoa,...");
        values.put("noiDung", " “Supergirl” – bom tấn mới nhất từ DC Studios – sẽ chính thức đổ bộ các rạp chiếu toàn cầu vào mùa hè này, với Milly Alcock đảm nhận vai kép Supergirl/Kara Zor-El. Khi một kẻ thù bất ngờ và tàn nhẫn giáng đòn ngay tại nơi cô gọi là nhà, Kara Zor-El – hay còn được biết đến với cái tên Supergirl – buộc phải bắt tay với một đồng minh không ai ngờ tới, bắt đầu chuyến hành trình xuyên dải ngân hà đầy sử thi, nơi vừa là cuộc trả thù, vừa là hành trình đi tìm công lý.");
        values.put("kiemDuyet", "T13");
        values.put("trailer", "https://youtu.be/FIoDvHBZjUs?si=_TsVz9cgm5DwgCPs");
        db.insert("Movie",null,values);

        values.clear();

        values.put("name","Làm Giàu Với Ma 2");
        values.put("duration",140);
        values.put("poster",R.drawable.lam_giau_voi_ma_2_poster);
        values.putNull("PosterUri");
        values.put("status","dang_chieu");
        values.put("ngayKhoiChieu", "20/06/2026");
        values.put("theLoai", " Hành Động, Phiêu Lưu");
        values.put("daoDien", "  Craig Gillespie");
        values.put("dienVien", " Milly Alcock, Matthias Schoenaerts, Eve Ridley, David Krumholtz, Emily Beecham, Jason Momoa,...");
        values.put("noiDung", "Lấy bối cảnh mùa hè năm 2001, phim theo chân Kiên - cậu thanh niên 17 tuổi từ Việt Nam trở về đoàn tụ với gia đình tại khu chợ nhộn nhịp ở thị trấn Cheb sau 10 năm xa cách. Chuyến trở về mở ra nhiều mâu thuẫn liên thế hệ, cảm giác lạc lõng và nỗi khao khát được thấu hiểu trong gia đình nhập cư.");
        values.put("kiemDuyet", "T16");
        values.put("trailer", "https://youtu.be/FIoDvHBZjUs?si=_TsVz9cgm5DwgCPs");
        db.insert("Movie",null,values);
        values.clear();


        values.put("name","Thám Tử Kiên");
        values.put("duration",120);
        values.put("poster",R.drawable.tham_tu_kien_poster);
        values.putNull("PosterUri");
        values.put("status","dang_chieu");
        values.put("ngayKhoiChieu", "20/06/2026");
        values.put("theLoai", "Kinh Dị");
        values.put("daoDien", " Victor Vũ");
        values.put("dienVien", "Quốc Huy, Đinh Ngọc Diệp, Quốc Anh, Minh Anh, Anh Phạm,");
        values.put("noiDung", "Thám Tử Kiên là một nhân vật được yêu thích trong tác phẩm điện của ăn khách của NGƯỜI VỢ CUỐI CÙNG của Victor Vũ, Thám Tử Kiên: Kỳ Không Đầu sẽ là một phim Victor Vũ trở về với thể loại sở trường Kinh Dị - Trinh Thám sau những tác phẩm tình cảm lãng mạn trước đó.");
        values.put("kiemDuyet", "T16");
        values.put("trailer", "https://youtu.be/FIoDvHBZjUs?si=_TsVz9cgm5DwgCPs");
        db.insert("Movie",null,values);

        values.clear();


        values.put("name","Địa Đạo");
        values.put("duration",120);
        values.put("poster",R.drawable.dia_dao_poster);
        values.putNull("PosterUri");
        values.put("status","dang_chieu");
        values.put("ngayKhoiChieu", "10/06/2026");
        values.put("theLoai", "Hài hước");
        values.put("daoDien", " Dužan Duong");
        values.put("dienVien", "Bùi Thế Dương, Hoang Anh Doan, Tô Tiến Tài, Lê Quỳnh Lan, Dung Nguyen");
        values.put("noiDung", "Phim theo chân Kiên - cậu thanh niên 17 tuổi từ Việt Nam trở về đoàn tụ với gia đình tại khu chợ nhộn nhịp ở thị trấn Cheb sau 10 năm xa cách. Chuyến trở về mở ra nhiều mâu thuẫn liên thế hệ, cảm giác lạc lõng và nỗi khao khát được thấu hiểu trong gia đình nhập cư.");
        values.put("kiemDuyet", "T16");
        values.put("trailer", "https://youtu.be/FIoDvHBZjUs?si=_TsVz9cgm5DwgCPs");
        db.insert("Movie",null,values);

        values.clear();


        values.put("name","Ốc Mượn Hồn");
        values.put("duration",110);
        values.put("poster",R.drawable.oc_muon_hon_poster);
        values.putNull("PosterUri");
        values.put("status","dang_chieu");
        values.put("ngayKhoiChieu", "10/06/2026");
        values.put("theLoai", "Bí ẩn, Tâm Lý");
        values.put("daoDien", "Đinh Tuấn Vũ");
        values.put("dienVien", "V");
        values.put("noiDung", "Câu chuyện kể về Quân – một người chồng đau khổ khi vợ qua đời trong một tai nạn bất ngờ. Hạnh phúc tưởng chừng được hồi sinh khi linh hồn vợ anh \"trở về\" trong thân xác của cô đồng nghiệp, người gặp tai nạn chung với vợ Quân nhưng may mắn sống sót. Giống như những con ốc mượn hồn, họ đều bám víu - lệ thuộc vào chiếc vỏ khác để tồn tại cũng như cố lẩn tránh nỗi đau của cuộc đời. Niềm vui ngắn ngủi tan biến khi một bí mật kinh hoàng liên quan đến cái chết của vợ anh được hé lộ, đặt Quân trước lựa chọn giữa việc tiếp tục bám víu, chấp nhận chiếc vỏ của hạnh phúc tự tạo hay phanh phui sự thật kinh hoàng bên trong chính chiếc vỏ này.");
        values.put("kiemDuyet", "T16");
        values.put("trailer", "https://youtu.be/FIoDvHBZjUs?si=_TsVz9cgm5DwgCPs");
        db.insert("Movie",null,values);

        values.clear();


        values.put("name","Phí Phông");
        values.put("duration",130);
        values.put("poster",R.drawable.phi_phong_poster);
        values.putNull("PosterUri");
        values.put("status","dang_chieu");
        values.put("ngayKhoiChieu", "25/06/2026");
        values.put("theLoai", "Hồi hộp, Kinh Dị");
        values.put("daoDien", " Đỗ Quốc Trung");
        values.put("dienVien", "Kiều Minh Tuấn, Nina Nutthacha Padovan, Diệp Bảo Ngọc, Đoàn Minh Anh, NSƯT Hạnh Thuý,...");
        values.put("noiDung", "Phí Phông, loài quỷ khát máu trong truyền thuyết dân gian của đồng bào miền núi gây ám ảnh bao đời nay. Phim xoay quanh Còn (Kiều Minh Tuấn) và Dương (Minh Anh), hai pháp sư tập sự lên núi cứu người mẹ đang bị lời nguyền Phí Phông đánh gục. Cùng lúc đó, trong bản sâu cũng xảy ra nhiều cái chết ghê rợn. Mọi nghi ngờ đổ dồn về hai mẹ con Mon (Diệp Bảo Ngọc) và Lua (Nina Nutthacha), những người mang đặc tính y hệt Phí Phông. Thế nhưng, vẫn còn những bí mật động trời bị chôn vùi trong chốn rừng thiêng nước độc, cuốn hai anh em Còn và Dương vào cuộc truy lùng “Phí Phông” không hồi kết.");
        values.put("kiemDuyet", "T16");
        values.put("trailer", "https://youtu.be/FIoDvHBZjUs?si=_TsVz9cgm5DwgCPs");
        db.insert("Movie",null,values);

        values.clear();
    }
    public boolean insertMovie(
            String name,
            int duration,
            String status,
            int poster,
            String PosterUri,
            String ngayKhoiChieu,
            String theLoai,
            String daoDien,
            String dienVien,
            String noiDung,
            String kiemDuyet,
            String trailer
    ) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", name);
        values.put("duration", duration);
        values.put("status", status);
        values.put("poster", poster);
        values.put("PosterUri", PosterUri);
        values.put("ngayKhoiChieu", ngayKhoiChieu);
        values.put("theLoai", theLoai);
        values.put("daoDien", daoDien);
        values.put("dienVien", dienVien);
        values.put("noiDung", noiDung);
        values.put("kiemDuyet", kiemDuyet);
        values.put("trailer", trailer);

        long result = db.insert("Movie", null, values);

        db.close();

        return result != -1;
    }
    private void insertRap(SQLiteDatabase db) {


        ContentValues values = new ContentValues();


        values.put("tenRap", "CineGo Vincom Royal City");
        values.put("diaChi", "72A Nguyễn Trãi, Thanh Xuân, Hà Nội");
        db.insert("Rap", null, values);

        values.clear();



        values.put("tenRap", "CineGo Vincom Center Bà Triệu");
        values.put("diaChi", "191 Bà Triệu, Hai Bà Trưng, Hà Nội");
        db.insert("Rap", null, values );

        values.clear();



        values.put("tenRap","CineGo Vincom Times City");
        values.put("diaChi","458 Minh Khai, Hai Bà Trưng, Hà Nội");
        db.insert("Rap", null, values );
        values.clear();
    }
    private void insertSuatChieu(SQLiteDatabase db) {

        String[] gio = {
                "09:00",
                "10:45",
                "12:30",
                "14:15",
                "16:00",
                "18:30",
                "20:45",
                "21:45",
                "23:55"

        };
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf =
                new SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault());
        for (int ngay = 0; ngay < 15; ngay++) {
            String ngayChieu = sdf.format(calendar.getTime());
            for (int phim = 1; phim <= 10; phim++) {
                for (int rap = 1; rap <= 3; rap++) {
                    for (String g : gio) {
                        ContentValues v = new ContentValues();
                        v.put("maPhim", phim);
                        v.put("maRap", rap);
                        v.put("maPhong", "P" + rap);
                        v.put("ngayChieu", ngayChieu);
                        v.put("gioChieu", g);
                        db.insert("SuatChieu", null, v);

                    }
                }
            }
            calendar.add(Calendar.DATE, 1);
        }
    }
    public boolean insertTaiKhoan(TaiKhoan tk) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("HoTen", tk.getHoTen());
        values.put("Email", tk.getEmail());
        values.put("MatKhau", tk.getMatKhau());
        values.put("SoDienThoai", tk.getSoDienThoai());
        values.put("CCCD", tk.getCCCD());
        values.put("NgaySinh", tk.getNgaySinh());
        values.put("GioiTinh", tk.getGioiTinh());
        values.put("AnhDaiDien", tk.getAnhDaiDien());
        values.put("VaiTro", tk.getVaiTro());
        values.put("TrangThai", tk.getTrangThai());
        values.put("NgayTao", tk.getNgayTao());

        long result = db.insert("TaiKhoan", null, values);

        db.close();

        return result != -1;
    }

    public void insertAdmin(SQLiteDatabase db) {

        ContentValues values = new ContentValues();

        values.put("HoTen", "Admin");
        values.put("Email", "admin@gmail.com");
        values.put("MatKhau", "123456");
        values.put("SoDienThoai", "0776691093");
        values.put("VaiTro", "Admin");
        values.put("TrangThai", 1);
        values.put("NgayTao", "07/07/2026");

        db.insert("TaiKhoan", null, values);
        values.clear();
    }
    private boolean isExist(String column, String value) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT 1 FROM TaiKhoan WHERE " + column + " = ?",
                new String[]{value}
        );

        boolean tonTai = cursor.moveToFirst();

        cursor.close();
        db.close();

        return tonTai;
    }
    public boolean checkEmail(String email) {
        return isExist("Email", email);
    }

    public boolean checkSoDienThoai(String sdt) {
        return isExist("SoDienThoai", sdt);
    }

    public boolean checkCCCD(String cccd) {
        return isExist("CCCD", cccd);
    }
    public TaiKhoan Login(String TaiKhoan , String MatKhau){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "SELECT * FROM TaiKhoan WHERE (Email = ? OR SoDienThoai = ?)" +
                                         " AND MatKhau = ?",
                            new String[]{TaiKhoan, TaiKhoan, MatKhau});
        TaiKhoan tk = null;
        if(cursor.moveToFirst()){
            tk = new TaiKhoan();
            tk.setMaTK(cursor.getInt(cursor.getColumnIndexOrThrow("MaTK")));
            tk.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
            tk.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("Email")));
            tk.setSoDienThoai(cursor.getString(cursor.getColumnIndexOrThrow("SoDienThoai")));
            tk.setMatKhau(cursor.getString(cursor.getColumnIndexOrThrow("MatKhau")));
            tk.setCCCD(cursor.getString(cursor.getColumnIndexOrThrow("CCCD")));
            tk.setNgaySinh(cursor.getString(cursor.getColumnIndexOrThrow("NgaySinh")));
            tk.setGioiTinh(cursor.getString(cursor.getColumnIndexOrThrow("GioiTinh")));
            tk.setAnhDaiDien(cursor.getString(cursor.getColumnIndexOrThrow("AnhDaiDien")));
            tk.setVaiTro(cursor.getString(cursor.getColumnIndexOrThrow("VaiTro")));
            tk.setTrangThai(cursor.getInt(cursor.getColumnIndexOrThrow("TrangThai")));
            tk.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow("NgayTao")));
        }
        cursor.close();
        db.close();
        return tk;
    }
    public TaiKhoan getTaiKhoanById(int maTK) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM TaiKhoan WHERE MaTK = ?",
                new String[]{String.valueOf(maTK)}
        );

        TaiKhoan tk = null;

        if (cursor.moveToFirst()) {

            tk = new TaiKhoan();

            tk.setMaTK(cursor.getInt(cursor.getColumnIndexOrThrow("MaTK")));
            tk.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow("HoTen")));
            tk.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("Email")));
            tk.setMatKhau(cursor.getString(cursor.getColumnIndexOrThrow("MatKhau")));
            tk.setSoDienThoai(cursor.getString(cursor.getColumnIndexOrThrow("SoDienThoai")));
            tk.setCCCD(cursor.getString(cursor.getColumnIndexOrThrow("CCCD")));
            tk.setNgaySinh(cursor.getString(cursor.getColumnIndexOrThrow("NgaySinh")));
            tk.setGioiTinh(cursor.getString(cursor.getColumnIndexOrThrow("GioiTinh")));
            tk.setAnhDaiDien(cursor.getString(cursor.getColumnIndexOrThrow("AnhDaiDien")));
            tk.setVaiTro(cursor.getString(cursor.getColumnIndexOrThrow("VaiTro")));
            tk.setTrangThai(cursor.getInt(cursor.getColumnIndexOrThrow("TrangThai")));
            tk.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow("NgayTao")));
        }

        cursor.close();
        db.close();

        return tk;
    }
    public boolean checkMatKhau(int maTK, String matKhau) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM TaiKhoan WHERE MaTK = ? AND MatKhau = ?",
                new String[]{
                        String.valueOf(maTK),
                        matKhau
                });

        boolean result = cursor.moveToFirst();

        cursor.close();
        db.close();

        return result;
    }
    public boolean doiMatKhau(int maTK, String matKhauMoi) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("MatKhau", matKhauMoi);

        int row = db.update(
                "TaiKhoan",
                values,
                "MaTK = ?",
                new String[]{String.valueOf(maTK)}
        );

        db.close();

        return row > 0;
    }
    public ArrayList<Movie> getAllMovie() {

        ArrayList<Movie> list = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Movie", null);

        while (cursor.moveToNext()) {

            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            int duration = cursor.getInt(cursor.getColumnIndexOrThrow("duration"));
            int poster = cursor.getInt(cursor.getColumnIndexOrThrow("poster"));
            String PosterUri = cursor.getString(cursor.getColumnIndexOrThrow("PosterUri"));
            String ngayKhoiChieu = cursor.getString(cursor.getColumnIndexOrThrow("ngayKhoiChieu"));
            String theLoai = cursor.getString(cursor.getColumnIndexOrThrow("theLoai"));
            String daoDien = cursor.getString(cursor.getColumnIndexOrThrow("daoDien"));
            String dienVien = cursor.getString(cursor.getColumnIndexOrThrow("dienVien"));
            String noiDung = cursor.getString(cursor.getColumnIndexOrThrow("noiDung"));
            String kiemDuyet = cursor.getString(cursor.getColumnIndexOrThrow("kiemDuyet"));
            String trailer = cursor.getString(cursor.getColumnIndexOrThrow("trailer"));

            list.add(new Movie(
                    id,
                    name,
                    duration,
                    poster,
                    PosterUri,
                    ngayKhoiChieu,
                    theLoai,
                    daoDien,
                    dienVien,
                    noiDung,
                    kiemDuyet,
                    trailer
            ));
        }

        cursor.close();
        db.close();

        return list;
    }
    public boolean khoaTaiKhoan(int maTK) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TrangThai", 0);

        int result = db.update(
                "TaiKhoan",
                values,
                "MaTK=?",
                new String[]{String.valueOf(maTK)}
        );

        return result > 0;
    }
    public boolean moKhoaTaiKhoan(int maTK) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TrangThai", 1);

        int result = db.update(
                "TaiKhoan",
                values,
                "MaTK=?",
                new String[]{String.valueOf(maTK)}
        );

        return result > 0;
    }
    public ArrayList<TaiKhoan> getAllTaiKhoan() {

        ArrayList<TaiKhoan> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM TaiKhoan", null);

        while (c.moveToNext()) {

            TaiKhoan tk = new TaiKhoan();

            tk.setMaTK(c.getInt(0));
            tk.setHoTen(c.getString(1));
            tk.setEmail(c.getString(2));
            tk.setMatKhau(c.getString(3));
            tk.setSoDienThoai(c.getString(4));
            tk.setCCCD(c.getString(5));
            tk.setNgaySinh(c.getString(6));
            tk.setGioiTinh(c.getString(7));
            tk.setAnhDaiDien(c.getString(8));
            tk.setVaiTro(c.getString(9));
            tk.setTrangThai(c.getInt(10));
            tk.setNgayTao(c.getString(11));

            list.add(tk);
        }

        c.close();

        return list;
    }
    public Movie getMovieById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Movie WHERE id=?", new String[]{String.valueOf(id)});
        Movie movie = null;
        if(cursor.moveToFirst()){
            movie = new Movie(
                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    cursor.getString(cursor.getColumnIndexOrThrow("name")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("duration")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("poster")),
                    cursor.getString(cursor.getColumnIndexOrThrow("PosterUri")),
                    cursor.getString(cursor.getColumnIndexOrThrow("ngayKhoiChieu")),
                    cursor.getString(cursor.getColumnIndexOrThrow("theLoai")),
                    cursor.getString(cursor.getColumnIndexOrThrow("daoDien")),
                    cursor.getString(cursor.getColumnIndexOrThrow("dienVien")),
                    cursor.getString(cursor.getColumnIndexOrThrow("noiDung")),
                    cursor.getString(cursor.getColumnIndexOrThrow("kiemDuyet")),
                    cursor.getString(cursor.getColumnIndexOrThrow("trailer"))
            );

        }
        cursor.close();
        return movie;
    }
    public Boolean UpdateMovie(int id,
                               String tenPhim,
                               int thoiLuong,
                               String status,
                               int poster,
                               String posterUri,
                               String ngayKhoiChieu,
                               String theLoai,
                               String daoDien,
                               String dienVien,
                               String noiDung,
                               String kiemDuyet,
                               String trailer){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("name",tenPhim);
        values.put("duration",thoiLuong);
        values.put("Status",status);
        values.put("Poster",poster);
        values.put("PosterUri",posterUri);
        values.put("ngayKhoiChieu",ngayKhoiChieu);
        values.put("theLoai",theLoai);
        values.put("daoDien",daoDien);
        values.put("dienVien",dienVien);
        values.put("noiDung",noiDung);
        values.put("kiemDuyet",kiemDuyet);
        values.put("trailer",trailer);

        return db.update(
                "Movie",
                values,
                "id=?",
                new String[]{String.valueOf(id)}
        )>0;

    }
    public boolean deleteMovie(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(
                "Movie",
                "id=?",
                new String[]{String.valueOf(id)}
        ) > 0;
    }
}
