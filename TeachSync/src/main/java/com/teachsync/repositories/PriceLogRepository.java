package com.teachsync.repositories;

import com.teachsync.entities.PriceLog;
import com.teachsync.utils.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PriceLogRepository extends JpaRepository<PriceLog, Long> {

    Page<PriceLog> findAllByStatusNot(Status status, Pageable pageable);

    /* id */
    Optional<PriceLog> findByIdAndStatusNot(Long id, Status status);
    List<PriceLog> findAllByIdInAndStatusNot(Collection<Long> idCollection, Status status);

    /* courseId */
    Optional<PriceLog> findByCourseIdAndIsCurrentTrueAndStatusNot(Long courseId, Status status);
    List<PriceLog> findAllByCourseIdAndStatusNot(Long courseId, Status status);
    List<PriceLog> findAllByCourseIdInAndStatusNot(Collection<Long> courseIdCollection, Status status);

    /* Check duplicate */
}