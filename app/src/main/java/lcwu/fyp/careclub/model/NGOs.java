package lcwu.fyp.careclub.model;

import java.io.Serializable;

public class NGOs implements Serializable {
    private String address, category, email, id, image, levelOfAction, name, phone, vision, website, workingSince;
    private int approved;

    public NGOs() {
        address = category = email = id = image = levelOfAction = name = phone = vision = website = workingSince = "";
    }

    public NGOs(String address, String category, String email, String id, String image, String levelOfAction, String name, String phone, String vision, String website, String workingSince, int approved) {
        this.address = address;
        this.category = category;
        this.email = email;
        this.id = id;
        this.image = image;
        this.levelOfAction = levelOfAction;
        this.name = name;
        this.phone = phone;
        this.vision = vision;
        this.website = website;
        this.workingSince = workingSince;
        this.approved = approved;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLevelOfAction() {
        return levelOfAction;
    }

    public void setLevelOfAction(String levelOfAction) {
        this.levelOfAction = levelOfAction;
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

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getWorkingSince() {
        return workingSince;
    }

    public void setWorkingSince(String workingSince) {
        this.workingSince = workingSince;
    }

    public int getApproved() {
        return approved;
    }

    public void setApproved(int approved) {
        this.approved = approved;
    }
}
