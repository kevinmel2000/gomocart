package gomocart.application.com.model;

import java.io.Serializable;

public class grandtotal implements Serializable {

    private static final long serialVersionUID = 1L;

    private double total, diskon, sub_total, voucher, pengiriman, grand_total;

    public grandtotal(double total, double diskon, double sub_total, double voucher, double pengiriman, double grand_total) {
        this.total = total;
        this.diskon = diskon;
        this.sub_total = sub_total;
        this.voucher = voucher;
        this.pengiriman = pengiriman;
        this.grand_total = grand_total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getTotal() {
        return this.total;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public double getDiskon() {
        return this.diskon;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public double getSub_total() {
        return this.sub_total;
    }

    public void setVoucher(double voucher) {
        this.voucher = voucher;
    }

    public double getVoucher() {
        return this.voucher;
    }

    public void setPengiriman(double pengiriman) {
        this.pengiriman = pengiriman;
    }

    public double getPengiriman() {
        return this.pengiriman;
    }

    public void setGrand_total(double grand_total) {
        this.grand_total = grand_total;
    }

    public double getGrand_total() {
        return this.grand_total;
    }

}
