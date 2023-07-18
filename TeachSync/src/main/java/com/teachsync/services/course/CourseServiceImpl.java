package com.teachsync.services.course;

import com.teachsync.dtos.BaseReadDTO;
import com.teachsync.dtos.course.CourseCreateDTO;
import com.teachsync.dtos.course.CourseReadDTO;
import com.teachsync.dtos.priceLog.PriceLogReadDTO;
import com.teachsync.entities.BaseEntity;
import com.teachsync.entities.Course;
import com.teachsync.entities.PriceLog;
import com.teachsync.repositories.CourseRepository;
import com.teachsync.repositories.PriceLogRepository;
import com.teachsync.services.priceLog.PriceLogService;
import com.teachsync.utils.MiscUtil;
import com.teachsync.utils.enums.DtoOption;
import com.teachsync.utils.enums.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PriceLogService priceLogService;

    @Autowired
    private PriceLogRepository priceLogRepository;

    @Autowired
    private MiscUtil miscUtil;
    @Autowired
    private ModelMapper mapper;


    /* =================================================== CREATE =================================================== */
    @Override
    @Transactional
    public CourseReadDTO addCourse(CourseCreateDTO courseDTO, Long userId) throws Exception {
        Course course = new Course();

        course.setCourseName(courseDTO.getCourseName());
        //TODO : process upload file
        course.setCourseImg(courseDTO.getCourseImg());
        course.setCourseDesc(courseDTO.getCourseDesc());
        course.setMinScore(courseDTO.getMinScore());
        course.setMinAttendant(courseDTO.getMinAttendant());
        course.setStatus(courseDTO.getStatus());
        course.setCreatedBy(userId);
        Course courseDb = courseRepository.save(course);
        if (ObjectUtils.isEmpty(courseDb)) {
            throw new Exception("Tạo khóa học thất bại");
        }

        //add price
        PriceLog priceLog = new PriceLog();
        priceLog.setCourseId(courseDb.getId());
        priceLog.setPrice(courseDTO.getPrice());
        priceLog.setStatus(Status.CREATED);
        priceLog.setPromotionAmount(0.0);
        priceLog.setIsPromotion(false);
        priceLog.setValidFrom(LocalDateTime.now());
        priceLog.setValidTo(LocalDateTime.now());
        priceLog.setCreatedBy(userId);
        priceLog.setUpdatedBy(userId);

        PriceLog priceLogDb = priceLogRepository.save(priceLog);
        if (ObjectUtils.isEmpty(priceLogDb)) {
            throw new Exception("Tạo giá của khóa học thất bại");
        }
        return mapper.map(courseDb, CourseReadDTO.class);
    }

    /* =================================================== READ ===================================================== */
    @Override
    public Page<Course> getPageAll(Pageable paging) throws Exception {
        if (paging == null) {
            paging = miscUtil.defaultPaging();
        }

        Page<Course> coursePage =
                courseRepository.findAllByStatusNot(Status.DELETED, paging);

        if (coursePage.isEmpty()) {
            return null;
        }

        return coursePage;
    }

    @Override
    public Page<CourseReadDTO> getPageDTOAll(Pageable paging) throws Exception {
        Page<Course> coursePage = getPageAll(paging);

        if (coursePage == null) {
            return null;
        }

        return wrapPageDTO(coursePage);
    }
    @Override
    public Page<CourseReadDTO> getPageDTOAllHotCourse(Pageable paging) throws Exception {
        Page<PriceLogReadDTO> priceLogDTOPage = priceLogService.getPageAllLatestPromotionDTO(paging);

        if (priceLogDTOPage == null) {
            return null; }

        Map<Long, PriceLogReadDTO> courseIdPriceLogDTOMap =
                priceLogDTOPage.stream()
                        .collect(Collectors.toMap(PriceLogReadDTO::getCourseId, Function.identity()));

        Page<Course> coursePage = getPageAllByIdIn(priceLogDTOPage.getPageable(), courseIdPriceLogDTOMap.keySet());

        if (coursePage == null) {
            return null; }

        List<CourseReadDTO> courseDTOList = wrapListDTO(coursePage.getContent());

        courseDTOList =
                courseDTOList.stream()
                        .peek(dto -> dto.setCurrentPrice(courseIdPriceLogDTOMap.get(dto.getId())))
                        .collect(Collectors.toList());

        return new PageImpl<>(
                courseDTOList,
                coursePage.getPageable(),
                coursePage.getTotalPages());
    }

    @Override
    public List<Course> getAll() throws Exception {
        List<Course> coursePage =
                courseRepository.findAllByStatusNot(Status.DELETED);

        if (coursePage.isEmpty()) {
            return null;
        }

        return coursePage;
    }

    @Override
    public List<CourseReadDTO> getAllDTO() throws Exception {
        List<Course> courseList = getAll();

        if (courseList == null) {
            return null;
        }

        return wrapListDTO(courseList);
    }

    @Override
    public List<CourseReadDTO> getAllDTO(Collection<DtoOption> options) throws Exception {
        List<Course> courseList = getAll();

        if (courseList == null) {
            return null;
        }

        return wrapListDTO(courseList, options);
    }
    @Override
    public Map<Long, CourseReadDTO> mapIdDTO(Collection<DtoOption> options) throws Exception {
        List<CourseReadDTO> courseDTOList = getAllDTO(options);

        if (courseDTOList == null) {
            return new HashMap<>();
        }

        return courseDTOList.stream()
                .collect(Collectors.toMap(BaseReadDTO::getId, Function.identity()));
    }

    /* id */
    @Override
    public Course getById(Long id) throws Exception {
        return courseRepository
                .findByIdAndStatusNot(id, Status.DELETED)
                .orElse(null);
    }
    @Override
    public CourseReadDTO getDTOById(Long id) throws Exception {
        Course course = getById(id);

        if (course == null) {
            return null;
        }

        return wrapDTO(course);
    }

    @Override
    public Page<Course> getPageAllByIdIn(Pageable paging, Collection<Long> courseIdCollection) throws Exception {
        if (paging == null) {
            paging = miscUtil.defaultPaging(); }

        Page<Course> coursePage =
                courseRepository.findAllByIdInAndStatusNot(courseIdCollection, Status.DELETED, paging);

        if (coursePage.isEmpty()) {
            return null; }

        return coursePage;
    }
    @Override
    public Page<CourseReadDTO> getPageDTOAllByIdIn(Pageable paging, Collection<Long> courseIdCollection) throws Exception {
        Page<Course> coursePage = getPageAllByIdIn(paging, courseIdCollection);

        if (coursePage == null) {
            return null; }

        return wrapPageDTO(coursePage);
    }

    @Override
    public List<Course> getAllByIdIn(Collection<Long> courseIdCollection) throws Exception {
        List<Course> courseList =
                courseRepository.findAllByIdInAndStatusNot(courseIdCollection, Status.DELETED);

        if (courseList.isEmpty()) {
            return null; }

        return courseList;
    }
    @Override
    public Map<Long, String> mapCourseIdCourseNameByIdIn(Collection<Long> courseIdCollection) throws Exception {
        List<Course> courseList = getAllByIdIn(courseIdCollection);

        if (courseList == null) {
            return new HashMap<>(); }

        return courseList.stream()
                .collect(Collectors.toMap(BaseEntity::getId, Course::getCourseName));
    }
    @Override
    public Map<Long, String> mapCourseIdCourseAliasByIdIn(Collection<Long> courseIdCollection) throws Exception {
        List<Course> courseList = getAllByIdIn(courseIdCollection);

        if (courseList == null) {
            return new HashMap<>();
        }

        return courseList.stream()
                .collect(Collectors.toMap(BaseEntity::getId, Course::getCourseAlias));
    }
    @Override
    public List<CourseReadDTO> getAllDTOByIdIn(
            Collection<Long> courseIdCollection, Collection<DtoOption> options) throws Exception {
        List<Course> courseList = getAllByIdIn(courseIdCollection);

        if (courseList == null) {
            return null; }

        return wrapListDTO(courseList);
    }
    @Override
    public Map<Long, CourseReadDTO> mapIdDTOByIdIn(
            Collection<Long> courseIdCollection, Collection<DtoOption> options) throws Exception {
        List<CourseReadDTO> courseDTOList = getAllDTOByIdIn(courseIdCollection, options);

        if (courseDTOList == null) {
            return new HashMap<>();
        }

        return courseDTOList.stream()
                .collect(Collectors.toMap(BaseReadDTO::getId, Function.identity()));
    }


    /* =================================================== UPDATE =================================================== */
    @Override
    @Transactional
    public CourseReadDTO editCourse(CourseReadDTO courseReadDTO, Long userId) throws Exception {
        Course course = courseRepository.findById(courseReadDTO.getId()).orElseThrow(() -> new Exception("không tìm thấy khóa học"));

        course.setCourseName(courseReadDTO.getCourseName());
        //TODO : process upload file
        course.setCourseImg(courseReadDTO.getCourseImg());
        course.setCourseDesc(courseReadDTO.getCourseDesc());
        course.setMinScore(courseReadDTO.getMinScore());
        course.setMinAttendant(courseReadDTO.getMinAttendant());
        course.setStatus(Status.UPDATED);
        course.setUpdatedBy(userId);
        Course courseDb = courseRepository.save(course);
        if (ObjectUtils.isEmpty(courseDb)) {
            throw new Exception("Cập nhật khóa học thất bại");
        }

        //add price
        PriceLog priceLog = priceLogRepository.findByCourseIdAndValidBetweenAndStatusNot(
                course.getId(), LocalDateTime.now(), Status.DELETED)
                .orElseThrow(() -> new Exception("không tìm thấy giá"));
        priceLog.setPrice(courseReadDTO.getCurrentPrice().getPrice());
        priceLog.setStatus(Status.UPDATED);
        if (!ObjectUtils.isEmpty(courseReadDTO.getCurrentPrice().getPromotionAmount()) && courseReadDTO.getCurrentPrice().getPromotionAmount() > 0) {
            priceLog.setPromotionAmount(courseReadDTO.getCurrentPrice().getPromotionAmount());
            priceLog.setPromotionType(courseReadDTO.getCurrentPrice().getPromotionType());
            priceLog.setPromotionDesc(courseReadDTO.getCurrentPrice().getPromotionDesc());
            priceLog.setIsPromotion(true);
        }
        priceLog.setValidFrom(LocalDateTime.now());
        priceLog.setValidTo(LocalDateTime.now());
        priceLog.setUpdatedBy(userId);

        PriceLog priceLogDb = priceLogRepository.save(priceLog);
        if (ObjectUtils.isEmpty(priceLogDb)) {
            throw new Exception("Sửa giá tiền của khóa học thất bại");
        }
        return mapper.map(courseDb, CourseReadDTO.class);
    }


    /* =================================================== DELETE =================================================== */
    @Override
    @Transactional
    public void deleteCourse(Long Id, Long userId) throws Exception {
        Course course = courseRepository.findById(Id).orElseThrow(() -> new Exception("không tìm thấy khóa học"));
        PriceLog priceLog = priceLogRepository.findByCourseIdAndValidBetweenAndStatusNot(
                Id, LocalDateTime.now(), Status.DELETED)
                .orElseThrow(() -> new Exception("không tìm thấy giá khóa học"));
        priceLog.setStatus(Status.DELETED);
        priceLog.setUpdatedBy(userId);
        course.setStatus(Status.DELETED);
        course.setUpdatedBy(userId);
        priceLogRepository.save(priceLog);
        courseRepository.save(course);

    }


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

    @Override
    public CourseReadDTO wrapDTO(Course course, Collection<DtoOption> options) throws Exception {
        CourseReadDTO dto = mapper.map(course, CourseReadDTO.class);

        /* Add Dependency */
        if (options != null && !options.isEmpty()) {
            if (options.contains(DtoOption.CLAZZ_LIST)) {
//                dto.setClazzList();
            }

            if (options.contains(DtoOption.MATERIAL_LIST)) {
//                dto.setMaterialList();
            }

            if (options.contains(DtoOption.TEST_LIST)) {
//                dto.setTestList();
            }

            if (options.contains(DtoOption.CURRENT_PRICE)) {
                PriceLogReadDTO priceLogDTO = priceLogService.getLatestDTOByCourseId(course.getId());

                dto.setCurrentPrice(priceLogDTO);
            }
        }

        return dto;
    }
    @Override
    public List<CourseReadDTO> wrapListDTO(
            Collection<Course> courseCollection, Collection<DtoOption> options) throws Exception {
        List<CourseReadDTO> dtoList = new ArrayList<>();

        CourseReadDTO dto;

//        Map<Long, List<ClazzReadDTO>> courseIdClazzDTOListMap = new HashMap<>();
//        Map<Long, List<MaterialReadDTO>> courseIdMaterialDTOListMap = new HashMap<>();
//        Map<Long, List<TestReadDTO>> courseIdTestDTOListMap = new HashMap<>();
        Map<Long, PriceLogReadDTO> courseIdLatestPriceLogMap = new HashMap<>();

        if (options != null && !options.isEmpty()) {
            Set<Long> courseIdSet = new HashSet<>();

            for (Course course : courseCollection) {
                courseIdSet.add(course.getId());
            }

            if (options.contains(DtoOption.CLAZZ_LIST)) {
//                dto.setClazzList();
            }

            if (options.contains(DtoOption.MATERIAL_LIST)) {
//                dto.setMaterialList();
            }

            if (options.contains(DtoOption.TEST_LIST)) {
//                dto.setTestList();
            }

            if (options.contains(DtoOption.CURRENT_PRICE)) {
                courseIdLatestPriceLogMap =
                        priceLogService.mapCourseIdLatestDTOByCourseIdIn(courseIdSet);
            }
        }


        for (Course course : courseCollection) {
            dto = mapper.map(course, CourseReadDTO.class);

            /* Add Dependency */
//            dto.setClazzList();
//            dto.setMaterialList();
//            dto.setTestList();

            dto.setCurrentPrice(courseIdLatestPriceLogMap.get(course.getId()));

            dtoList.add(dto);
        }

        return dtoList;
    }
    @Override
    public Page<CourseReadDTO> wrapPageDTO(
            Page<Course> coursePage, Collection<DtoOption> options) throws Exception {
        return new PageImpl<>(
                wrapListDTO(coursePage.getContent()),
                coursePage.getPageable(),
                coursePage.getTotalPages());
    }
}
