package gomocart.application.com.model;

import java.io.Serializable;

/**
 * Created by apple on 15/03/16.
 */
public class brand implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String kode, nama, logo;
    boolean checked;

    public brand(int id, String kode, String nama, String logo) {
        this.id = id;
        this.kode = kode;
        this.nama = nama;
        this.logo = logo;
        this.checked = false;
    }

    public int getId() {
        return this.id;
    }

    public String getKode() {
        return this.kode;
    }

    public String getNama() {
        return this.nama;
    }

    public String getLogo() {
        return this.logo;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean getChecked() {
        return this.checked;
    }
}
