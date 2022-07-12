package org.example.mapper;


import org.example.dto.AdminDto;
import org.example.entity.Admin;
import org.mapstruct.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminDto adminToAdminDto(Admin admin);

    Admin adminDtoToAdmin(AdminDto adminDto);
}
