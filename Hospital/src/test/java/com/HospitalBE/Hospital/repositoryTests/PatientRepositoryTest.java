package com.HospitalBE.Hospital.repositoryTests;

import com.HospitalBE.Hospital.models.Patient;
import com.HospitalBE.Hospital.models.AdmissionHistory;
import com.HospitalBE.Hospital.repositories.PatientRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class PatientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PatientRepository patientRepository;

    private Patient patient;

    @BeforeEach
    void setUp() {
        patient = new Patient("John", "Doe", "Cardiology",new Date(1 / 2020), "Some clinical data");
        entityManager.persist(patient);

        AdmissionHistory admissionHistory = new AdmissionHistory(1L, patient.getId(), LocalDate.of(2024, 6, 21), null, "Test admission");
        entityManager.persist(admissionHistory);

        patient.setAdmissionHistories(List.of(admissionHistory));
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        entityManager.remove(patient);
    }

    @Test
    void testFindById() {
        // Act
        Patient foundPatient = patientRepository.findById(patient.getId()).orElse(null);

        // Assert
        assertNotNull(foundPatient);
        assertEquals("John", foundPatient.getName());
        assertEquals("Doe", foundPatient.getLastname());
        assertEquals("Cardiology", foundPatient.getDepartmentName());
        assertEquals("Some clinical data", foundPatient.getClinicalData());
        assertEquals(1, foundPatient.getAdmissionHistories().size());
    }
}
