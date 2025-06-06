package com.HospitalBE.Hospital.models;


import com.HospitalBE.Hospital.models.AdmissionHistory;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Department")
public class Department {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "DEPARTMENT_NAME")
    private String name;
    @Column(name = "CODE")
    private String code;

   // @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
   // private List<Patient> patients;

   @OneToMany(mappedBy = "departmentId", cascade = CascadeType.ALL)
   private List<AdmissionHistory> admissionHistories;
    public Department() {
    }
    public Department(String name, String code) {
        this.name = name;
        this.code = code;
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
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public List<AdmissionHistory> getAdmissionHistories() {
        return admissionHistories;
    }
    public void setAdmissionHistories(List<AdmissionHistory> admissionHistories) {
        this.admissionHistories = admissionHistories;
    }
}