package org.example.mapper;

import org.example.dto.MessageDto;
import org.example.entity.Message;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageDto toDto(Message message);

    Message toEntity(MessageDto messageDto);
}
