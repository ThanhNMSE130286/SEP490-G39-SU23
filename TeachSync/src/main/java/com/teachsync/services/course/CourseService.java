package com.teachsync.services.course;

import com.teachsync.dtos.course.CourseCreateDTO;
import com.teachsync.dtos.course.CourseReadDTO;
import com.teachsync.entities.Course;
import com.teachsync.utils.enums.DtoOption;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CourseService {
    /* =================================================== CREATE =================================================== */
    CourseReadDTO addCourse(CourseCreateDTO courseDTO, Long userId) throws Exception;

    /* =================================================== READ ===================================================== */
    Page<Course> getPageAll(Pageable paging) throws Exception;

    Page<CourseReadDTO> getPageDTOAll(Pageable paging) throws Exception;
    Page<CourseReadDTO> getPageDTOAllHotCourse(Pageable paging) throws Exception;

    List<Course> getAll() throws Exception;

    @Deprecated
    List<CourseReadDTO> getAllDTO() throws Exception;
    List<CourseReadDTO> getAllDTO(Collection<DtoOption> options) throws Exception;
    Map<Long, CourseReadDTO> mapIdDTO(Collection<DtoOption> options) throws Exception;

    /* id */
    Course getById(Long id) throws Exception;
    CourseReadDTO getDTOById(Long id) throws Exception;

    Page<Course> getPageAllByIdIn(Pageable paging, Collection<Long> courseIdCollection) throws Exception;
    Page<CourseReadDTO> getPageDTOAllByIdIn(Pageable paging, Collection<Long> courseIdCollection) throws Exception;

    List<Course> getAllByIdIn(Collection<Long> courseIdCollection) throws Exception;
    Map<Long, String> mapCourseIdCourseNameByIdIn(Collection<Long> courseIdCollection) throws Exception;
    Map<Long, String> mapCourseIdCourseAliasByIdIn(Collection<Long> courseIdCollection) throws Exception;


    /* =================================================== UPDATE =================================================== */

    CourseReadDTO editCourse(CourseReadDTO courseReadDTO, Long userId) throws Exception;

    /* =================================================== DELETE =================================================== */

    void deleteCourse(Long Id, Long userId) throws Exception;

    /* =================================================== WRAPPER ================================================== */
    @Deprecated
    CourseReadDTO wrapDTO(Course course) throws Exception;
    @Deprecated
    List<CourseReadDTO> wrapListDTO(Collection<Course> courseCollection) throws Exception;
    @Deprecated
    Page<CourseReadDTO> wrapPageDTO(Page<Course> coursePage) throws Exception;

    CourseReadDTO wrapDTO(Course course, Collection<DtoOption> options) throws Exception;
    List<CourseReadDTO> wrapListDTO(Collection<Course> courseCollection, Collection<DtoOption> options) throws Exception;
    Page<CourseReadDTO> wrapPageDTO(Page<Course> coursePage, Collection<DtoOption> options) throws Exception;
}
