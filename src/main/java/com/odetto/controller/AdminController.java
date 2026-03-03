package com.odetto.controller;

import com.odetto.dto.admin.AdminResponseDTO;
import com.odetto.dto.admin.PreCadastroRequestDTO;
import com.odetto.dto.Student.StudentResponseDTO;
import com.odetto.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.odetto.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final StudentService studentService;

    @Autowired
    public AdminController(AdminService adminService, StudentService studentService) {
        this.adminService = adminService;
        this.studentService = studentService;
    }


    @GetMapping("/list-admins")
    public ResponseEntity<List<AdminResponseDTO>> listAdmins() {
        List<AdminResponseDTO> admins = adminService.listAdmin();
        return ResponseEntity.ok(admins);
    }

    @PostMapping("/pre-cadastro")
    public ResponseEntity<StudentResponseDTO> preCadastro(@RequestBody PreCadastroRequestDTO request) {
        return ResponseEntity.ok(studentService.preCadastro(request));
    }
}
