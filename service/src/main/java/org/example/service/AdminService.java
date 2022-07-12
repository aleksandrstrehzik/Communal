package org.example.service;

import org.example.entity.Admin;

import java.util.List;

public interface AdminService {

    Admin getAdmin(String label);

    List<Admin> getAllAdmin();

}
