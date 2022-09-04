package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.PCTCBasicUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PCTCBasicUser and its DTO PCTCBasicUserDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PCTCBasicUserMapper extends EntityMapper<PCTCBasicUserDTO, PCTCBasicUser> {



    default PCTCBasicUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        PCTCBasicUser pCTCBasicUser = new PCTCBasicUser();
        pCTCBasicUser.setId(id);
        return pCTCBasicUser;
    }
}
