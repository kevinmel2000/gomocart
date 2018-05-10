package gomocart.application.com.model;

import java.io.Serializable;

public class facebook implements Serializable {

    private static final long serialVersionUID = 1L;
    private String userid, firstname, lastname, useremail, userpicture;

    public facebook(String userid, String useremail, String firstname, String lastname, String userpicture) {
        this.userid = userid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.useremail = useremail;
        this.userpicture = userpicture;
    }

    public String getUserId() {
        return this.userid;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getUseremail() {
        return this.useremail;
    }

    public String getUserpicture() {
        return this.userpicture;
    }

}