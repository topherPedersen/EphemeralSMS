package com.phponacid.ephemeralsms;

/**
 * Created by topherpedersen on 11/27/17.
 */

public class Contact {
    private String phoneNumber;
    private String name;
    public Contact() { // construct AddressBook
        phoneNumber = null;
        name = null;
    }
    public void setPhoneNumber(String param) {
        phoneNumber = param;
    }
    public void setName(String param) {
        name = param;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getName() {
        return name;
    }
}
