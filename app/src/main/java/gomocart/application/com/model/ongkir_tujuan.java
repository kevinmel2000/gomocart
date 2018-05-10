package gomocart.application.com.model;

import java.io.Serializable;

public class ongkir_tujuan implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nama;
    private Boolean is_last;

    public ongkir_tujuan(String nama, Boolean is_last) {
        this.nama = nama;
        this.is_last = is_last;
    }

    public String getNama() {
        return this.nama;
    }

    public Boolean getIs_last() {
        return this.is_last;
    }

}
