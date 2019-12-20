package lcwu.fyp.careclub.model;

import java.io.Serializable;
import java.util.List;

public class Rider implements Serializable {
    private String name,id,phoneNo,numberPlate,vehicalName,vehicalColor,vehicalType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public String getVehicalName() {
        return vehicalName;
    }

    public void setVehicalName(String vehicalName) {
        this.vehicalName = vehicalName;
    }

    public String getVehicalColor() {
        return vehicalColor;
    }

    public void setVehicalColor(String vehicalColor) {
        this.vehicalColor = vehicalColor;
    }

    public String getVehicalType() {
        return vehicalType;
    }

    public void setVehicalType(String vehicalType) {
        this.vehicalType = vehicalType;
    }

    public Rider(String name, String id, String phoneNo, String numberPlate, String vehicalName, String vehicalColor, String vehicalType) {
        this.name = name;
        this.id = id;
        this.phoneNo = phoneNo;
        this.numberPlate = numberPlate;
        this.vehicalName = vehicalName;
        this.vehicalColor = vehicalColor;
        this.vehicalType = vehicalType;
    }

    public Rider() {
    }
}
