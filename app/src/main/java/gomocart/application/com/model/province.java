package gomocart.application.com.model;

import java.io.Serializable;

public class province implements Serializable {

    private static final long serialVersionUID = 1L;
    private int province_id;
    private String province;

    public province(int province_id, String province) {
        this.province_id = province_id;
        this.province = province;
    }

    public int getProvince_id() {
        return this.province_id;
    }

    public String getProvince() {
        return this.province;
    }

}
