package org.example.dao;

import org.example.entity.MonthReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthReportRepository extends JpaRepository<MonthReport, Integer> {
}
