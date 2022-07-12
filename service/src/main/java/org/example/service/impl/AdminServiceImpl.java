package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.AdminRepository;
import org.example.entity.Admin;
import org.example.entity.Operator;
import org.example.mapper.AdminMapper;
import org.example.service.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public Admin getAdmin(String label) {
        return adminRepository.findAdminByLabel(label);
    }

    public List<Admin> getAllAdmin() {
        return adminRepository.findAll();
    }
}
