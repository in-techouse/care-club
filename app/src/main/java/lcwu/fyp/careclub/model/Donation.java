package lcwu.fyp.careclub.model;

import java.io.Serializable;

public class Donation implements Serializable {
    private String id,userId,ngoId,date;
    private int amount;

    public Donation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNgoId() {
        return ngoId;
    }

    public void setNgoId(String ngoId) {
        this.ngoId = ngoId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Donation(String id, String userId, String ngoId, String date, int amount) {
        this.id = id;
        this.userId = userId;
        this.ngoId = ngoId;
        this.date = date;
        this.amount = amount;
    }
}
