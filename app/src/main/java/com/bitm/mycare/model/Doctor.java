package com.bitm.mycare.model;

/**
 * Created by Alamgir on 04/05/2017.
 */
public class Doctor {
    private int id;
    private String name;
    private String speciality;
    private String appointmentDate;
    private String phone;
    private String email;

    public Doctor(int id, String name, String speciality, String appointmentDate, String phone, String email) {
        this.id = id;
        this.name = name;
        this.speciality = speciality;
        this.appointmentDate = appointmentDate;
        this.phone = phone;
        this.email = email;
    }

    public Doctor() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }
}
