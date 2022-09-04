package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.ApplyJobDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ApplyJob and its DTO ApplyJobDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ApplyJobMapper extends EntityMapper<ApplyJobDTO, ApplyJob> {



    default ApplyJob fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplyJob applyJob = new ApplyJob();
        applyJob.setId(id);
        return applyJob;
    }
}
