package org.example.mapper;

import org.example.dto.ElectricityTariffDto;
import org.example.entity.ElectricityTariff;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ElectricityTariffMapper {

    ElectricityTariffDto toDto(ElectricityTariff electricityTariff);

    ElectricityTariff toEntity(ElectricityTariffDto ElectricityTariffDto);
}
