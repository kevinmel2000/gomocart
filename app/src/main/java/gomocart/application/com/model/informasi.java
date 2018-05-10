package gomocart.application.com.model;

import java.io.Serializable;

/**
 * Created by apple on 21/05/16.
 */
public class informasi implements Serializable {

    private static final long serialVersionUID = 1L;

    private int id;
    private String tanggal;
    private String judul;
    private String header;
    private String konten;
    private String gambar;

    public informasi(int id, String tanggal, String judul, String header,  String konten, String gambar) {
        this.id = id;
        this.tanggal = tanggal;
        this.judul = judul;
        this.header  = header;
        this.konten = konten;
        this.gambar = gambar;
    }

    public int getId() {
        return this.id;
    }

    public String getTanggal() {
        return this.tanggal;
    }

    public String getHeader() {
        return this.header;
    }

    public String getJudul() {
        return this.judul;
    }

    public String getKonten() {
        return this.konten;
    }

    public String getGambar() {
        return this.gambar;
    }

}


