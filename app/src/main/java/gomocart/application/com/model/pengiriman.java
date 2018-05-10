package gomocart.application.com.model;

import java.io.Serializable;

public class pengiriman implements Serializable {

	private static final long serialVersionUID = 1L;
	private String kode, keterangan, tanggal, jam, kota;
	public pengiriman(String kode, String keterangan, String tanggal, String jam, String kota) {
		this.kode = kode;
		this.keterangan = keterangan;
		this.tanggal = tanggal;
		this.jam = jam;
		this.kota = kota;
	}
	
	public String getKode() {
		return this.kode;
	}

	public String getKeterangan() {
		return this.keterangan;
	}
	
	public String getTanggal() {
		return this.tanggal;
	}
	
	public String getJam() {
		return this.jam;
	}
	
	public String getKota() {
		return this.kota;
	}
	
}
