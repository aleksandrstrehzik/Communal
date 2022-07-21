package org.example.service;

import org.example.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ConsumerService {

    Page<ConsumerDto> findAllPaginated(int pageNumber, String sortField, String sortDirection);

    ConsumerDto getConsumerById(Integer id);

    void deleteConsumer(Integer id);

    void createConsumer(ConsumerDto cons, Integer operId, Integer elId,
                        Integer gasId, Integer heatId);

    void editConsumer(ConsumerDto cons, Integer operId, Integer elId,
                      Integer gasId, Integer heatId);

    List<ConsumerDto> getAllFindConsumers(String name, String surname);
}
