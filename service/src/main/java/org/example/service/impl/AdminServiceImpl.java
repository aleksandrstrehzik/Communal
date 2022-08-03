package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.dao.AdminRepository;
import org.example.dao.RoleRepository;
import org.example.dao.UserRepository;
import org.example.dto.AdminDto;
import org.example.entity.Admin;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.mapper.AdminMapper;
import org.example.service.interfaces.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static org.example.service.impl.MockUtils.ADMIN;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public AdminDto getAdmin(String label) {
        return adminMapper.toDto(adminRepository.findAdminByLabel(label));
    }

    @Transactional
    public void createAdmin(AdminDto adminDto) {
        if (adminRepository.findAdminByLabel(adminDto.getLabel()) != null) {
            return;
        }
        Admin save = adminRepository.save(adminMapper.toEntity(adminDto));
        String adminLabel = adminDto.getLabel();
        User user = User.builder()
                .userName(adminLabel)
                .password(bCryptPasswordEncoder.encode(adminLabel))
                .admin(save)
                .build();
        Role role = roleRepository.findByName(ADMIN);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional
    public List<AdminDto> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
    }

}
