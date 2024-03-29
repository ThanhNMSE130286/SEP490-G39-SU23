package com.teachsync.repositories;

import com.teachsync.entities.ScheduleCategory;
import com.teachsync.utils.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ScheduleCateRepository extends JpaRepository<ScheduleCategory, Long> {

    Page<ScheduleCategory> findAllByStatusNot(Status status, Pageable pageable);

    List<ScheduleCategory> findAllByStatusNot(Status status);

    /* id */
    Optional<ScheduleCategory> findByIdAndStatusNot(long id, Status status);
    List<ScheduleCategory> findAllByIdInAndStatusNot(Collection<Long> idCollection, Status status);


}
