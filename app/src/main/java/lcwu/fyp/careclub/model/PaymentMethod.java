package lcwu.fyp.careclub.model;

import java.io.Serializable;

public class PaymentMethod implements Serializable {
    private String number, methodName;

    public PaymentMethod() {
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public PaymentMethod(String number, String methodName) {
        this.number = number;
        this.methodName = methodName;
    }
}
