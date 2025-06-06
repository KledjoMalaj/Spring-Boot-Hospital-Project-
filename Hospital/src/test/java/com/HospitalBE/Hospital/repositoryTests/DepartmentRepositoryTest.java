package com.HospitalBE.Hospital.repositoryTests;

import com.HospitalBE.Hospital.models.Department;
import com.HospitalBE.Hospital.models.AdmissionHistory;
import com.HospitalBE.Hospital.repositories.DepartmentRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class DepartmentRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartmentRepository departmentRepository;

    private Department department;

    @BeforeEach
    void setUp() {
        department = new Department("Test Department", "TEST");
        entityManager.persist(department);

        AdmissionHistory admissionHistory = new AdmissionHistory(1L, department.getId(), "2024-06-21", null, "Test admission");
        entityManager.persist(admissionHistory);

        department.setAdmissionHistories(List.of(admissionHistory));
        entityManager.flush();
    }

    @AfterEach
    void tearDown() {
        entityManager.remove(department);
    }

    @Test
    void testFindById() {
        // Act
        Department foundDepartment = departmentRepository.findById(department.getId()).orElse(null);

        // Assert
        assertNotNull(foundDepartment);
        assertEquals("Test Department", foundDepartment.getName());
        assertEquals("TEST", foundDepartment.getCode());
        assertEquals(1, foundDepartment.getAdmissionHistories().size());
    }
}
