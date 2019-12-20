package lcwu.fyp.careclub.model;

import java.io.Serializable;
import java.util.List;

public class Products implements Serializable {
    private String id,name,category;
    private int quantityOfProducts;
    private List<String>images;

    public Products() {
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

    public Products(String id, String name, String category, int quantityOfProducts, List<String> images) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantityOfProducts = quantityOfProducts;
        this.images = images;
    }
}
