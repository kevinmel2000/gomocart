package gomocart.application.com.model;

import java.util.ArrayList;

public class produk_kategori {
	int id;
	String nama;
	ArrayList<produk> produk_list;
	int height;
	
	public produk_kategori(int id, String nama, ArrayList<produk> produk_list) {
		this.id = id;
		this.nama = nama;
		this.produk_list = produk_list;
	}

	public int getId() {
		return this.id;
	}

	public String getNama() {
		return this.nama;
	}

	public ArrayList<produk> getProduk_list() {
		return this.produk_list;
	}

	public void setProduk_list(ArrayList<produk> produk_list) {
		this.produk_list = produk_list;
	}

	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height=height;
	}

}
