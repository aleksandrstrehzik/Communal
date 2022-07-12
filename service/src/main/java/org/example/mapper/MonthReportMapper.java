package org.example.mapper;

import org.example.dto.MonthReportDto;
import org.example.entity.MonthReport;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonthReportMapper {
    MonthReportDto toDto(MonthReport monthReport);

    MonthReport toEntity(MonthReportDto monthReportDto);
}
