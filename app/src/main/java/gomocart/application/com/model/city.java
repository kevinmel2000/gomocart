package gomocart.application.com.model;

import java.io.Serializable;

public class city implements Serializable {

    private static final long serialVersionUID = 1L;
    private int city_id, province_id;
    private String city;

    public city(int city_id, int province_id, String city) {
        this.province_id = province_id;
        this.city_id = city_id;
        this.city = city;
    }

    public int getCity_id() {
        return this.city_id;
    }

    public int getProvince_id() {
        return this.province_id;
    }

    public String getCity() {
        return this.city;
    }

}
