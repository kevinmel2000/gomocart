package gomocart.application.com.model;

import java.io.Serializable;

public class ongkir implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id_kurir, nominal;
    private String kode_kurir, nama_kurir, kode_service, nama_service, etd, tarif, gambar;
    private boolean is_selected;

    public ongkir(int id_kurir, String kode_kurir, String nama_kurir, String kode_service, String nama_service, int nominal, String etd, String tarif, String gambar) {
        this.id_kurir = id_kurir;
        this.kode_kurir = kode_kurir;
        this.nama_kurir = nama_kurir;
        this.kode_service= kode_service;
        this.nama_service = nama_service;
        this.nominal = nominal;
        this.etd = etd;
        this.tarif = tarif;
        this.gambar = gambar;
    }

    public int getId_kurir() {
        return this.id_kurir;
    }

    public String getKode_kurir() {
        return this.kode_kurir;
    }

    public String getNama_kurir() {
        return this.nama_kurir;
    }

    public String getKode_service() {
        return this.kode_service;
    }

    public String getNama_service() {
        return this.nama_service;
    }

    public int getNominal() {
        return this.nominal;
    }

    public String getEtd() {
        return this.etd;
    }

    public String getTarif() {
        return this.tarif;
    }

    public String getGambar() {
        return this.gambar;
    }

    public boolean getIs_selected() {
        return this.is_selected;
    }

    public void setIs_selected(boolean is_selected) {
        this.is_selected=is_selected;
    }

}
