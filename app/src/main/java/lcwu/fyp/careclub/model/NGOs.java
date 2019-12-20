package lcwu.fyp.careclub.model;

import java.io.Serializable;
import java.util.List;

public class NGOs implements Serializable {
    private String id,name,address,phoneNo,category;
    private List<PaymentMethods> paymentMethodsList;

    public NGOs() {
    }

    public NGOs(String id, String name, String address, String phoneNo, String category, List<PaymentMethods> paymentMethodsList) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneNo = phoneNo;
        this.category = category;
        this.paymentMethodsList = paymentMethodsList;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<PaymentMethods> getPaymentMethodsList() {
        return paymentMethodsList;
    }

    public void setPaymentMethodsList(List<PaymentMethods> paymentMethodsList) {
        this.paymentMethodsList = paymentMethodsList;
    }
}
