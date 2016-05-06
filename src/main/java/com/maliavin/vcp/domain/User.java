package com.maliavin.vcp.domain;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Describes user model, which uses video portal
 * 
 * @author DMaliavin
 * @since 0.0.1
 */
@Document
public class User implements Serializable {

    private static final long serialVersionUID = 8651792031916300819L;

    @Id
    private String id;

    private String name;

    private String surname;

    @Indexed(unique = true, name = "login")
    private String login;

    @Indexed(unique = true, name = "email")
    private String email;

    @DBRef(lazy = true)
    private Company userCompany;

    private String avatar;

    private String role;

    private String password;

    public User() {
        super();
    }

    public User(String name, String surname, String login, String email, Company company, String avatar, String role,
            String password) {
        super();
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.email = email;
        this.userCompany = company;
        this.avatar = avatar;
        this.role = role;
        this.password = password;
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Company getCompany() {
        return userCompany;
    }

    public void setCompany(Company company) {
        this.userCompany = company;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", login=" + login + ", email=" + email
                + ", company=" + userCompany + ", avatar=" + avatar + ", role=" + role + "]";
    }

}
