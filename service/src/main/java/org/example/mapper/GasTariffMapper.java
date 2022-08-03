package org.example.mapper;

import org.example.dto.GasTariffDto;
import org.example.entity.GasTariff;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GasTariffMapper {

    GasTariffDto toDto(GasTariff gasTariff);

    GasTariff toEntity(GasTariffDto gasTariffDto);
}
