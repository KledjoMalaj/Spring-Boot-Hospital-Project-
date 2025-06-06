package com.HospitalBE.Hospital.controller;

import com.HospitalBE.Hospital.models.Department;
import com.HospitalBE.Hospital.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping
    public List<Department> listDepartments() {
        return departmentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Department> addDepartment(@RequestBody Department department) {
        Department savedDepartment = departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDepartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department updatedDepartment) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (!departmentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        updatedDepartment.setId(id); // Ensure the ID is set for the updated department

        Department savedDepartment = departmentRepository.save(updatedDepartment);
        return ResponseEntity.ok(savedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (!departmentOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        departmentRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
