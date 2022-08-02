package org.example.dao;

import org.example.entity.MonthReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.util.List;

@Repository
public interface MonthReportRepository extends JpaRepository<MonthReport, Integer> {

    @Query("select m from MonthReport m where m.id = " +
            "(select max(mr.id) from MonthReport mr where mr.consumer.id =:consId)")
    MonthReport findPreviousReport(Integer consId);

    List<MonthReport> findAllByConsumer_Id(Integer id);

    List<MonthReport> findAllByConsumer_IdAndMonth(Integer id, Month month);

    List<MonthReport> findAllByConsumer_IdAndYear(Integer id, Integer year);

    List<MonthReport> findAllByConsumer_IdAndYearAndMonth(Integer id, Integer year, Month month);

    @Query("select m from MonthReport m where m.consumer.id =:consId and m.id >:repId")
    List<MonthReport> findAllHightReport(Integer consId, Integer repId);

    MonthReport findByConsumer_IdAndId(Integer consId, Integer repId);

}
