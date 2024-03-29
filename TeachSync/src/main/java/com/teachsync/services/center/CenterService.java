package com.teachsync.services.center;

import com.teachsync.dtos.center.CenterCreateDTO;
import com.teachsync.dtos.center.CenterReadDTO;
import com.teachsync.dtos.center.CenterUpdateDTO;
import com.teachsync.entities.Center;
import com.teachsync.utils.enums.DtoOption;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface CenterService {
    /* =================================================== CREATE =================================================== */
    Center createCenter(Center center) throws Exception;
    CenterReadDTO createCenterByDTO(CenterCreateDTO createDTO) throws Exception;


    /* =================================================== READ ===================================================== */
    List<Center> getAll() throws Exception;
    List<CenterReadDTO> getAllDTO(Collection<DtoOption> options) throws Exception;
    Map<Long, CenterReadDTO> mapIdDTO(Collection<DtoOption> options) throws Exception;

    /* id */
    Boolean existsById(Long id) throws Exception;
    Center getById(Long id) throws Exception;
    CenterReadDTO getDTOById(Long id, Collection<DtoOption> options) throws Exception;

    Boolean existsAllByIdIn(Collection<Long> idCollection) throws Exception;
    List<Center> getAllByIdIn(Collection<Long> idCollection) throws Exception;
    Map<Long, String> mapIdCenterNameByIdIn(
            Collection<Long> idCollection) throws Exception;
    List<CenterReadDTO> getAllDTOByIdIn(
            Collection<Long> idCollection, Collection<DtoOption> options) throws Exception;
    Map<Long, CenterReadDTO> mapIdDTOByIdIn(
            Collection<Long> idCollection, Collection<DtoOption> options) throws Exception;

    
    /* =================================================== UPDATE =================================================== */
    Center updateCenter(Center center) throws Exception;
    CenterReadDTO updateCenterByDTO(CenterUpdateDTO updateDTO) throws Exception;


    /* =================================================== DELETE =================================================== */
    
    
    /* =================================================== WRAPPER ================================================== */
    CenterReadDTO wrapDTO(Center center, Collection<DtoOption> options) throws Exception;
    List<CenterReadDTO> wrapListDTO(Collection<Center> centerCollection, Collection<DtoOption> options) throws Exception;
    Page<CenterReadDTO> wrapPageDTO(Page<Center> centerPage, Collection<DtoOption> options) throws Exception;
}
