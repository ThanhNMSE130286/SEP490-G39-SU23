package com.teachsync.services.address;

import com.teachsync.dtos.address.AddressCreateDTO;
import com.teachsync.dtos.address.AddressReadDTO;
import com.teachsync.dtos.address.AddressUpdateDTO;
import org.springframework.data.domain.Page;
import com.teachsync.utils.enums.DtoOption;
import com.teachsync.entities.Address;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AddressService {
    /* =================================================== CREATE =================================================== */
    Address createAddress(Address address) throws Exception;
    AddressReadDTO createAddressByDTO(AddressCreateDTO addressCreateDTO) throws Exception;


    /* =================================================== READ ===================================================== */
    Address getById(Long id) throws Exception;
    AddressReadDTO getDTOById(Long id) throws Exception;

    List<Address> getAllByIdIn(Collection<Long> idCollection) throws Exception;
    Map<Long, Address> mapIdAddressByIdIn(Collection<Long> idCollection) throws Exception;
    List<AddressReadDTO> getAllDTOByIdIn(Collection<Long> idCollection) throws Exception;


    /* =================================================== UPDATE =================================================== */
    Address updateAddress(Address address) throws Exception;
    AddressReadDTO updateAddressByDTO(AddressUpdateDTO addressUpdateDTO) throws Exception;


    /* =================================================== DELETE =================================================== */


    /* =================================================== WRAPPER ================================================== */
    AddressReadDTO wrapDTO(Address address, Collection<DtoOption> options) throws Exception;
    List<AddressReadDTO> wrapListDTO(Collection<Address> addressCollection, Collection<DtoOption> options) throws Exception;
    Page<AddressReadDTO> wrapPageDTO(Page<Address> addressPage, Collection<DtoOption> options) throws Exception;
}
