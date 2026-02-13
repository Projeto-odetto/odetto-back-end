package com.odetto.controller;


import com.odetto.dto.Admin.AdminRequestDTO;
import com.odetto.dto.Admin.AdminResponseDTO;
import com.odetto.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/list-admins")
    public ResponseEntity<List<AdminResponseDTO>> listAdmins() {
        List<AdminResponseDTO> admins = adminService.listAdmin();
        return ResponseEntity.ok(admins);
    }


}
