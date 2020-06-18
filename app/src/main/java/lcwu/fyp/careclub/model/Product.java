package lcwu.fyp.careclub.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    private String id;
    private String name;
    private String category;
    private String address;
    private String description, phoneno;
    private int quantityOfProducts;
    private List<String> images;
    private String userId, ngoid, riderId;
    private boolean isTaken, isPicked;
    private double latitude, longitude;

    public Product() {
        images = new ArrayList<>();
        latitude = longitude = 0;
        isPicked = false;
    }

    public Product(String id, String name, String category, String address, String description, String phoneno, int quantityOfProducts, List<String> images, String userId, String ngoid, String riderId, boolean isTaken, boolean isPicked, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.description = description;
        this.phoneno = phoneno;
        this.quantityOfProducts = quantityOfProducts;
        this.images = images;
        this.userId = userId;
        this.ngoid = ngoid;
        this.riderId = riderId;
        this.isTaken = isTaken;
        this.isPicked = isPicked;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public boolean isPicked() {
        return isPicked;
    }

    public void setPicked(boolean picked) {
        isPicked = picked;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public int getQuantityOfProducts() {
        return quantityOfProducts;
    }

    public void setQuantityOfProducts(int quantityOfProducts) {
        this.quantityOfProducts = quantityOfProducts;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNgoid() {
        return ngoid;
    }

    public void setNgoid(String ngoid) {
        this.ngoid = ngoid;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}
