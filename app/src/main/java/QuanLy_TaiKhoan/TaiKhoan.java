package QuanLy_TaiKhoan;

public class TaiKhoan {
    private int MaTK;
    private String HoTen;
    private String Email;
    private String MatKhau;
    private String SoDienThoai;
    private String CCCD;
    private String NgaySinh;
    private String GioiTinh;
    private String AnhDaiDien;
    private String VaiTro;
    private int TrangThai;
    private String NgayTao;

    public TaiKhoan(int MaTK, String HoTen, String Email, String MatKhau, String SoDienThoai,
                    String CCCD, String NgaySinh, String GioiTinh, String AnhDaiDien, String VaiTro,
                    int TrangThai, String NgayTao){
        this.AnhDaiDien = AnhDaiDien;
        this.MaTK = MaTK;
        this.HoTen = HoTen;
        this.GioiTinh = GioiTinh;
        this.Email = Email;
        this.MatKhau = MatKhau;
        this.SoDienThoai = SoDienThoai;
        this.CCCD = CCCD;
        this.NgaySinh = NgaySinh;
        this.VaiTro = VaiTro;
        this.TrangThai = TrangThai;
        this.NgayTao = NgayTao;
    }
    public TaiKhoan(){

    }

    public int getMaTK() {
        return MaTK;
    }

    public void setMaTK(int maTK) {
        MaTK = maTK;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public String getSoDienThoai() {
        return SoDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        SoDienThoai = soDienThoai;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        GioiTinh = gioiTinh;
    }

    public String getAnhDaiDien() {
        return AnhDaiDien;
    }

    public void setAnhDaiDien(String anhDaiDien) {
        AnhDaiDien = anhDaiDien;
    }

    public String getVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(String vaiTro) {
        VaiTro = vaiTro;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int trangThai) {
        TrangThai = trangThai;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String ngayTao) {
        NgayTao = ngayTao;
    }
}
