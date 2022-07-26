package org.example.restController;

import lombok.RequiredArgsConstructor;
import org.example.dto.AdminDto;
import org.example.service.AdminService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class Rest {

    private final AdminService adminService;

    @GetMapping()
    public List<AdminDto> getAdmins() {
        return adminService.getAllAdmins();
    }
}
