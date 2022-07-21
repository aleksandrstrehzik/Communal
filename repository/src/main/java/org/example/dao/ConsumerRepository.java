package org.example.dao;

import org.example.entity.Consumer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsumerRepository extends JpaRepository<Consumer, Integer> {

    List<Consumer> findAllByOperator_Id(Integer id);

    List<Consumer> findAllByNameAndSurname(String name, String surname);
}
