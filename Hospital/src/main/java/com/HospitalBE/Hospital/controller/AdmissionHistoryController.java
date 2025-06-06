package com.HospitalBE.Hospital.controller;

import com.HospitalBE.Hospital.models.AdmissionHistory;
import com.HospitalBE.Hospital.services.AdmissionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admissions")
public class AdmissionHistoryController {

    @Autowired
    private AdmissionHistoryService admissionHistoryService;

    @GetMapping
    public ResponseEntity<List<AdmissionHistory>> getAllAdmissions() {
        List<AdmissionHistory> admissions = admissionHistoryService.getAllAdmissions();
        return ResponseEntity.ok(admissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdmissionHistory> getAdmissionById(@PathVariable Long id) {
        AdmissionHistory admissionHistory = admissionHistoryService.getAdmissionById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admission not found with id: " + id));
        return ResponseEntity.ok(admissionHistory);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<AdmissionHistory> createAdmission(@RequestBody AdmissionHistory admissionHistory) {
        AdmissionHistory createdAdmission = admissionHistoryService.createAdmission(admissionHistory);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAdmission);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AdmissionHistory> updateAdmission(@PathVariable Long id, @RequestBody AdmissionHistory admissionHistoryDetails) {
        AdmissionHistory updatedAdmission = admissionHistoryService.updateAdmission(id, admissionHistoryDetails);
        return ResponseEntity.ok(updatedAdmission);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmission(@PathVariable Long id) {
        try {
            admissionHistoryService.deleteAdmission(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            // Log the exception or handle it appropriately
            return ResponseEntity.notFound().build();
        }
    }
}
