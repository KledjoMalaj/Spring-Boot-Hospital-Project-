package com.HospitalBE.Hospital.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "admission_history")
public class AdmissionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "admission_date")
    private String admissionDate;

    @Column(name = "discharge_date")
    private String dischargeDate;

    @Column(name = "discharge_cause")
    private String dischargeCause;

    // Constructors

    public AdmissionHistory() {
    }

    public AdmissionHistory(Long patientId, Long departmentId, String admissionDate, String dischargeDate, String dischargeCause) {
        this.patientId = patientId;
        this.departmentId = departmentId;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.dischargeCause = dischargeCause;
    }

    public AdmissionHistory(long patientId, Long id, LocalDate of, Object dischargeDate, String testAdmission) {

    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(String admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(String dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public String getDischargeCause() {
        return dischargeCause;
    }

    public void setDischargeCause(String dischargeCause) {
        this.dischargeCause = dischargeCause;
    }

    // toString() method (optional)

    @Override
    public String toString() {
        return "AdmissionHistory{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", departmentId=" + departmentId +
                ", admissionDate='" + admissionDate + '\'' +
                ", dischargeDate='" + dischargeDate + '\'' +
                ", dischargeCause='" + dischargeCause + '\'' +
                '}';
    }
}
