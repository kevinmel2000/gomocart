package gomocart.application.com.model;

import java.io.Serializable;

public class wishlist implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	int _id, id;
	String kode, nama, penjelasan, foto1_produk, harga_jual, harga_spesial, ukuran, warna;

	public wishlist(int _id, int id, String kode, String nama, String penjelasan, String foto1_produk, String harga_jual, String harga_spesial, String ukuran, String warna) {
		this._id = _id;
		this.id = id;
		this.kode = kode;
		this.nama = nama;
		this.penjelasan = penjelasan;
		this.foto1_produk = foto1_produk;
		this.harga_jual = harga_jual;
		this.harga_spesial = harga_spesial;
		this.ukuran = ukuran;
		this.warna = warna;
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

	public String getPenjelasan() {
		return this.penjelasan;
	}

	public String getFoto1_produk() {
		return this.foto1_produk;
	}

	public String getHargaJual() {
		return this.harga_jual;
	}

	public String getHargaSpesial() {
		return this.harga_spesial;
	}

	public String getUkuran() {
		return this.ukuran;
	}

	public String getWarna() {
		return this.warna;
	}

}
