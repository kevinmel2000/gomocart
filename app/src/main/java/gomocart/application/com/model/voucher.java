package gomocart.application.com.model;

import java.io.Serializable;

public class voucher implements Serializable {

    private static final long serialVersionUID = 1L;
    private String kode, tipe_voucher, jenis_voucher;
    private double nominal;

    public voucher(String kode, double nominal, String tipe_voucher, String jenis_voucher) {
        this.kode = kode;
        this.nominal = nominal;
        this.jenis_voucher = jenis_voucher;
        this.tipe_voucher = tipe_voucher;
    }

    public String getKode() {
        return this.kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public double getNominal() {
        return this.nominal;
    }

    public void setNominal(double nominal) {
        this.nominal = nominal;
    }

    public String getTipe_voucher() {
        return this.tipe_voucher;
    }

    public void setTipe_voucher(String tipe_voucher) {
        this.tipe_voucher = tipe_voucher;
    }

    public String getJenis_voucher() {
        return this.jenis_voucher;
    }

    public void setJenis_voucher(String jenis_voucher) {
        this.jenis_voucher = jenis_voucher;
    }
}
