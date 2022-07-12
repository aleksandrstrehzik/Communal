package org.example.mapper;

import org.example.dto.OperatorDto;
import org.example.entity.Operator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OperatorMapper {

    OperatorDto toDto(Operator operator);

    Operator toEntity(OperatorDto operatorDto);
}
