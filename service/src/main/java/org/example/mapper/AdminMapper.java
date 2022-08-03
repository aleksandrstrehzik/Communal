package org.example.mapper;


import org.example.dto.AdminDto;
import org.example.entity.Admin;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminDto toDto(Admin admin);

    Admin toEntity(AdminDto adminDto);
}
