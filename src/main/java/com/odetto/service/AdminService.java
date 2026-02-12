package com.odetto.service;

import com.odetto.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AdminService(AdminRepository adminRepository, ObjectMapper objectMapper) {
        this.adminRepository = adminRepository;
        this.objectMapper = objectMapper;
    }


}
