package lcwu.fyp.careclub.model;

import java.io.Serializable;

public class PaymentMethod implements Serializable {
    private String accountHolderName, accountNumber, id, name, ngoId, providerName;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNgoId() {
        return ngoId;
    }

    public void setNgoId(String ngoId) {
        this.ngoId = ngoId;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public PaymentMethod(String accountHolderName, String accountNumber, String id, String name, String ngoId, String providerName) {
        this.accountHolderName = accountHolderName;
        this.accountNumber = accountNumber;
        this.id = id;
        this.name = name;
        this.ngoId = ngoId;
        this.providerName = providerName;
    }

    public PaymentMethod() {
    }
}

