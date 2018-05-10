package gomocart.application.com.model;

import java.io.Serializable;

/**
 * Created by apple on 15/03/16.
 */
public class ukuran implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String ukuran, logo;
    boolean checked;

    public ukuran(int id, String ukuran, String logo) {
        this.id = id;
        this.ukuran = ukuran;
        this.logo = logo;
        this.checked = false;
    }

    public int getId() {
        return this.id;
    }

    public String getUkuran() {
        return this.ukuran;
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
