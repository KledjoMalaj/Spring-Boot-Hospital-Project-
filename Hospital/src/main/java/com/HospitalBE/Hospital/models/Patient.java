package com.HospitalBE.Hospital.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "Patient")
public class Patient {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "PATIENT_NAME")
    private String name;

    @Column(name = "PATIENT_LASTNAME")
    private String lastname;

    @Column(name = "DEPARTMENT_NAME")
    private String departmentName; // Updated to match the column name in your database

    @Column(name = "DATE_OF_BIRTH")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateOfBirth;

    @Column(name = "CLINICAL_DATA")
    private String clinicalData;

    @OneToMany(mappedBy = "patientId", cascade = CascadeType.ALL)
    private List<AdmissionHistory> admissionHistories;

    public Patient() {
    }

    public Patient(String name, String lastname, String departmentName, Date dateOfBirth, String clinicalData) {
        this.name = name;
        this.lastname = lastname;
        this.departmentName = departmentName;
        this.dateOfBirth = dateOfBirth;
        this.clinicalData = clinicalData;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getClinicalData() {
        return clinicalData;
    }

    public void setClinicalData(String clinicalData) {
        this.clinicalData = clinicalData;
    }

    public List<AdmissionHistory> getAdmissionHistories() {
        return admissionHistories;
    }

    public void setAdmissionHistories(List<AdmissionHistory> admissionHistories) {
        this.admissionHistories = admissionHistories;
    }
}


