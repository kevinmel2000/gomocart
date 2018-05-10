package gomocart.application.com.model;

import java.io.Serializable;

public class layanan implements Serializable {

    private static final long serialVersionUID = 1L;
    private String kode, nama, tarif, etd;
    private int nominal;

    public layanan(String kode, String nama, String tarif, int nominal, String etd) {
        this.kode = kode;
        this.nama = nama;
        this.tarif = tarif;
        this.nominal = nominal;
        this.etd = etd;
    }

    public String getKode() {
        return this.kode;
    }

    public String getNama() {
        return this.nama;
    }

    public String getTarif() {
        return this.tarif;
    }

    public int getNominal() {
        return this.nominal;
    }

    public String getEtd() {
        return this.etd;
    }

}
