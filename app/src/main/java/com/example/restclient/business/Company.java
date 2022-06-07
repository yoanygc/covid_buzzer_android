package com.example.restclient.business;

import java.util.ArrayList;

public class Company {

    private static ArrayList<Company> companies = new ArrayList<>();

    private Integer id;
    private String name;
    private String phoneNumber;
    private String email;

    public Company() {
        this.id = 0;
        this.name = null;
        this.phoneNumber = null;
        this.email = null;
    }

    public Company(Integer id, String name, String phoneNumber, String email) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }
}
