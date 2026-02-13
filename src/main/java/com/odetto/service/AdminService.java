package com.odetto.service;

import com.odetto.dto.Admin.AdminResponseDTO;
import com.odetto.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AdminService(AdminRepository adminRepository, ObjectMapper objectMapper) {
        this.adminRepository = adminRepository;
        this.objectMapper = objectMapper;
    }

    public List<AdminResponseDTO> listAdmin() {
        List<AdminResponseDTO> adminResponseDTOS = adminRepository.findAll().stream()
                .map(admin -> objectMapper.convertValue(admin, AdminResponseDTO.class))
                .toList();
        return adminResponseDTOS;
    }


}
