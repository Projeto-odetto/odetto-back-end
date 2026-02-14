package com.odetto.service;

<<<<<<< HEAD
=======
import com.odetto.dto.Admin.AdminResponseDTO;
>>>>>>> 814bcf09fc4a851dcf9f536425a72c894bbffe11
import com.odetto.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

<<<<<<< HEAD
=======
import java.util.List;

>>>>>>> 814bcf09fc4a851dcf9f536425a72c894bbffe11
@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AdminService(AdminRepository adminRepository, ObjectMapper objectMapper) {
        this.adminRepository = adminRepository;
        this.objectMapper = objectMapper;
    }

<<<<<<< HEAD
=======
    public List<AdminResponseDTO> listAdmin() {
        List<AdminResponseDTO> adminResponseDTOS = adminRepository.findAll().stream()
                .map(admin -> objectMapper.convertValue(admin, AdminResponseDTO.class))
                .toList();
        return adminResponseDTOS;
    }

>>>>>>> 814bcf09fc4a851dcf9f536425a72c894bbffe11

}
