package gomocart.application.com.model;

import java.io.Serializable;
/**
 * Created by apple on 21/05/16.
 */
public class order implements Serializable {

    private static final long serialVersionUID = 1L;

    private String no_transaksi;
    private String tgl_transaksi;
    private String nama;
    private int qty;
    private double jumlah;
    private String estimasi;
    private String kurir;
    private String noresi;
    private int status;
    private String gambar;
    private int user_id;
    private int pembayaran;

    public order(String no_transaksi, String tgl_transaksi, int pembayaran, String nama, int qty, double jumlah, String estimasi, String kurir, String noresi, String gambar, int status, int user_id) {
        this.no_transaksi = no_transaksi;
        this.tgl_transaksi = tgl_transaksi;
        this.pembayaran = pembayaran;
        this.nama = nama;
        this.qty = qty;
        this.jumlah = jumlah;
        this.estimasi = estimasi;
        this.kurir = kurir;
        this.noresi = noresi;
        this.status= status;
        this.gambar = gambar;
        this.user_id = user_id;
    }

    public String getNo_transaksi() {
        return no_transaksi;
    }

    public String getTgl_transaksi() {
        return this.tgl_transaksi;
    }

    public int getPembayaran() {
        return this.pembayaran;
    }

    public String getNama() {
        return this.nama;
    }

    public int getQty() {
        return this.qty;
    }

    public double getJumlah() {
        return this.jumlah;
    }

    public String getEstimasi() {
        return this.estimasi;
    }

    public String getKurir() {
        return this.kurir;
    }

    public String getNoresi() {
        return this.noresi;
    }

    public int getStatus() {
        return this.status;
    }

    public String getGambar() {
        return this.gambar;
    }

    public int getUser_id() {
        return this.user_id;
    }

}


