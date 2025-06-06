package com.HospitalBE.Hospital.repositoryTests;

import com.HospitalBE.Hospital.models.AdmissionHistory;
import com.HospitalBE.Hospital.repositories.AdmissionHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AdmissionHistoryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AdmissionHistoryRepository admissionHistoryRepository;

    private AdmissionHistory admissionHistory;

    @BeforeEach
    void setUp() {
        admissionHistory = new AdmissionHistory(1L, 1L, "2023-06-21", null, "Test admission");
        entityManager.persist(admissionHistory);
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        entityManager.remove(admissionHistory);
        entityManager.flush();
    }

    @Test
    void testFindById() {
        // Act
        AdmissionHistory foundAdmission = admissionHistoryRepository.findById(admissionHistory.getId()).orElse(null);

        // Assert
        assertNotNull(foundAdmission);
        assertEquals(admissionHistory.getPatientId(), foundAdmission.getPatientId());
        assertEquals(admissionHistory.getDepartmentId(), foundAdmission.getDepartmentId());
        assertEquals(admissionHistory.getAdmissionDate(), foundAdmission.getAdmissionDate());
        assertEquals(admissionHistory.getDischargeDate(), foundAdmission.getDischargeDate());
        assertEquals(admissionHistory.getDischargeCause(), foundAdmission.getDischargeCause());
    }

    @Test
    void testSave() {
        // Arrange
        AdmissionHistory newAdmission = new AdmissionHistory(2L, 2L, "2023-06-22", null, "Another test admission");

        // Act
        AdmissionHistory savedAdmission = admissionHistoryRepository.save(newAdmission);

        // Assert
        assertNotNull(savedAdmission);
        assertEquals(newAdmission.getPatientId(), savedAdmission.getPatientId());
        assertEquals(newAdmission.getDepartmentId(), savedAdmission.getDepartmentId());
        assertEquals(newAdmission.getAdmissionDate(), savedAdmission.getAdmissionDate());
        assertEquals(newAdmission.getDischargeDate(), savedAdmission.getDischargeDate());
        assertEquals(newAdmission.getDischargeCause(), savedAdmission.getDischargeCause());
    }

    @Test
    void testDelete() {
        // Act
        admissionHistoryRepository.deleteById(admissionHistory.getId());
        AdmissionHistory deletedAdmission = admissionHistoryRepository.findById(admissionHistory.getId()).orElse(null);

        // Assert
        assertNull(deletedAdmission);
    }
}
