package gomocart.application.com.model;

import java.io.Serializable;

public class metodebayar implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String nama;

    public metodebayar(int id, String nama) {
        this.id = id;
        this.nama = nama;
    }

    public int getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

}
