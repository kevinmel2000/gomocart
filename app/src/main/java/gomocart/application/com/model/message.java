package gomocart.application.com.model;

import java.io.Serializable;

public class message implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	int id;
	String nama;
	String telepon;
	String photo;
	String datetime;
	String message;
	Boolean is_self;
	Boolean is_sent;

	public message(int id, String nama, String telepon, String photo, String datetime, String message, Boolean is_self, Boolean is_sent) {
		this.id       = id;
		this.nama     = nama;
		this.telepon  = telepon;
		this.photo    = photo;
		this.datetime = datetime;
		this.message  = message;
		this.is_self  = is_self;
		this.is_sent  = is_sent;
	}

	public int getId() {
		return this.id;
	}

	public String getNama() {
		return this.nama;
	}

	public String getTelepon() {
		return this.telepon;
	}

	public String getPhoto() {
		return this.photo;
	}

	public String getDatetime() {
		return this.datetime;
	}

	public String getMessage() {
		return this.message;
	}

	public Boolean getIs_self() {
		return this.is_self;
	}

	public Boolean getIs_sent() {
		return this.is_self;
	}

}
