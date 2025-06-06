package com.HospitalBE.Hospital.services;

import com.HospitalBE.Hospital.models.AdmissionHistory;
import com.HospitalBE.Hospital.repositories.AdmissionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionHistoryService {

    @Autowired
    private AdmissionHistoryRepository admissionHistoryRepository;

    public List<AdmissionHistory> getAllAdmissions() {
        return admissionHistoryRepository.findAll();
    }

    public Optional<AdmissionHistory> getAdmissionById(Long id) {
        return admissionHistoryRepository.findById(id);
    }

    public AdmissionHistory createAdmission(AdmissionHistory admissionHistory) {
        return admissionHistoryRepository.save(admissionHistory);
    }

    public AdmissionHistory updateAdmission(Long id, AdmissionHistory admissionHistoryDetails) {
        AdmissionHistory admissionHistory = admissionHistoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admission history not found with id: " + id));

        admissionHistory.setPatientId(admissionHistoryDetails.getPatientId()); // Ensure patient association is updated
        admissionHistory.setDepartmentId(admissionHistoryDetails.getDepartmentId()); // Ensure department association is updated
        admissionHistory.setAdmissionDate(admissionHistoryDetails.getAdmissionDate());
        admissionHistory.setDischargeDate(admissionHistoryDetails.getDischargeDate());
        admissionHistory.setDischargeCause(admissionHistoryDetails.getDischargeCause());

        return admissionHistoryRepository.save(admissionHistory);
    }

    public void deleteAdmission(Long id) {
        Optional<AdmissionHistory> admissionOptional = admissionHistoryRepository.findById(id);
        if (admissionOptional.isPresent()) {
            admissionHistoryRepository.deleteById(id);
        } else {
            throw new RuntimeException("Admission not found with id: " + id);
        }
    }
}
