package lcwu.fyp.careclub.model;

import java.io.Serializable;

public class Donation implements Serializable {
    private String id, userId, ngoId, paymentMethod, accountHolderName, accountNumber, date, ngoName, ngoCategory, ngoContact, ngoEmail;
    private int amount;

    public Donation() {
        id = userId = ngoId = paymentMethod = date = ngoName = ngoCategory = ngoContact = ngoEmail = accountHolderName = accountNumber = "";
        amount = 0;
    }

    public Donation(String id, String userId, String ngoId, String paymentMethod, String accountHolderName, String accountNumber, String date, String ngoName, String ngoCategory, String ngoContact, String ngoEmail, int amount) {
        this.id = id;
        this.userId = userId;
        this.ngoId = ngoId;
        this.paymentMethod = paymentMethod;
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.date = date;
        this.ngoName = ngoName;
        this.ngoCategory = ngoCategory;
        this.ngoContact = ngoContact;
        this.ngoEmail = ngoEmail;
        this.amount = amount;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNgoName() {
        return ngoName;
    }

    public void setNgoName(String ngoName) {
        this.ngoName = ngoName;
    }

    public String getNgoCategory() {
        return ngoCategory;
    }

    public void setNgoCategory(String ngoCategory) {
        this.ngoCategory = ngoCategory;
    }

    public String getNgoContact() {
        return ngoContact;
    }

    public void setNgoContact(String ngoContact) {
        this.ngoContact = ngoContact;
    }

    public String getNgoEmail() {
        return ngoEmail;
    }

    public void setNgoEmail(String ngoEmail) {
        this.ngoEmail = ngoEmail;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
