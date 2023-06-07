package com.teachsync.services.priceLog;

import com.teachsync.dtos.priceLog.PriceLogReadDTO;
import com.teachsync.entities.PriceLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;

public interface PriceLogService {
    /* =================================================== CREATE =================================================== */



    /* =================================================== READ ===================================================== */
    Page<PriceLog> getPageAll(Pageable paging) throws Exception;
    Page<PriceLogReadDTO> getPageDTOAll(Pageable paging) throws Exception;

    PriceLog getById(Long id) throws Exception;
    PriceLogReadDTO getDTOById(Long id) throws Exception;

    /* courseId */
    PriceLog getLatestByCourseId(Long courseId) throws Exception;
    PriceLogReadDTO getLatestDTOByCourseId(Long courseId) throws Exception;
    
    List<PriceLog> getAllByCourseId(Long courseId) throws Exception;
    List<PriceLogReadDTO> getAllDTOById(Long courseId) throws Exception;



    /* =================================================== UPDATE =================================================== */



    /* =================================================== DELETE =================================================== */



    /* =================================================== WRAPPER ================================================== */
    PriceLogReadDTO wrapDTO(PriceLog priceLog) throws Exception;

    List<PriceLogReadDTO> wrapListDTO(Collection<PriceLog> priceLogCollection) throws Exception;

    Page<PriceLogReadDTO> wrapPageDTO(Page<PriceLog> priceLogPage) throws Exception;
}
