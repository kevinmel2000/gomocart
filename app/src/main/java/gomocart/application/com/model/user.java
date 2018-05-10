package gomocart.application.com.model;

import java.io.Serializable;

public class user implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int id;
	String first_name, last_name, email, phone, dropship_name, dropship_phone, jenis_user, photo;

	public user(int id, String first_name, String last_name, String email, String phone, String dropship_name, String dropship_phone, String jenis_user, String photo) {
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.phone = phone;
		this.dropship_name = dropship_name;
		this.dropship_phone = dropship_phone;
		this.jenis_user = jenis_user;
		this.photo = photo;
	}
	
	public int getId() {
		return this.id;		
	}

	public String getFirst_name() {
		return this.first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name=first_name;
	}

	public String getLast_name() {
		return this.last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name=last_name;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email=email;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone=phone;
	}

	public String getDropship_name() {
		return this.dropship_name;
	}

	public String getDropship_phone() {
		return this.dropship_phone;
	}

	public String getJenis_user() {
		return this.jenis_user;		
	}

	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo=photo;
	}
}
