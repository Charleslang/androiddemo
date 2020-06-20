package com.dysy.carttest.dto;

public class OrderAddressDTO {
    private String aDetails;
    private String aPhone;
    private String aUserId;
    private String aUsername;

    public String getaUsername() {
        return aUsername;
    }

    public void setaUsername(String aUsername) {
        this.aUsername = aUsername;
    }

    public String getaDetails() {
        return aDetails;
    }

    public void setaDetails(String aDetails) {
        this.aDetails = aDetails;
    }

    public String getaPhone() {
        return aPhone;
    }

    public void setaPhone(String aPhone) {
        this.aPhone = aPhone;
    }

    public String getaUserId() {
        return aUserId;
    }

    public void setaUserId(String aUserId) {
        this.aUserId = aUserId;
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "aDetails='" + aDetails + '\'' +
                ", aPhone='" + aPhone + '\'' +
                ", aUserId='" + aUserId + '\'' +
                ", aUserame='" + aUsername + '\'' +
                '}';
    }
}
