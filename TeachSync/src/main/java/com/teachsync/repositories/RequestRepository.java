package com.teachsync.repositories;


import com.teachsync.entities.Request ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RequestRepository extends JpaRepository<Request , Long> {

}