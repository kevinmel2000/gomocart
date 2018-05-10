package gomocart.application.com.model;

import java.io.Serializable;

public class alamat implements Serializable {

    private static final long serialVersionUID = 1L;
    private int id, province_id, city_id, subdistrict_id;
    private String nama, alamat, province, city_name, subdistrict_name, kode_pos, no_hp, dropship_name, dropship_phone, email_notifikasi;
    boolean is_dropship, as_default;

    public alamat(int id, String nama, String alamat, int province_id, String province, int city_id, String city_name, int subdistrict_id, String subdistrict_name, String kode_pos, String no_hp, boolean as_default, boolean is_dropship, String dropship_name, String dropship_phone, String email_notifikasi) {
        this.id = id;
        this.nama = nama;
        this.alamat = alamat;
        this.province_id = province_id;
        this.province = province;
        this.city_id = city_id;
        this.city_name = city_name;
        this.subdistrict_id = subdistrict_id;
        this.subdistrict_name = subdistrict_name;
        this.kode_pos = kode_pos;
        this.no_hp = no_hp;
        this.as_default = as_default;

        this.is_dropship = is_dropship;
        this.dropship_name = dropship_name;
        this.dropship_phone = dropship_phone;
        this.email_notifikasi = email_notifikasi;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAs_default(boolean as_default) {
        this.as_default = as_default;
    }

    public int getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public String getAlamat() {
        return this.alamat;
    }

    public int getProvince_id() {
        return this.province_id;
    }

    public String getProvince() {
        return this.province;
    }

    public int getCity_id() {
        return this.city_id;
    }

    public String getCity_name() {
        return this.city_name;
    }

    public int getSubdistrict_id() {
        return this.subdistrict_id;
    }

    public String getSubdistrict_name() {
        return this.subdistrict_name;
    }

    public String getKode_pos() {
        return this.kode_pos;
    }

    public String getNo_hp() {
        return this.no_hp;
    }

    public boolean getAsDefaultAlamat() {
        return this.as_default;
    }

    public boolean getIs_dropship() {
        return this.is_dropship;
    }

    public String getDropship_name() {
        return this.dropship_name;
    }

    public String getDropship_phone() {
        return this.dropship_phone;
    }

    public String getEmail_notifikasi() {
        return this.email_notifikasi;
    }
}
