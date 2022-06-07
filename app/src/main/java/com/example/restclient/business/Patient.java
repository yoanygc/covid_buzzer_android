package com.example.restclient.business;

public class Patient {

    private Integer id;
    private String fullName;
    private String gender;
    private Integer age;
    private Company company;
    private PatientStatus patientStatus;

    public PatientStatus getPatientStatus() {
        return patientStatus;
    }

    public void setPatientStatus(PatientStatus patientStatus) {
        this.patientStatus = patientStatus;
    }

    public Patient() {
        this.id = 0;
        this.fullName = null;
        this.gender = null;
        this.age = 0;
        this.company = new Company();
        this.patientStatus = new PatientStatus();

    }

    public Patient(Integer id, String fullName, String gender, Integer age, PatientStatus patientStatus) {
        this.id = id;
        this.fullName = fullName;
        this.gender = gender;
        this.age = age;
        this.patientStatus = patientStatus;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
