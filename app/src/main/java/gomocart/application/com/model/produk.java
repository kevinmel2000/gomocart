package gomocart.application.com.model;

import java.io.Serializable;
import java.util.ArrayList;

public class produk implements Serializable {

	private static final long serialVersionUID = 1L;

	int _id;
	int id, id_category, qty, max_qty;
	String kode, nama, category_name, penjelasan, foto1_produk, list_ukuran, ukuran, list_warna, warna;
	Boolean is_wishlist;
	int berat;

	int produk_promo, produk_featured, produk_terbaru, produk_preorder, produk_soldout, produk_grosir, produk_freeongkir;
	int rating, total_responden, total_review;
	
	int persen_diskon;
	double harga_beli, harga_jual, harga_diskon;
	double subtotal, grandtotal;
	int minimum_pesan;

	ArrayList<stok> list_stok;

	public produk(int id, String kode, String nama, int id_category, String category_name, String penjelasan, String foto1_produk, double harga_beli, double harga_jual, double harga_diskon, int persen_diskon, int berat, String list_ukuran, String ukuran, String list_warna, String warna, int qty, int max_qty, int minimum_pesan, Boolean is_wishlist, int produk_promo, int produk_featured, int produk_terbaru, int produk_preorder, int produk_soldout, int produk_grosir, int produk_freeongkir, int rating, int total_responden, int total_review) {
		this._id = 0;
		this.id = id;
		this.kode = kode;
		this.nama = nama;
		this.id_category = id_category;
		this.category_name = category_name;
		this.penjelasan = penjelasan;
		this.foto1_produk = foto1_produk;
		this.harga_beli = harga_beli;
		this.harga_jual = harga_jual;
		this.harga_diskon = harga_diskon;
		this.persen_diskon = persen_diskon;

		this.qty = qty;
		this.berat = berat;
		this.max_qty = max_qty;
		this.list_ukuran = list_ukuran;
		this.ukuran = ukuran;
		this.list_warna = list_warna;
		this.warna = warna;
		this.minimum_pesan = minimum_pesan;
		this.is_wishlist = is_wishlist;

		this.produk_promo = produk_promo;
		this.produk_featured = produk_featured;
		this.produk_terbaru = produk_terbaru;
		this.produk_preorder = produk_preorder;
		this.produk_soldout = produk_soldout;
		this.produk_grosir = produk_grosir;
		this.produk_freeongkir = produk_freeongkir;

		this.rating = rating;
		this.total_responden = total_responden;
		this.total_review = total_review;

	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_id() {
		return this._id;
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

	public int getId_category() {
		return this.id_category;
	}

	public String getCategory_name() {
		return this.category_name;
	}

	public String getPenjelasan() {
		return this.penjelasan;
	}
	
	public String getFoto1_produk() {
		return this.foto1_produk;
	}

	public void setHarga_jual(double harga_jual) {
		this.harga_jual = harga_jual;
	}
	
	public double getHarga_jual() {
		return this.harga_jual;
	}

	public void setHarga_beli(double harga_beli) {
		this.harga_beli = harga_beli;
	}

	public double getHarga_beli() {
		return this.harga_beli;
	}

	public void setHarga_diskon(double harga_diskon) {
		this.harga_diskon = harga_diskon;
	}

	public double getHarga_diskon() {
		return this.harga_diskon;
	}

	public void setPersen_diskon(int persen_diskon) {
		this.persen_diskon = persen_diskon;
	}

	public int getPersen_diskon() {
		return this.persen_diskon;
	}

	public String getList_ukuran() {
		return this.list_ukuran;
	}

	public void setList_ukuran(String list_ukuran) {
		this.list_ukuran = list_ukuran;
	}

	public String getUkuran() {
		return this.ukuran;
	}

	public void setUkuran(String ukuran) {
		this.ukuran = ukuran;
	}

	public String getList_warna() {
		return this.list_warna;
	}

	public void setList_warna(String list_warna) {
		this.list_warna = list_warna;
	}

	public String getWarna() {
		return this.warna;
	}

	public void setWarna(String warna) {
		this.warna = warna;
	}

	public int getQty() {
		return this.qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public int getMax_qty() {
		return this.max_qty;
	}

	public int getMinimum_pesan() {
		return this.minimum_pesan;
	}

	public void setMax_qty(int max_qty) {
		this.max_qty = max_qty;
	}

	public Boolean getIs_wishlist() {
		return this.is_wishlist;
	}

	public void setIs_wishlist(boolean is_wishlist) {
		this.is_wishlist = is_wishlist;
	}

	public void setBerat(int berat) {
		this.berat = berat;
	}

	public int getBerat() {
		return this.berat;
	}

	public int getProduk_promo() {
		return this.produk_promo;
	}

	public int getProduk_featured() {
		return this.produk_featured;
	}

	public int getProduk_terbaru() {
		return this.produk_terbaru;
	}

	public int getProduk_preorder() {
		return this.produk_preorder;
	}

	public int getProduk_soldout() {
		return this.produk_soldout;
	}

	public int getProduk_grosir() {
		return this.produk_grosir;
	}

	public int getProduk_freeongkir() {
		return this.produk_freeongkir;
	}

	public int getRating() {
		return this.rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getTotal_responden() {
		return this.total_responden;
	}

	public void setTotal_responden(int total_responden) {
		this.total_responden = total_responden;
	}

	public int getTotal_review() {
		return this.total_review;
	}

	public void setTotal_review(int total_review) {
		this.total_review = total_review;
	}

	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}

	public double getSubtotal() {
		return this.subtotal;
	}
	
	public void setGrandtotal(double grandtotal) {
		this.grandtotal = grandtotal;
	}

	public double getGrandtotal() {
		return this.grandtotal;
	}

	public void setList_stok(ArrayList<stok> list_stok) {
		this.list_stok = list_stok;
	}

	public ArrayList<stok> getList_stok() {
		return this.list_stok;
	}
}
