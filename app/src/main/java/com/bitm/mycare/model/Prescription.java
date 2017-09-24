package com.bitm.mycare.model;

/**
 * Created by Alamgir on 04/08/2017.
 */

public class Prescription {
    private int id;
    private int doctorId;
    private String imageUrl;
    private Doctor doctor;
    private String prescriptionDate;
    private String prescriptionDescription;

    public Prescription() {
    }

    public Prescription(int id, int doctorId, String imageUrl, Doctor doctor, String prescriptionDate) {
        this.id = id;
        this.doctorId = doctorId;
        this.imageUrl = imageUrl;
        this.doctor = doctor;
        this.prescriptionDate = prescriptionDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(String prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getPrescriptionDescription() {
        return prescriptionDescription;
    }

    public void setPrescriptionDescription(String prescriptionDescription) {
        this.prescriptionDescription = prescriptionDescription;
    }
}
