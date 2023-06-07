package com.teachsync.services.course;

import com.teachsync.dtos.course.CourseReadDTO;
import com.teachsync.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface CourseService {
    /* =================================================== CREATE =================================================== */



    /* =================================================== READ ===================================================== */
    Page<Course> getPageAll(Pageable paging) throws Exception;
    Page<CourseReadDTO> getPageDTOAll(Pageable paging) throws Exception;

    Course getById(Long id) throws Exception;
    CourseReadDTO getDTOById(Long id) throws Exception;



    /* =================================================== UPDATE =================================================== */



    /* =================================================== DELETE =================================================== */



    /* =================================================== WRAPPER ================================================== */
    CourseReadDTO wrapDTO(Course course) throws Exception;

    List<CourseReadDTO> wrapListDTO(Collection<Course> courseCollection) throws Exception;

    Page<CourseReadDTO> wrapPageDTO(Page<Course> coursePage) throws Exception;
}
