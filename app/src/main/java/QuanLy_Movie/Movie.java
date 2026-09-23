package QuanLy_Movie;

import java.io.Serializable;

public class Movie implements Serializable {


    private int id;

    private String name;

    private int duration;

    private int poster;
    private String PosterUri;


    private String ngayKhoiChieu;

    private String theLoai;

    private String daoDien;

    private String dienVien;

    private String noiDung;

    private String kiemDuyet;

    private String trailer;



    public Movie(

            int id,

            String name,

            int duration,

            int poster,
            String PosterUri,

            String ngayKhoiChieu,

            String theLoai,

            String daoDien,

            String dienVien,

            String noiDung,

            String kiemDuyet,

            String trailer

    ){


        this.id = id;

        this.name = name;

        this.duration = duration;

        this.poster = poster;
        this.PosterUri = PosterUri;


        this.ngayKhoiChieu = ngayKhoiChieu;

        this.theLoai = theLoai;

        this.daoDien = daoDien;

        this.dienVien = dienVien;

        this.noiDung = noiDung;

        this.kiemDuyet = kiemDuyet;
        this.trailer = trailer;


    }
    public int getId(){

        return id;

    }
    public String getName(){

        return name;

    }
    public int getDuration(){

        return duration;

    }
    public int getPoster(){

        return poster;

    }
    public String getPosterUri(){
        return PosterUri;
    }
    public String getNgayKhoiChieu(){

        return ngayKhoiChieu;

    }
    public String getTheLoai(){

        return theLoai;

    }
    public String getDaoDien(){

        return daoDien;

    }
    public String getDienVien(){

        return dienVien;

    }
    public String getNoiDung(){

        return noiDung;

    }
    public String getKiemDuyet(){

        return kiemDuyet;

    }
    public String getTrailer(){

        return trailer;


    }
}
