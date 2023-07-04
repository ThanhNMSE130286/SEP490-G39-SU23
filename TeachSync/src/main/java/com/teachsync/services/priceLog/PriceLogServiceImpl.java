package com.teachsync.services.priceLog;

import com.teachsync.dtos.BaseReadDTO;
import com.teachsync.dtos.priceLog.PriceLogReadDTO;
import com.teachsync.entities.PriceLog;
import com.teachsync.repositories.PriceLogRepository;
import com.teachsync.utils.MiscUtil;
import com.teachsync.utils.enums.Status;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PriceLogServiceImpl implements PriceLogService {
    @Autowired
    private PriceLogRepository priceLogRepository;
    
    @Autowired
    private MiscUtil miscUtil;
    @Autowired
    private ModelMapper mapper;
    
    
    
    /* =================================================== CREATE =================================================== */



    /* =================================================== READ ===================================================== */
    @Override
    public Page<PriceLog> getPageAll(Pageable paging) throws Exception {
        if (paging == null) {
            paging = miscUtil.defaultPaging(); }

        Page<PriceLog> priceLogPage =
                priceLogRepository.findAllByStatusNot(Status.DELETED, paging);

        if (priceLogPage.isEmpty()) {
            return null; }

        return priceLogPage;
    }
    @Override
    public Page<PriceLogReadDTO> getPageDTOAll(Pageable paging) throws Exception {
        Page<PriceLog> priceLogPage = getPageAll(paging);

        if (priceLogPage == null) {
            return null; }

        return wrapPageDTO(priceLogPage);
    }

    @Override
    public Page<PriceLog> getPageAllLatestPromotion(Pageable paging) throws Exception {
        if (paging == null) {
            paging = miscUtil.defaultPaging(); }

        Page<PriceLog> priceLogPage =
                priceLogRepository.findAllByIsCurrentTrueAndIsPromotionTrueAndStatusNot(Status.DELETED, paging);

        if (priceLogPage.isEmpty()) {
            return null; }

        return priceLogPage;
    }
    @Override
    public Page<PriceLogReadDTO> getPageAllLatestPromotionDTO(Pageable paging) throws Exception {
        Page<PriceLog> priceLogPage = getPageAllLatestPromotion(paging);

        if (priceLogPage == null) {
            return null; }

        return wrapPageDTO(priceLogPage);
    }

    /* id */
    @Override
    public PriceLog getById(Long id) throws Exception {
        Optional<PriceLog> priceLog =
                priceLogRepository.findByIdAndStatusNot(id, Status.DELETED);

        return priceLog.orElse(null);
    }
    @Override
    public PriceLogReadDTO getDTOById(Long id) throws Exception {
        PriceLog priceLog = getById(id);

        if (priceLog == null) {
            return null; }

        return wrapDTO(priceLog);
    }

    /* courseId */
    @Override
    public PriceLog getLatestByCourseId(Long courseId) throws Exception {
        Optional<PriceLog> priceLog =
                priceLogRepository.findByCourseIdAndIsCurrentTrueAndStatusNot(courseId, Status.DELETED);

        return priceLog.orElse(null);
    }
    @Override
    public PriceLogReadDTO getLatestDTOByCourseId(Long CourseId) throws Exception {
        PriceLog priceLog = getLatestByCourseId(CourseId);

        if (priceLog == null) {
            return null; }

        return wrapDTO(priceLog);
    }

    @Override
    public List<PriceLog> getAllLatestByCourseIdIn(Collection<Long> courseIdCollection) throws Exception {
        List<PriceLog> priceLogList =
                priceLogRepository.findAllByCourseIdInAndIsCurrentTrueAndStatusNot(courseIdCollection ,Status.DELETED);

        if (priceLogList.isEmpty()) {
            return null; }

        return priceLogList;
    }
    @Override
    public List<PriceLogReadDTO> getAllLatestDTOByCourseIdIn(Collection<Long> courseIdCollection) throws Exception {
        List<PriceLog> priceLogList = getAllLatestByCourseIdIn(courseIdCollection);

        if (priceLogList == null) {
            return null; }

        return wrapListDTO(priceLogList);
    }
    @Override
    public Map<Long, PriceLogReadDTO> mapCourseIdLatestDTOByCourseIdIn(Collection<Long> courseIdCollection) throws Exception {
        List<PriceLogReadDTO> priceLodDTOList = getAllLatestDTOByCourseIdIn(courseIdCollection);

        if (priceLodDTOList == null) {
            return new HashMap<>(); }

        return priceLodDTOList.stream()
                .collect(Collectors.toMap(PriceLogReadDTO::getCourseId, Function.identity()));
    }

    @Override
    public List<PriceLog> getAllByCourseId(Long courseId) throws Exception {
        List<PriceLog> priceLogList =
                priceLogRepository.findAllByCourseIdAndStatusNot(courseId ,Status.DELETED);

        if (priceLogList.isEmpty()) {
            return null; }

        return priceLogList;
    }
    @Override
    public List<PriceLogReadDTO> getAllDTOByCourseId(Long courseId) throws Exception {
        List<PriceLog> priceLogList = getAllByCourseId(courseId);

        if (priceLogList == null) {
            return null; }

        return wrapListDTO(priceLogList);
    }



    /* =================================================== UPDATE =================================================== */



    /* =================================================== DELETE =================================================== */



    /* =================================================== WRAPPER ================================================== */
    @Override
    public PriceLogReadDTO wrapDTO(PriceLog priceLog) throws Exception {
        PriceLogReadDTO dto = mapper.map(priceLog, PriceLogReadDTO.class);

        Double finalPrice = priceLog.getPrice();

        if (priceLog.getIsPromotion()) {
            finalPrice = switch (priceLog.getPromotionType()) {
                case AMOUNT -> priceLog.getPrice() - priceLog.getPromotionAmount();
                case PERCENT -> priceLog.getPrice() * ((100.0 - priceLog.getPromotionAmount()) / 100.0);
            };
        }

        dto.setFinalPrice(finalPrice);
        
        return dto;
    }

    @Override
    public List<PriceLogReadDTO> wrapListDTO(Collection<PriceLog> priceLogCollection) throws Exception {
        List<PriceLogReadDTO> dtoList = new ArrayList<>();

        PriceLogReadDTO dto;

        Double finalPrice;
        for (PriceLog priceLog : priceLogCollection) {
            dto = mapper.map(priceLog, PriceLogReadDTO.class);

            finalPrice = priceLog.getPrice();

            if (priceLog.getIsPromotion()) {
                finalPrice = switch (priceLog.getPromotionType()) {
                    case AMOUNT -> priceLog.getPrice() - priceLog.getPromotionAmount();
                    case PERCENT -> priceLog.getPrice() * ((100.0 - priceLog.getPromotionAmount()) / 100.0);
                };
            }

            dto.setFinalPrice(finalPrice);

            dtoList.add(dto);
        }

        return dtoList;
    }

    @Override
    public Page<PriceLogReadDTO> wrapPageDTO(Page<PriceLog> priceLogPage) throws Exception {
        return new PageImpl<>(
                wrapListDTO(priceLogPage.getContent()),
                priceLogPage.getPageable(),
                priceLogPage.getTotalPages());
    }
}
