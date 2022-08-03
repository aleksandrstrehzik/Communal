package org.example.service.interfaces;

import org.example.dto.AdminDto;

import java.util.List;

public interface AdminService {

    AdminDto getAdmin(String label);

    void createAdmin(AdminDto admin);

    List<AdminDto> getAllAdmins();
}
