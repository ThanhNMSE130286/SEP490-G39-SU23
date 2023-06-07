package com.teachsync.repositories;

import com.teachsync.entities.Course;
import com.teachsync.utils.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {


    Page<Course> findAllByStatusNot(Status status, Pageable pageable);

    /* id */
    Optional<Course> findByIdAndStatusNot(Long id, Status status);
    List<Course> findAllByIdInAndStatusNot(Collection<Long> idCollection, Status status);

    /* Check duplicate */
}