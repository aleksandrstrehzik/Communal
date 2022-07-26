package org.example.service;

import org.example.dto.AdminDto;
import org.example.entity.Admin;

import java.util.List;

public interface AdminService {

    AdminDto getAdmin(String label);

    void createAdmin(AdminDto admin);

    List<AdminDto> getAllAdmins();
}
