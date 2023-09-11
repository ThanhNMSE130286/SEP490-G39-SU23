package com.teachsync.services.homework;

import com.teachsync.dtos.homework.HomeworkReadDTO;
import com.teachsync.dtos.memberHomeworkRecord.MemberHomeworkRecordReadDTO;
import com.teachsync.dtos.user.UserReadDTO;
import com.teachsync.entities.Clazz;
import com.teachsync.entities.ClazzMember;
import com.teachsync.entities.Homework;
import com.teachsync.entities.MemberHomeworkRecord;
import com.teachsync.repositories.ClazzMemberRepository;
import com.teachsync.repositories.ClazzRepository;
import com.teachsync.repositories.HomeworkRepository;
import com.teachsync.repositories.MemberHomeworkRecordRepository;
import com.teachsync.utils.Constants;
import com.teachsync.utils.MiscUtil;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class HomeworkServiceImpl implements HomeworkService {

    @Autowired
    private MiscUtil miscUtil;

    @Autowired
    private HomeworkRepository homeworkRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    ClazzRepository clazzRepository;

    @Autowired
    ClazzMemberRepository clazzMemberRepository;

    @Autowired
    private MemberHomeworkRecordRepository memberHomeworkRecordRepository;

    @Override
    public Page<HomeworkReadDTO> getPageAll(Pageable paging, UserReadDTO userDTO) throws Exception {
        if (paging == null) {
            paging = miscUtil.defaultPaging();
        }
        Page<Homework> homeworkPage = homeworkRepository.findAllByStatusNot(Status.DELETED, paging);

        List<HomeworkReadDTO> homeworkDtoList = new ArrayList<>();
        for (Homework homework : homeworkPage) {
            HomeworkReadDTO homeworkReadDTO = mapper.map(homework, HomeworkReadDTO.class);
            //get class
            Clazz clazz = clazzRepository.findById(homeworkReadDTO.getClazzId()).orElseThrow(() -> new Exception("không tìm lớp học"));
            homeworkReadDTO.setClazzName(clazz.getClazzName());

            //check student to show
            if (!userDTO.getRoleId().equals(Constants.ROLE_ADMIN) && !userDTO.getRoleId().equals(Constants.ROLE_TEACHER)) {
                //kiểm tra xem học sinh có thuộc bài tập này không
                ClazzMember clazzMember = clazzMemberRepository.findByClazzIdAndUserIdAndStatusNot(clazz.getId(), userDTO.getId(), Status.DELETED).orElse(null);
                if (!ObjectUtils.isEmpty(clazzMember)) {
                    if (!ObjectUtils.isEmpty(homeworkReadDTO.getOpenAt()) && homeworkReadDTO.getOpenAt().isBefore(LocalDateTime.now())) {
                        homeworkDtoList.add(homeworkReadDTO);
                    }
                }
            } else {
                homeworkDtoList.add(homeworkReadDTO);
            }
        }

        Page<HomeworkReadDTO> homeworkReadDTOS = new PageImpl<>(homeworkDtoList, paging, homeworkPage.getTotalElements());

        if (homeworkReadDTOS.isEmpty()) {
            return new PageImpl<>(Collections.emptyList());
        }

        return homeworkReadDTOS;
    }

    @Override
    public HomeworkReadDTO findById(Long id) throws Exception {
        Homework homework = homeworkRepository.findById(id).orElseThrow(() -> new Exception("không tìm bài tập về nhà"));
        HomeworkReadDTO homeworkReadDTO = mapper.map(homework, HomeworkReadDTO.class);
        Clazz clazz = clazzRepository.findById(homeworkReadDTO.getClazzId()).orElseThrow(() -> new Exception("không tìm lớp học"));
        homeworkReadDTO.setClazzName(clazz.getClazzName());
        List<MemberHomeworkRecord> memberHomeworkRecordList = memberHomeworkRecordRepository.findAllByStatusNot(Status.DELETED);
        List<MemberHomeworkRecordReadDTO> homeworkRecordReadDTOList = new ArrayList<>();
        for (MemberHomeworkRecord memberHomeworkRecord : memberHomeworkRecordList) {
            MemberHomeworkRecordReadDTO memberHomeworkRecordReadDTO = mapper.map(memberHomeworkRecord, MemberHomeworkRecordReadDTO.class);
            homeworkRecordReadDTOList.add(memberHomeworkRecordReadDTO);
        }
        homeworkReadDTO.setMemberHomeworkRecordList(homeworkRecordReadDTOList);
        return homeworkReadDTO;
    }

    @Override
    public HomeworkReadDTO findById(Long id, UserReadDTO userDTO) throws Exception {
        Homework homework = homeworkRepository.findById(id).orElseThrow(() -> new Exception("không tìm bài tập về nhà"));
        HomeworkReadDTO homeworkReadDTO = mapper.map(homework, HomeworkReadDTO.class);
        Clazz clazz = clazzRepository.findById(homeworkReadDTO.getClazzId()).orElseThrow(() -> new Exception("không tìm lớp học"));
        homeworkReadDTO.setClazzName(clazz.getClazzName());
        List<MemberHomeworkRecord> memberHomeworkRecordList = memberHomeworkRecordRepository.findAllByStatusNotAndCreatedBy(Status.DELETED, userDTO.getId());
        List<MemberHomeworkRecordReadDTO> homeworkRecordReadDTOList = new ArrayList<>();
        for (MemberHomeworkRecord memberHomeworkRecord : memberHomeworkRecordList) {
            MemberHomeworkRecordReadDTO memberHomeworkRecordReadDTO = mapper.map(memberHomeworkRecord, MemberHomeworkRecordReadDTO.class);
            homeworkRecordReadDTOList.add(memberHomeworkRecordReadDTO);
        }
        homeworkReadDTO.setMemberHomeworkRecordList(homeworkRecordReadDTOList);
        return homeworkReadDTO;
    }

    @Override
    public List<HomeworkReadDTO> getAllByClazzId(Long clazzId) {
        List<Homework> listHomeWork = homeworkRepository.findAllByClazzIdAndStatusNot(clazzId,Status.DELETED);
        List<HomeworkReadDTO> listHomeworkReadDTO = new ArrayList<>();
        for(Homework item : listHomeWork){
            HomeworkReadDTO homeworkReadDTO = mapper.map(item, HomeworkReadDTO.class);

            //find homewordk record
            List<MemberHomeworkRecord> memberHomeworkRecordList = memberHomeworkRecordRepository.findAllByStatusNotAndHomeworkId(Status.DELETED, item.getId());
            List<MemberHomeworkRecordReadDTO> homeworkRecordReadDTOList = new ArrayList<>();
            for (MemberHomeworkRecord memberHomeworkRecord : memberHomeworkRecordList) {
                MemberHomeworkRecordReadDTO memberHomeworkRecordReadDTO = mapper.map(memberHomeworkRecord, MemberHomeworkRecordReadDTO.class);
                homeworkRecordReadDTOList.add(memberHomeworkRecordReadDTO);
            }

            homeworkReadDTO.setMemberHomeworkRecordList(homeworkRecordReadDTOList);
            listHomeworkReadDTO.add(homeworkReadDTO);
        }
        return listHomeworkReadDTO;
    }

    @Override
    @Transactional
    public void addHomework(HomeworkReadDTO homeworkReadDTO, UserReadDTO userDTO) throws Exception {
        Homework homework = new Homework();

        homework.setHomeworkName(homeworkReadDTO.getHomeworkName());
        homework.setClazzId(homeworkReadDTO.getClazzId());
        homework.setHomeworkDesc(homeworkReadDTO.getHomeworkDesc());
        homework.setHomeworkDoc(homeworkReadDTO.getHomeworkDoc());
        homework.setHomeworkContent(homeworkReadDTO.getHomeworkContent());//TODO : upload file
        //homework.setHomeworkDocLink(homeworkReadDTO.getHomeworkDocLink());
        homework.setDeadline(homeworkReadDTO.getDeadline());
        homework.setOpenAt(homeworkReadDTO.getOpenAt());
        homework.setCreatedBy(userDTO.getId());
        homework.setUpdatedBy(userDTO.getId());
        homework.setStatus(Status.CREATED);

        Homework homeworkDb = homeworkRepository.save(homework);
        if (ObjectUtils.isEmpty(homeworkDb)) {
            throw new Exception("Lỗi khi tạo bài tập về nhà");
        }
    }

    @Override
    @Transactional
    public void editHomework(HomeworkReadDTO homeworkReadDTO, UserReadDTO userDTO) throws Exception {
        Homework homework = homeworkRepository.findById(homeworkReadDTO.getId()).orElseThrow(() -> new Exception("không tìm bài tập về nhà"));

        homework.setHomeworkName(homeworkReadDTO.getHomeworkName());
        homework.setClazzId(homeworkReadDTO.getClazzId());
        homework.setHomeworkDesc(homeworkReadDTO.getHomeworkDesc());
        homework.setHomeworkDoc(homeworkReadDTO.getHomeworkDoc());
        homework.setHomeworkContent(homeworkReadDTO.getHomeworkContent());//TODO : upload file
        //homework.setHomeworkDocLink(homeworkReadDTO.getHomeworkDocLink());
        homework.setDeadline(homeworkReadDTO.getDeadline());
        homework.setOpenAt(homeworkReadDTO.getOpenAt());
        homework.setUpdatedBy(userDTO.getId());
        homework.setStatus(Status.UPDATED);

        Homework homeworkDb = homeworkRepository.save(homework);
        if (ObjectUtils.isEmpty(homeworkDb)) {
            throw new Exception("Lỗi khi sửa bài tập về nhà");
        }
    }

    @Override
    public void deleteHomework(Long Id, UserReadDTO userDTO) throws Exception {
        Homework homework = homeworkRepository.findById(Id).orElseThrow(() -> new Exception("không tìm bài tập về nhà"));
        homework.setUpdatedBy(userDTO.getId());
        homework.setStatus(Status.DELETED);
        Homework homeworkDb = homeworkRepository.save(homework);
        if (ObjectUtils.isEmpty(homeworkDb)) {
            throw new Exception("Lỗi khi xóa bài tập về nhà");
        }
    }
}
