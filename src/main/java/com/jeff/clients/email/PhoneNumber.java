package com.jeff.clients.email;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhoneNumber {

    private String number;

    private Carrier carrier;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "number='" + number + '\'' +
                ", carrier=" + carrier +
                '}';
    }
}
