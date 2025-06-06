package com.HospitalBE.Hospital.ControllerTests;

import com.HospitalBE.Hospital.controller.DepartmentController;
import com.HospitalBE.Hospital.models.Department;
import com.HospitalBE.Hospital.repositories.DepartmentRepository;
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

@WebMvcTest(DepartmentController.class)
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentRepository departmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListDepartments() throws Exception {
        // Arrange
        Department department = new Department("Cardiology", "CARD");
        Mockito.when(departmentRepository.findAll()).thenReturn(Collections.singletonList(department));

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Cardiology"))
                .andExpect(jsonPath("$[0].code").value("CARD"));
    }

    @Test
    void testAddDepartment() throws Exception {
        // Arrange
        Department department = new Department("Neurology", "NEURO");
        Mockito.when(departmentRepository.save(any(Department.class))).thenReturn(department);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Neurology"))
                .andExpect(jsonPath("$.code").value("NEURO"));
    }

    @Test
    void testUpdateDepartment() throws Exception {
        // Arrange
        Long departmentId = 1L;
        Department existingDepartment = new Department("Cardiology", "CARD");
        Department updatedDepartment = new Department("Oncology", "ONC");
        updatedDepartment.setId(departmentId);

        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(existingDepartment));
        Mockito.when(departmentRepository.save(any(Department.class))).thenReturn(updatedDepartment);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.put("/api/departments/{id}", departmentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedDepartment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Oncology"))
                .andExpect(jsonPath("$.code").value("ONC"));
    }

    @Test
    void testDeleteDepartment() throws Exception {
        // Arrange
        Long departmentId = 1L;
        Department department = new Department("Cardiology", "CARD");
        Mockito.when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));
        Mockito.doNothing().when(departmentRepository).deleteById(departmentId);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/departments/{id}", departmentId))
                .andExpect(status().isNoContent());
    }

}
