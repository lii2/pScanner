package com.jeff.clients.email.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneNumberList {

    private PhoneNumber[] phoneNumbers;

    private String name;

    public PhoneNumber[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(PhoneNumber[] phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PhoneNumberList{" +
                "phoneNumbers=" + Arrays.toString(phoneNumbers) +
                ", name='" + name + '\'' +
                '}';
    }

}
