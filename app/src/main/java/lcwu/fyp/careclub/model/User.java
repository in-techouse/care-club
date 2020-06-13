package lcwu.fyp.careclub.model;

import java.io.Serializable;

public class User implements Serializable {
    private String fname, lname, email, phone, id, address, ngoId, image;
    private int role; // To track user role, 0=> customer, 1=> rider

    public User() {
        fname = lname = email = phone = id = address = ngoId = image = "";

    }

    public User(String fname, String lname, String email, String phone, String id, String address, String ngoId, String image, int role) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.id = id;
        this.address = address;
        this.ngoId = ngoId;
        this.image = image;
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNgoId() {
        return ngoId;
    }

    public void setNgoId(String ngoId) {
        this.ngoId = ngoId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
