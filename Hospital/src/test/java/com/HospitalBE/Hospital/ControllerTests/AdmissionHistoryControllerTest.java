package com.HospitalBE.Hospital.ControllerTests;

import com.HospitalBE.Hospital.controller.AdmissionHistoryController;
import com.HospitalBE.Hospital.models.AdmissionHistory;
import com.HospitalBE.Hospital.services.AdmissionHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdmissionHistoryController.class)
public class AdmissionHistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdmissionHistoryService admissionHistoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllAdmissions() throws Exception {
        // Arrange
        AdmissionHistory admissionHistory = new AdmissionHistory(1L, 1L, "2024-06-20", null, "Test admission");
        Mockito.when(admissionHistoryService.getAllAdmissions()).thenReturn(Collections.singletonList(admissionHistory));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].admissionDate").value("2024-06-20"))
                .andExpect(jsonPath("$[0].dischargeCause").value("Test admission"));
    }

    @Test
    void testGetAdmissionByIdNotFound() throws Exception {
        // Arrange
        Long admissionId = 1L;
        Mockito.when(admissionHistoryService.getAdmissionById(admissionId)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admissions/{id}", admissionId))
                .andExpect(status().isNotFound());
    }


    @Test
    void testCreateAdmission() throws Exception {
        // Arrange
        AdmissionHistory admissionHistory = new AdmissionHistory(1L, 1L, "2024-06-20", null, "Test admission");
        Mockito.when(admissionHistoryService.createAdmission(any(AdmissionHistory.class))).thenReturn(admissionHistory);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/admissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(admissionHistory)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.admissionDate").value("2024-06-20"))
                .andExpect(jsonPath("$.dischargeCause").value("Test admission"));
    }

    @Test
    void testUpdateAdmission() throws Exception {
        // Arrange
        Long admissionId = 1L;
        AdmissionHistory updatedAdmission = new AdmissionHistory(admissionId, 1L, "2024-06-22", "2024-06-25", "Updated admission");

        Mockito.when(admissionHistoryService.updateAdmission(anyLong(), any(AdmissionHistory.class))).thenReturn(updatedAdmission);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admissions/{id}", admissionId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedAdmission)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.admissionDate").value("2024-06-22"))
                .andExpect(jsonPath("$.dischargeDate").value("2024-06-25"))
                .andExpect(jsonPath("$.dischargeCause").value("Updated admission"));
    }

    @Test
    void testDeleteAdmission() throws Exception {
        // Arrange
        Long admissionId = 1L;
        Mockito.doNothing().when(admissionHistoryService).deleteAdmission(admissionId);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admissions/{id}", admissionId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteAdmissionNotFound() throws Exception {
        // Arrange
        Long admissionId = 1L;
        Mockito.doThrow(new RuntimeException("Admission not found with id: " + admissionId))
                .when(admissionHistoryService).deleteAdmission(admissionId);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admissions/{id}", admissionId))
                .andExpect(status().isNotFound());
    }

}
