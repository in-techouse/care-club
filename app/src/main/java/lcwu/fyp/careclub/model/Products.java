package lcwu.fyp.careclub.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Products implements Serializable {
    private String id;
    private String name;
    private String category;
    private String address;
    private String description, phoneno;
    private int quantityOfProducts;
    private List<String>images;

    public Products() {
    }

    public Products(String id, String name, String category, String address, String description, String phoneno, int quantityOfProducts, List<String> images) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.address = address;
        this.description = description;
        this.phoneno = phoneno;
        this.quantityOfProducts = quantityOfProducts;
        this.images = images;
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
}
