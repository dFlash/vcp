package com.maliavin.vcp.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Describes company model, which uses video portal
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Document
public class Company {

    @Id
    private String id;

    @Indexed(unique = true, name = "name")
    private String name;

    private String address;

    @Indexed(unique = true, name = "contactEmail")
    private String contactEmail;

    @Indexed(unique = true, name = "phone")
    private String phone;

    public Company() {
        super();
    }

    public Company(String name, String address, String contactEmail, String phone) {
        super();
        this.name = name;
        this.address = address;
        this.contactEmail = contactEmail;
        this.phone = phone;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Company [name=" + name + ", address=" + address + ", contactEmail=" + contactEmail + ", phone=" + phone
                + "]";
    }
}
