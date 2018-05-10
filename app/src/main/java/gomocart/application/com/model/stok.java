package gomocart.application.com.model;

import java.io.Serializable;

public class stok implements Serializable {

    private static final long serialVersionUID = 1L;
    private String ukuran, warna;
    private int qty;

    public stok(String ukuran, String warna, int qty) {
        this.ukuran = ukuran;
        this.warna = warna;
        this.qty = qty;
    }

    public String getUkuran() {
        return this.ukuran;
    }

    public String getWarna() {
        return this.warna;
    }

    public int getQty() {
        return this.qty;
    }

}
