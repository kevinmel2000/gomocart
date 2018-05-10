package gomocart.application.com.model;

import java.io.Serializable;

public class cekorder implements Serializable {

    private static final long serialVersionUID = 1L;
    private int _id, id, qty;
    private String ukuran, warna, message, status;


    public cekorder(int _id, int id, String ukuran, String warna, int qty, String message, String status) {
        this._id = _id;
        this.id = id;
        this.ukuran = ukuran;
        this.warna = warna;
        this.qty = qty;
        this.message = message;
        this.status = status;
    }

    public int get_id() {
        return this._id;
    }

    public int getId() {
        return this.id;
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

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }


}
