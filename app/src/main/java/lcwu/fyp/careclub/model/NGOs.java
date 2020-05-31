package lcwu.fyp.careclub.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NGOs implements Serializable {
    private String address, category, email, id, name, phone;
    private String images;

    public NGOs() {
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public NGOs(String address, String category, String email, String id, String name, String phone, String images) {
        this.address = address;
        this.category = category;
        this.email = email;
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.images = images;
    }
}
