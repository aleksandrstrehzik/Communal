package org.example.mapper;

import org.example.dto.HeatTariffDto;
import org.example.dto.OperatorDto;
import org.example.entity.HeatTariff;
import org.example.entity.Operator;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HeatTariffMapper {

    HeatTariffDto toDto(HeatTariff heatTariff);

    HeatTariff toEntity(HeatTariffDto heatTariffDto);
}
