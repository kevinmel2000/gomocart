package gomocart.application.com.model;

import java.io.Serializable;

/**
 * Created by apple on 15/03/16.
 */
public class warna implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id;
    private String warna, logo;
    boolean checked;

    public warna(int id, String warna, String logo) {
        this.id = id;
        this.warna = warna;
        this.logo = logo;
        this.checked = false;
    }

    public int getId() {
        return this.id;
    }

    public String getWarna() {
        return this.warna;
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
