package gomocart.application.com.model;

import java.io.Serializable;

public class subdistrict implements Serializable {

    private static final long serialVersionUID = 1L;
    private int subdistrict_id, city_id;
    private String subdistrict;

    public subdistrict(int subdistrict_id, int city_id, String subdistrict) {
        this.subdistrict_id = subdistrict_id;
        this.city_id = city_id;
        this.subdistrict = subdistrict;
    }

    public int getSubdistrict_id() {
        return this.subdistrict_id;
    }

    public int getCity_id() {
        return this.city_id;
    }

    public String getSubdistrict() {
        return this.subdistrict;
    }

}
