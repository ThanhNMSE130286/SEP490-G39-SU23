package com.teachsync.services.courseSemester;

import com.teachsync.dtos.BaseReadDTO;
import com.teachsync.dtos.center.CenterReadDTO;
import com.teachsync.dtos.clazz.ClazzReadDTO;
import com.teachsync.dtos.course.CourseReadDTO;
import com.teachsync.dtos.courseSemester.CourseSemesterReadDTO;
import com.teachsync.dtos.semester.SemesterReadDTO;
import com.teachsync.entities.*;
import com.teachsync.repositories.CourseSemesterRepository;
import com.teachsync.repositories.SemesterRepository;
import com.teachsync.services.center.CenterService;
import com.teachsync.services.clazz.ClazzService;
import com.teachsync.services.course.CourseService;
import com.teachsync.services.semester.SemesterService;
import com.teachsync.utils.MiscUtil;
import com.teachsync.utils.enums.DtoOption;
import com.teachsync.utils.enums.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CourseSemesterServiceImpl implements CourseSemesterService {
    @Autowired
    private CourseSemesterRepository courseSemesterRepository;
    @Autowired
    private SemesterRepository semesterRepository;

    @Lazy
    @Autowired
    private ClazzService clazzService;
    @Lazy
    @Autowired
    private CourseService courseService;
    @Lazy
    @Autowired
    private CenterService centerService;
    @Lazy
    @Autowired
    private SemesterService semesterService;

    @Autowired
    private MiscUtil miscUtil;
    @Autowired
    private ModelMapper mapper;


    /* =================================================== CREATE =================================================== */
    @Override
    public CourseSemester createCourseSemester(CourseSemester courseSemester) throws Exception {
        /* Validate input */
        /* TODO: */

        /* Check FK */
        /* TODO: */

        /* Check duplicate */
        /* TODO: */

        /* Create */
        courseSemester = courseSemesterRepository.saveAndFlush(courseSemester);

        return courseSemester;
    }


    /* =================================================== READ ===================================================== */
    /* id */
    @Override
    public CourseSemester getById(Long id) throws Exception {
        Optional<CourseSemester> courseSemester =
                courseSemesterRepository.findByIdAndStatusNot(id, Status.DELETED);

        return courseSemester.orElse(null);
    }
    @Override
    public CourseSemesterReadDTO getDTOById(Long id, Collection<DtoOption> options) throws Exception {
        CourseSemester courseSemester = getById(id);

        if (courseSemester == null) {
            return null;
        }

        return wrapDTO(courseSemester, options);
    }

    @Override
    public List<CourseSemester> getAllByIdIn(Collection<Long> idCollection) throws Exception {
        List<CourseSemester> courseSemesterPage =
                courseSemesterRepository.findAllByIdInAndStatusNot(idCollection, Status.DELETED);

        if (courseSemesterPage.isEmpty()) {
            return null;
        }

        return courseSemesterPage;
    }
    @Override
    public List<CourseSemesterReadDTO> getAllDTOByIdIn(
            Collection<Long> idCollection, Collection<DtoOption> options) throws Exception {
        List<CourseSemester> courseSemesterList = getAllByIdIn(idCollection);

        if (courseSemesterList == null) {
            return null;
        }

        return wrapListDTO(courseSemesterList, options);
    }
    @Override
    public Map<Long, CourseSemesterReadDTO> mapIdDTOByIdIn(
            Collection<Long> idCollection, Collection<DtoOption> options) throws Exception {
        List<CourseSemesterReadDTO> courseSemesterDTOList = getAllDTOByIdIn(idCollection, options);

        if (courseSemesterDTOList == null) {
            return new HashMap<>(); }

        return courseSemesterDTOList.stream()
                .collect(Collectors.toMap(BaseReadDTO::getId, Function.identity()));
    }

    /* courseId */
    @Override
    public List<CourseSemester> getAllByCourseId(Long courseId) throws Exception {
        List<CourseSemester> courseSemesterPage =
                courseSemesterRepository.findAllByCourseIdAndStatusNot(courseId, Status.DELETED);

        if (courseSemesterPage.isEmpty()) {
            return null;
        }

        return courseSemesterPage;
    }
    @Override
    public List<CourseSemesterReadDTO> getAllDTOByCourseId(
            Long courseId, Collection<DtoOption> options) throws Exception {
        List<CourseSemester> courseSemesterList = getAllByCourseId(courseId);

        if (courseSemesterList == null) {
            return null;
        }

        return wrapListDTO(courseSemesterList, options);
    }

    @Override
    public List<CourseSemesterReadDTO> getAllLatestDTOByCourseId(
            Long courseId, Collection<DtoOption> options) throws Exception {
        List<Semester> semesterList = /* Sau 10 ngày để còn có hạn học sinh đăng ký */
                semesterRepository.findAllByStartDateAfterAndStatusNot(LocalDate.now().plusDays(10), Status.DELETED);
        Set<Long> semesterIdSet =
                semesterList.stream()
                        .map(BaseEntity::getId)
                        .collect(Collectors.toSet());

        List<CourseSemester> courseSemesterList = getAllByCourseIdAndSemesterIdIn(courseId, semesterIdSet);

        if (courseSemesterList == null) {
            return null;
        }

        return wrapListDTO(courseSemesterList, options);
    }
    @Override
    public Map<Long, CourseSemesterReadDTO> mapIdLatestDTOByCourseId(
            Long courseId, Collection<DtoOption> options) throws Exception {
        List<CourseSemesterReadDTO> latestDTOList = getAllLatestDTOByCourseId(courseId, options);

        if (latestDTOList == null) {
            return new HashMap<>();
        }

        return latestDTOList.stream()
                .collect(Collectors.toMap(BaseReadDTO::getId, Function.identity()));
    }
    /* courseId, semesterId */
    @Override
    public List<CourseSemester> getAllByCourseIdAndSemesterIdIn(
            Long courseId, Collection<Long> semesterIdCollection) throws Exception {
        List<CourseSemester> courseSemesterPage =
                courseSemesterRepository
                        .findAllByCourseIdAndSemesterIdInAndStatusNot(courseId, semesterIdCollection, Status.DELETED);

        if (courseSemesterPage.isEmpty()) {
            return null;
        }

        return courseSemesterPage;
    }
    @Override
    public Map<Long, String> mapIdSemesterIdCenterIdStringByCourseIdAndSemesterIdIn(
            Long courseId, Collection<Long> semesterIdCollection) throws Exception {
        List<CourseSemester> courseSemesterList = getAllByCourseIdAndSemesterIdIn(courseId, semesterIdCollection);

        if (courseSemesterList == null) {
            return new HashMap<>();
        }

        return courseSemesterList.stream()
                .collect(Collectors.toMap(
                        BaseEntity::getId,
                        cS -> cS.getSemesterId().toString().concat(cS.getCenterId().toString())));
    }
    @Override
    public List<CourseSemesterReadDTO> getAllDTOByCourseIdAndSemesterIdIn(
            Long courseId, Collection<Long> semesterIdCollection, Collection<DtoOption> options) throws Exception {
        List<CourseSemester> courseSemesterList = getAllByCourseIdAndSemesterIdIn(courseId, semesterIdCollection);

        if (courseSemesterList == null) {
            return null;
        }

        return wrapListDTO(courseSemesterList, options);
    }


    /* semesterId */
    @Override
    public List<CourseSemester> getAllBySemesterId(Long semesterId) throws Exception {
        List<CourseSemester> courseSemesterPage =
                courseSemesterRepository.findAllBySemesterIdAndStatusNot(semesterId, Status.DELETED);

        if (courseSemesterPage.isEmpty()) {
            return null;
        }

        return courseSemesterPage;
    }
    @Override
    public List<CourseSemesterReadDTO> getAllDTOBySemesterId(
            Long semesterId, Collection<DtoOption> options) throws Exception {
        List<CourseSemester> courseSemesterList = getAllBySemesterId(semesterId);

        if (courseSemesterList == null) {
            return null;
        }

        return wrapListDTO(courseSemesterList, options);
    }

    @Override
    public List<CourseSemester> getAllBySemesterIdIn(Collection<Long> semesterIdCollection) throws Exception {
        List<CourseSemester> courseSemesterPage =
                courseSemesterRepository.findAllBySemesterIdInAndStatusNot(semesterIdCollection, Status.DELETED);

        if (courseSemesterPage.isEmpty()) {
            return null;
        }

        return courseSemesterPage;
    }
    @Override
    public Map<Long, Map<Long, Set<Long>>> mapSemesterIdMapCenterIdListCourseIdBySemesterIdIn(
            Collection<Long> semesterIdCollection) throws Exception {
        List<CourseSemester> courseSemesterList = getAllBySemesterIdIn(semesterIdCollection);

        if (courseSemesterList == null) {
            return new HashMap<>();
        }
        Map<Long, Map<Long, Set<Long>>> semesterIdCenterIdCourseIdSetMapMap = new HashMap<>();

        Map<Long, Set<Long>> centerIdCourseIdSetMap;
        Set<Long> courseIdSet;
        Long semesterId, centerId, courseId;
        for (CourseSemester courseSemester : courseSemesterList) {
            semesterId = courseSemester.getSemesterId();
            centerId = courseSemester.getCenterId();
            courseId = courseSemester.getCourseId();

            centerIdCourseIdSetMap = semesterIdCenterIdCourseIdSetMapMap.get(semesterId);

            if (centerIdCourseIdSetMap == null) {
                centerIdCourseIdSetMap = new HashMap<>();

                centerIdCourseIdSetMap.put(
                        centerId,
                        new HashSet<>(Set.of(courseId)));

                semesterIdCenterIdCourseIdSetMapMap.put(
                        semesterId,
                        centerIdCourseIdSetMap);
            } else {
                courseIdSet = centerIdCourseIdSetMap.get(centerId);

                if (courseIdSet == null) {
                    centerIdCourseIdSetMap.put(
                            centerId,
                            new HashSet<>(Set.of(courseId)));

                    semesterIdCenterIdCourseIdSetMapMap.put(
                            semesterId,
                            centerIdCourseIdSetMap);
                } else {
                    courseIdSet.add(courseId);

                    semesterIdCenterIdCourseIdSetMapMap.put(
                            semesterId,
                            centerIdCourseIdSetMap);
                }
            }
        }

        return semesterIdCenterIdCourseIdSetMapMap;
    }
    @Override
    public List<CourseSemesterReadDTO> getAllDTOBySemesterIdIn(
            Collection<Long> semesterIdCollection, Collection<DtoOption> options) throws Exception {
        List<CourseSemester> courseSemesterList = getAllBySemesterIdIn(semesterIdCollection);

        if (courseSemesterList == null) {
            return null;
        }

        return wrapListDTO(courseSemesterList, options);
    }
    /* courseId, semesterId, centerId */
    @Override
    public CourseSemester getByCourseIdAndSemesterIdAndCenterId(
            Long courseId, Long semesterId, Long centerId) throws Exception {
        return courseSemesterRepository
                .findByCourseIdAndSemesterIdAndCenterIdAndStatusNot(courseId, semesterId, centerId, Status.DELETED)
                .orElse(null);
    }


    /* =================================================== UPDATE =================================================== */


    /* =================================================== DELETE =================================================== */


    /* =================================================== WRAPPER ================================================== */
    @Override
    public CourseSemesterReadDTO wrapDTO(
            CourseSemester courseSemester, Collection<DtoOption> options) throws Exception {
        CourseSemesterReadDTO dto = mapper.map(courseSemester, CourseSemesterReadDTO.class);

        /* Add Dependency */
        if (options != null && !options.isEmpty()) {
            Long courseId = courseSemester.getCourseId();
            Long centerId = courseSemester.getCenterId();
            Long semesterId = courseSemester.getSemesterId();

            if (options.contains(DtoOption.COURSE)) {
                CourseReadDTO courseDTO = courseService.getDTOById(
                        courseId,
                        List.of(Status.DELETED),
                        false,
                        options);
                dto.setCourse(courseDTO);
            }
            if (options.contains(DtoOption.COURSE_NAME)) {
                Course course = courseService.getById(courseId,
                        List.of(Status.DELETED),
                        false);
                dto.setCourseName(course.getCourseName());
            }
            if (options.contains(DtoOption.COURSE_ALIAS)) {
                Course course = courseService.getById(courseId,
                        List.of(Status.DELETED),
                        false);
                dto.setCourseAlias(course.getCourseAlias());
            }

            if (options.contains(DtoOption.CENTER)) {
                CenterReadDTO center = centerService.getDTOById(centerId, options);
                dto.setCenter(center);
            }
            if (options.contains(DtoOption.CENTER_NAME)) {
                Center center = centerService.getById(centerId);
                dto.setCenterName(center.getCenterName());
            }

            if (options.contains(DtoOption.SEMESTER)) {
                SemesterReadDTO semester = semesterService.getDTOById(semesterId, options);
                dto.setSemester(semester);
            }
            if (options.contains(DtoOption.SEMESTER_NAME)) {
                Semester semester = semesterService.getById(semesterId);
                dto.setSemesterName(semester.getSemesterName());
            }
            if (options.contains(DtoOption.SEMESTER_ALIAS)) {
                Semester semester = semesterService.getById(semesterId);
                dto.setSemesterAlias(semester.getSemesterAlias());
            }

//            if (options.contains(DtoOption.CLAZZ_LIST)) {
//                List<ClazzReadDTO> clazzList = clazzService.getAllDTOByCourseSemesterId(courseSemester.getId(), options);
//                dto.setClazzList(clazzList);
//            }
        }

        return dto;
    }
    @Override
    public List<CourseSemesterReadDTO> wrapListDTO(
            Collection<CourseSemester> courseSemesterCollection, Collection<DtoOption> options) throws Exception {
        List<CourseSemesterReadDTO> dtoList = new ArrayList<>();

        CourseSemesterReadDTO dto;

        Map<Long, CourseReadDTO> courseIdCourseDTOMap = new HashMap<>();
        Map<Long, String> courseIdCourseNameMap = new HashMap<>();
        Map<Long, String> courseIdCourseAliasMap = new HashMap<>();

        Map<Long, CenterReadDTO> centerIdCenterDTOMap = new HashMap<>();
        Map<Long, String> centerIdCenterNameMap = new HashMap<>();

        Map<Long, SemesterReadDTO> semesterIdSemesterDTOMap = new HashMap<>();
        Map<Long, String> semesterIdSemesterNameMap = new HashMap<>();
        Map<Long, String> semesterIdSemesterAliasMap = new HashMap<>();

        Map<Long, List<ClazzReadDTO>> courseSemesterIdClazzDTOListMap = new HashMap<>();

        if (options != null && !options.isEmpty()) {
            Set<Long> courseSemesterIdSet = new HashSet<>();
            Set<Long> courseIdSet = new HashSet<>();
            Set<Long> centerIdSet = new HashSet<>();
            Set<Long> SemesterIdSet = new HashSet<>();

            for (CourseSemester courseSemester : courseSemesterCollection) {
                courseSemesterIdSet.add(courseSemester.getId());
                courseIdSet.add(courseSemester.getCourseId());
                centerIdSet.add(courseSemester.getCenterId());
                SemesterIdSet.add(courseSemester.getSemesterId());
            }

            if (options.contains(DtoOption.COURSE)) {
                courseIdCourseDTOMap = courseService.mapIdDTOByIdIn(courseIdSet,
                        List.of(Status.DELETED),
                        false, options);
            }
            if (options.contains(DtoOption.COURSE_NAME)) {
                courseIdCourseNameMap = courseService.mapIdCourseNameByIdIn(courseIdSet,
                        List.of(Status.DELETED),
                        false);
            }
            if (options.contains(DtoOption.COURSE_ALIAS)) {
                courseIdCourseAliasMap = courseService.mapIdCourseAliasByIdIn(courseIdSet,
                        List.of(Status.DELETED),
                        false);
            }

            if (options.contains(DtoOption.CENTER)) {
                centerIdCenterDTOMap = centerService.mapIdDTOByIdIn(centerIdSet, options);
            }
            if (options.contains(DtoOption.CENTER_NAME)) {
                centerIdCenterNameMap = centerService.mapIdCenterNameByIdIn(centerIdSet);
            }

            if (options.contains(DtoOption.SEMESTER)) {
                semesterIdSemesterDTOMap = semesterService.mapIdDTOByIdIn(SemesterIdSet, options);
            }
            if (options.contains(DtoOption.SEMESTER_NAME)) {
                semesterIdSemesterNameMap = semesterService.mapIdSemesterNameByIdIn(SemesterIdSet);
            }
            if (options.contains(DtoOption.SEMESTER_ALIAS)) {
                semesterIdSemesterAliasMap = semesterService.mapIdSemesterAliasByIdIn(SemesterIdSet);
            }

//            if (options.contains(DtoOption.CLAZZ_LIST)) {
//                courseSemesterIdClazzDTOListMap =
//                        clazzService.mapCourseSemesterIdListDTOByCourseSemesterIdIn(courseSemesterIdSet, options);
//            }
        }

        for (CourseSemester courseSemester : courseSemesterCollection) {
            dto = mapper.map(courseSemester, CourseSemesterReadDTO.class);

            /* Add Dependency */
            dto.setCourse(courseIdCourseDTOMap.get(courseSemester.getCourseId()));
            dto.setCourseName(courseIdCourseNameMap.get(courseSemester.getCourseId()));
            dto.setCourseAlias(courseIdCourseAliasMap.get(courseSemester.getCourseId()));

            dto.setCenter(centerIdCenterDTOMap.get(courseSemester.getCenterId()));
            dto.setCenterName(centerIdCenterNameMap.get(courseSemester.getCenterId()));

            dto.setSemester(semesterIdSemesterDTOMap.get(courseSemester.getSemesterId()));
            dto.setSemesterName(semesterIdSemesterNameMap.get(courseSemester.getSemesterId()));
            dto.setSemesterAlias(semesterIdSemesterAliasMap.get(courseSemester.getSemesterId()));

            dto.setClazzList(courseSemesterIdClazzDTOListMap.get(courseSemester.getId()));

            dtoList.add(dto);
        }

        return dtoList;
    }
    @Override
    public Page<CourseSemesterReadDTO> wrapPageDTO(
            Page<CourseSemester> courseSemesterPage, Collection<DtoOption> options) throws Exception {
        return new PageImpl<>(
                wrapListDTO(courseSemesterPage.getContent(), options),
                courseSemesterPage.getPageable(),
                courseSemesterPage.getTotalPages());
    }
}