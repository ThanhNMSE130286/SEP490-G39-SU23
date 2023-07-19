package com.teachsync.repositories;

import com.teachsync.entities.ApplicationDetail;
import com.teachsync.utils.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ApplicationDetailRepository extends JpaRepository<ApplicationDetail, Long> {

    /* applicationId */
    List<ApplicationDetail> findAllByApplicationIdAndStatusNot(Long applicationId, Status status);
    List<ApplicationDetail> findAllByApplicationIdInAndStatusNot(Collection<Long> applicationIdCollection, Status status);
}