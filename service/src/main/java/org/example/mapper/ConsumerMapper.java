package org.example.mapper;

import org.example.dto.ConsumerDto;
import org.example.entity.Consumer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConsumerMapper {

    ConsumerDto toDto(Consumer consumer);

    Consumer toEntity(ConsumerDto consumerDto);
}
