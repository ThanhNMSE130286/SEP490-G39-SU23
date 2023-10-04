package com.teachsync.services.course;

import com.teachsync.dtos.BaseReadDTO;
import com.teachsync.dtos.course.CourseCreateDTO;
import com.teachsync.dtos.course.CourseReadDTO;
import com.teachsync.dtos.course.CourseUpdateDTO;
import com.teachsync.dtos.priceLog.PriceLogCreateDTO;
import com.teachsync.dtos.priceLog.PriceLogReadDTO;
import com.teachsync.dtos.priceLog.PriceLogUpdateDTO;
import com.teachsync.dtos.test.TestReadDTO;
import com.teachsync.entities.*;
import com.teachsync.repositories.CourseMaterialRepository;
import com.teachsync.repositories.CourseRepository;
import com.teachsync.repositories.MaterialRepository;
import com.teachsync.repositories.PriceLogRepository;
import com.teachsync.services.priceLog.PriceLogService;
import com.teachsync.services.test.TestService;
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

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private PriceLogService priceLogService;
    @Autowired
    private TestService testService;

    @Autowired
    private PriceLogRepository priceLogRepository;

    @Autowired
    private MiscUtil miscUtil;
    @Autowired
    private ModelMapper mapper;


    /* =================================================== CREATE =================================================== */
    @Override
    public Course createCourse(Course course) throws Exception {
        StringBuilder errorMsg = new StringBuilder();
        /* Validate input */
        /* courseAlias */
        errorMsg.append(
                miscUtil.validateString(
                        "Mã khóa học", course.getCourseAlias(), 1, 10,
                        List.of("required", "minLength", "maxLength", "onlyBlank", "startBlank", "endBlank", "specialChar")));
        /* courseName */
        errorMsg.append(
                miscUtil.validateString(
                        "Tên khóa học", course.getCourseName(), 1, 45,
                        List.of("required", "minLength", "maxLength", "onlyBlank", "startBlank", "endBlank", "specialChar")));
        /* courseDesc */
        errorMsg.append(
                miscUtil.validateString(
                        "Miêu tả khóa học", course.getCourseDesc(), 1, 9999,
                        List.of("nullOrMinLength", "maxLength", "onlyBlank", "startBlank", "endBlank", "specialChar")));
        /* courseImg */
        /* TODO: check valid link */
        /* numSession */
        errorMsg.append(
                miscUtil.validateNumber(
                        "Số tiết học", Double.valueOf(course.getNumSession()), 1.0, 100.0, 1.0,
                        List.of("min", "max", "onlyBlank", "step")));
        /* minScore */
        errorMsg.append(
                miscUtil.validateNumber(
                        "Điểm tối thiểu", course.getMinScore(), 0.0, 10.0, 0.01,
                        List.of("min", "max", "onlyBlank", "step")));
        /* minAttendant */
        errorMsg.append(
                miscUtil.validateNumber(
                        "Điểm danh tối thiểu", course.getMinAttendant(), 0.0, 100.0, 0.01,
                        List.of("min", "max", "onlyBlank", "step")));

        /* Check FK */
        /* No FK */

        /* Check duplicate */
        if (courseRepository
                .existsByCourseNameOrCourseAliasAndStatusNot(
                        course.getCourseName(),
                        course.getCourseAlias(),
                        Status.DELETED)) {
            errorMsg.append("Already exists Course with Name: ").append(course.getCourseName())
                    .append(" or with Alias: ").append(course.getCourseAlias());
        }

        if (!errorMsg.isEmpty()) {
            throw new IllegalArgumentException(errorMsg.toString());
        }

        /* Save to DB */
        course = courseRepository.save(course);

        return course;
    }
    @Override
    public CourseReadDTO createCourseByDTO(CourseCreateDTO createDTO) throws Exception {
        Course course = mapper.map(createDTO, Course.class);

        course = createCourse(course);

        /* Create dependency */
        PriceLogCreateDTO priceLogCreateDTO = createDTO.getPrice();
        priceLogCreateDTO.setCourseId(course.getId());
        priceLogService.createPriceLogByDTO(priceLogCreateDTO);

        return wrapDTO(course, null);
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

        return wrapPageDTO(coursePage, null);
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

        List<CourseReadDTO> courseDTOList = wrapListDTO(coursePage.getContent(), null);

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
    public CourseReadDTO getDTOById(Long id, Collection<DtoOption> options) throws Exception {
        Course course = getById(id);

        if (course == null) {
            return null;
        }

        return wrapDTO(course, options);
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

        return wrapPageDTO(coursePage, null);
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

        return wrapListDTO(courseList, options);
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

    /* courseName */
    @Override
    public List<Course> getAllByNameContains(String courseName) throws Exception {
        List<Course> courseList =
                courseRepository.findAllByCourseNameContainsAndStatusNot(courseName, Status.DELETED);

        if (courseList.isEmpty()) {
            return null;
        }

        return courseList;
    }
    @Override
    public List<CourseReadDTO> getAllDTOByNameContains(String courseName, Collection<DtoOption> options) throws Exception {
        List<Course> courseList = getAllByNameContains(courseName);

        if (courseList == null) {
            return null; }

        return wrapListDTO(courseList, options);
    }


    /* =================================================== UPDATE =================================================== */
    @Override
    public Course updateCourse(Course course) throws Exception {
        /* Check exists */
        Course oldCourse = getById(course.getId());
        if (Objects.isNull(oldCourse)) {
            throw new IllegalArgumentException(
                    "No Course found with id: " + course.getId());
        }
        course.setCreatedAt(oldCourse.getCreatedAt());
        course.setCreatedBy(oldCourse.getCreatedBy());

        /* Validate input */
        /* TODO: valid ImgLink, ... */

        /* Check FK */
        /* No FK */

        /* Check duplicate */
        if (courseRepository
                .existsByIdNotAndCourseNameOrCourseAliasAndStatusNot(
                        course.getId(),
                        course.getCourseName(),
                        course.getCourseAlias(),
                        Status.DELETED)) {
            throw new IllegalArgumentException(
                    "Already exists Course with Name: " + course.getCourseName()
                            + " or with Alias: " + course.getCourseAlias());
        }

        /* Save to DB */
        course = courseRepository.save(course);

        return course;
    }
    @Override
    public CourseReadDTO updateCourseByDTO(CourseUpdateDTO updateDTO) throws Exception {
        Course course = mapper.map(updateDTO, Course.class);

        course = updateCourse(course);

        /* Update dependency */
        /* Close old price */
        PriceLogReadDTO oldPrice = priceLogService.getCurrentDTOByCourseId(course.getId());
        oldPrice.setValidTo(LocalDateTime.now());
        priceLogService.updatePriceLogByDTO(mapper.map(oldPrice, PriceLogUpdateDTO.class));

        /* Create dependency */
        PriceLogCreateDTO priceLogCreateDTO = updateDTO.getPrice();
        priceLogCreateDTO.setCourseId(course.getId());
        priceLogService.createPriceLogByDTO(priceLogCreateDTO);

        return wrapDTO(course, null);
    }


    /* =================================================== DELETE =================================================== */
    @Override
    @Transactional
    public void deleteCourse(Long id, Long userId) throws Exception {
        Course course = courseRepository.findById(id).orElseThrow(() -> new Exception("không tìm thấy khóa học"));
        PriceLog priceLog = priceLogRepository.findByCourseIdAndValidBetweenAndStatusNot(
                        id, LocalDateTime.now(), Status.DELETED)
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
    public CourseReadDTO wrapDTO(Course course, Collection<DtoOption> options) throws Exception {
        CourseReadDTO dto = mapper.map(course, CourseReadDTO.class);

        /* Add Dependency */
        if (options != null && !options.isEmpty()) {
            if (options.contains(DtoOption.MATERIAL_LIST)) {
                List<CourseMaterial> courseMaterialList = courseMaterialRepository.findAllByCourseIdAndStatusNot(dto.getId(),Status.DELETED);
                Collection<Long> idCollection = new ArrayList<>(courseMaterialList.size());
                for(CourseMaterial item : courseMaterialList){
                    idCollection.add(item.getMaterialId());
                }
                List<Material> materialList = materialRepository.findAllByIdInAndStatusNot(idCollection,Status.DELETED);
                dto.setMaterialList(materialList);
            }

            if (options.contains(DtoOption.TEST_LIST)) {
//                dto.setTestList();
            }

            if (options.contains(DtoOption.CURRENT_PRICE)) {
                PriceLogReadDTO priceLogDTO = priceLogService.getCurrentDTOByCourseId(course.getId());

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
        Map<Long, List<TestReadDTO>> courseIdTestDTOListMap = new HashMap<>();
        Map<Long, PriceLogReadDTO> courseIdLatestPriceLogMap = new HashMap<>();

        if (options != null && !options.isEmpty()) {
            Set<Long> courseIdSet = new HashSet<>();

            for (Course course : courseCollection) {
                courseIdSet.add(course.getId());
            }

            if (options.contains(DtoOption.MATERIAL_LIST)) {
//                dto.setMaterialList();
            }

            if (options.contains(DtoOption.TEST_LIST)) {
                courseIdTestDTOListMap =
                        testService.mapCourseIdListDTOByCourseIdIn(courseIdSet, options);
            }

            if (options.contains(DtoOption.CURRENT_PRICE)) {
                courseIdLatestPriceLogMap =
                        priceLogService.mapCourseIdCurrentPriceDTOByCourseIdIn(courseIdSet);
            }
        }


        for (Course course : courseCollection) {
            dto = mapper.map(course, CourseReadDTO.class);

            /* Add Dependency */
//            dto.setClazzList();
//            dto.setMaterialList();
            dto.setTestList(courseIdTestDTOListMap.get(course.getId()));

            dto.setCurrentPrice(courseIdLatestPriceLogMap.get(course.getId()));

            dtoList.add(dto);
        }

        return dtoList;
    }
    @Override
    public Page<CourseReadDTO> wrapPageDTO(
            Page<Course> coursePage, Collection<DtoOption> options) throws Exception {
        return new PageImpl<>(
                wrapListDTO(coursePage.getContent(), options),
                coursePage.getPageable(),
                coursePage.getTotalPages());
    }
}