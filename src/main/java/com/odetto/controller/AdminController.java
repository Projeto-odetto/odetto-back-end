package com.odetto.controller;


<<<<<<< HEAD
import com.odetto.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

=======
import com.odetto.dto.Admin.AdminRequestDTO;
import com.odetto.dto.Admin.AdminResponseDTO;
import com.odetto.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

>>>>>>> 814bcf09fc4a851dcf9f536425a72c894bbffe11
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

<<<<<<< HEAD
    @Autowired
=======
>>>>>>> 814bcf09fc4a851dcf9f536425a72c894bbffe11
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

<<<<<<< HEAD
=======
    @GetMapping("/list-admins")
    public ResponseEntity<List<AdminResponseDTO>> listAdmins() {
        List<AdminResponseDTO> admins = adminService.listAdmin();
        return ResponseEntity.ok(admins);
    }


>>>>>>> 814bcf09fc4a851dcf9f536425a72c894bbffe11
}
