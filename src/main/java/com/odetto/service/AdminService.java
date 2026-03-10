package com.odetto.service;

import com.odetto.dto.admin.AdminLoginRequestDTO;
import com.odetto.dto.admin.AdminLoginResponseDTO;
import com.odetto.dto.admin.AdminRequestDTO;
import com.odetto.dto.admin.AdminResponseDTO;
import com.odetto.model.Admin;
import com.odetto.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.NoSuchElementException;

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

    public AdminLoginResponseDTO login(AdminLoginRequestDTO dto) {
        Admin admin = adminRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new NoSuchElementException("Admin não encontrado."));

        if (!admin.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("Senha incorreta.");
        }

        return new AdminLoginResponseDTO(admin.getId(), admin.getEmail());
    }

    public AdminResponseDTO createAdmin(AdminRequestDTO dto) {
        Admin admin = new Admin();
        admin.setEmail(dto.getEmail());
        admin.setPassword(dto.getPassword());
        Admin saved = adminRepository.save(admin);
        return objectMapper.convertValue(saved, AdminResponseDTO.class);
    }

    public AdminResponseDTO editAdmin(AdminRequestDTO dto) {
        Admin admin = adminRepository.findById(dto.getId())
                .orElseThrow(() -> new NoSuchElementException("Admin com ID " + dto.getId() + " não encontrado."));

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            admin.setEmail(dto.getEmail());
        }
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            admin.setPassword(dto.getPassword());
        }

        Admin saved = adminRepository.save(admin);
        return objectMapper.convertValue(saved, AdminResponseDTO.class);
    }

    public void deleteAdmin(Long id) {
        adminRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Admin com ID " + id + " não encontrado."));
        adminRepository.deleteById(id);
    }

}
