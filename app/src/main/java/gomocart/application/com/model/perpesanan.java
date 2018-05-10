package gomocart.application.com.model;

import java.io.Serializable;

public class perpesanan implements Serializable {

	private static final long serialVersionUID = 1L;

	int id, id_produk, from_id, total_unread;
	String kode, nama, gambar, tanggal, pesan, from_nama, from_photo;

	public perpesanan(int id, int id_produk, String kode, String nama, String gambar, String tanggal, String pesan, int from_id, String from_nama, String from_photo, int total_unread) {
		this.id = id;
		this.id_produk = id_produk;
		this.kode = kode;
		this.nama = nama;
		this.gambar = gambar;
		this.tanggal = tanggal;
		this.pesan = pesan;
		this.from_id = from_id;
		this.from_nama = from_nama;
		this.from_photo = from_photo;
		this.total_unread = total_unread;
	}

	public int getId() {
		return this.id;
	}

	public int getId_produk() {
		return this.id_produk;
	}

	public String getKode() {
		return this.kode;
	}

	public String getNama() {
		return this.nama;
	}

	public String getGambar() {
		return this.gambar;
	}

	public String getTanggal() {
		return this.tanggal;
	}

	public String getPesan() {
		return this.pesan;
	}

	public int getFrom_id() {
		return this.from_id;
	}

	public String getFrom_nama() {
		return this.from_nama;
	}

	public String getFrom_photo() {
		return this.from_photo;
	}

	public int getTotal_unread() {
		return this.total_unread;
	}

	public void setTotal_unread(int total_unread) {
		this.total_unread = total_unread;
	}
}