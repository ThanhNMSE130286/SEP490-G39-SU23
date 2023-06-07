package com.teachsync.services.course;

import com.teachsync.dtos.course.CourseReadDTO;
import com.teachsync.dtos.priceLog.PriceLogReadDTO;
import com.teachsync.entities.Course;
import com.teachsync.repositories.CourseRepository;
import com.teachsync.services.priceLog.PriceLogService;
import com.teachsync.utils.MiscUtil;
import com.teachsync.utils.enums.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PriceLogService priceLogService;

    @Autowired
    private MiscUtil miscUtil;
    @Autowired
    private ModelMapper mapper;



    /* =================================================== CREATE =================================================== */



    /* =================================================== READ ===================================================== */
    @Override
    public Page<Course> getPageAll(Pageable paging) throws Exception {
        if (paging == null) {
            paging = miscUtil.defaultPaging(); }

        Page<Course> coursePage =
                courseRepository.findAllByStatusNot(Status.DELETED, paging);

        if (coursePage.isEmpty()) {
            return null; }

        return coursePage;
    }
    @Override
    public Page<CourseReadDTO> getPageDTOAll(Pageable paging) throws Exception {
        Page<Course> coursePage = getPageAll(paging);

        if (coursePage == null) {
            return null; }

        return wrapPageDTO(coursePage);
    }

    @Override
    public Course getById(Long id) throws Exception {
        Optional<Course> course =
                courseRepository.findByIdAndStatusNot(id, Status.DELETED);

        return course.orElse(null);
    }
    @Override
    public CourseReadDTO getDTOById(Long id) throws Exception {
        Course course = getById(id);

        if (course == null) {
            return null; }

        return wrapDTO(course);
    }



    /* =================================================== UPDATE =================================================== */



    /* =================================================== DELETE =================================================== */



    /* =================================================== WRAPPER ================================================== */
    @Override
    public CourseReadDTO wrapDTO(Course course) throws Exception {
        CourseReadDTO dto = mapper.map(course, CourseReadDTO.class);

        /* Add Dependency */
        /* TODO: replace with dto and call service */
        PriceLogReadDTO priceLogDTO =
                priceLogService.getLatestDTOByCourseId(course.getId());

        dto.setCurrentPrice(priceLogDTO);

//        dto.setMaterialList();
//        dto.setClassroomList();

        return dto;
    }

    @Override
    public List<CourseReadDTO> wrapListDTO(Collection<Course> courseCollection) throws Exception {
        List<CourseReadDTO> dtoList = new ArrayList<>();

        CourseReadDTO dto;

        for (Course course : courseCollection) {
            dto = mapper.map(course, CourseReadDTO.class);

            /* Add Dependency */
//            dto.setPriceLogList();
//            dto.setMaterialList();
//            dto.setClassroomList();

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public Page<CourseReadDTO> wrapPageDTO(Page<Course> coursePage) throws Exception {
        return new PageImpl<>(
                wrapListDTO(coursePage.getContent()),
                coursePage.getPageable(),
                coursePage.getTotalPages());
    }
}
