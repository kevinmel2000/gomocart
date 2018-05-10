package gomocart.application.com.model;

import java.io.Serializable;

public class grosir implements Serializable {

    private static final long serialVersionUID = 1L;
    private int jumlah_min, jumlah_max;
    private double harga;

    public grosir(int jumlah_min, int jumlah_max, double harga) {
        this.jumlah_min = jumlah_min;
        this.jumlah_max = jumlah_max;
        this.harga = harga;
    }

    public int getJumlah_min() {
        return this.jumlah_min;
    }

    public int getJumlah_max() {
        return this.jumlah_max;
    }

    public double getHarga() {
        return this.harga;
    }

}
