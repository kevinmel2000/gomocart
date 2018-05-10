package gomocart.application.com.model;

import java.io.Serializable;

public class kategori implements Serializable {
	private static final long serialVersionUID = 1L;
	int id;
	String nama, penjelasan, header;
	boolean checked;
	
	public kategori(int id, String nama, String penjelasan, String header) {
		this.id = id;
		this.nama = nama;
		this.penjelasan = penjelasan;
		this.header = header;
		this.checked = false;
	}

	public kategori(int id, String nama, boolean checked) {
		this.id = id;
		this.nama = nama;
		this.checked = checked;
	}

	public int getId() {
		return this.id;
	}

	public String getNama() {
		return this.nama;
	}

	public String getPenjelasan() {
		return this.penjelasan;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getHeader() {
		return this.header;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean getChecked() {
		return this.checked;
	}
}
