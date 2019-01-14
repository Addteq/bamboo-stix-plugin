package com.addteq.bamboo.plugin.rest;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "check")
@XmlAccessorType(XmlAccessType.FIELD)
public class addteqRestResourceModel {

    private String check;
    private String type;
    private String version;

    public addteqRestResourceModel() {
    }

    public addteqRestResourceModel(String message) {
        String[] parts = message.split("&");
        this.check = parts[0];
        this.type = parts[1];
        this.version = parts[2];
    }

    @XmlElement(name = "Type")
    public String getCheck() {
        return check;
    }

    public void setCheck(String message) {
        this.check = message;
    }

    @XmlElement(name = "Expiration")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @XmlElement(name = "Version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
