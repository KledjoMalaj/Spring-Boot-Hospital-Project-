package com.HospitalBE.Hospital.ControllerTests;

import com.HospitalBE.Hospital.controller.PatientController;
import com.HospitalBE.Hospital.models.Patient;
import com.HospitalBE.Hospital.repositories.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PatientController.class)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListPatients() throws Exception {
        // Arrange
        Patient patient = new Patient("John", "Doe", "Cardiology", new Date(), "Healthy");
        Mockito.when(patientRepository.findAll()).thenReturn(Collections.singletonList(patient));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/patients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].lastname").value("Doe"))
                .andExpect(jsonPath("$[0].departmentName").value("Cardiology"))
                .andExpect(jsonPath("$[0].clinicalData").value("Healthy"));
    }

    @Test
    void testAddPatient() throws Exception {
        // Arrange
        Patient patient = new Patient("Alice", "Wonder", "Neurology", new SimpleDateFormat("yyyy-MM-dd").parse("1995-01-01"), "Test Reason");
        Mockito.when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Alice"))
                .andExpect(jsonPath("$.lastname").value("Wonder"))
                .andExpect(jsonPath("$.departmentName").value("Neurology"))
                .andExpect(jsonPath("$.clinicalData").value("Test Reason"));
    }

    @Test
    void testUpdatePatient() throws Exception {
        // Arrange
        Long patientId = 1L;
        Patient existingPatient = new Patient("John", "Doe", "Cardiology", new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"), "Healthy");
        Patient updatedPatient = new Patient("Jane", "Doe", "Cardiology", new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"), "Healthy");
        updatedPatient.setId(patientId);

        Mockito.when(patientRepository.findById(patientId)).thenReturn(Optional.of(existingPatient));
        Mockito.when(patientRepository.save(any(Patient.class))).thenReturn(updatedPatient);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/patients/{id}", patientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPatient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane"))
                .andExpect(jsonPath("$.lastname").value("Doe"));
    }

    @Test
    void testDeletePatient() throws Exception {
        // Arrange
        Long patientId = 1L;
        Patient patient = new Patient("John", "Doe", "Cardiology", new SimpleDateFormat("yyyy-MM-dd").parse("1990-01-01"), "Healthy");
        Mockito.when(patientRepository.findById(patientId)).thenReturn(Optional.of(patient));
        Mockito.doNothing().when(patientRepository).deleteById(patientId);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/patients/{id}", patientId))
                .andExpect(status().isNoContent());
    }

}
