package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.HierarchyDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Hierarchy and its DTO HierarchyDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface HierarchyMapper extends EntityMapper<HierarchyDTO, Hierarchy> {

    @Mapping(source = "parentUser.id", target = "parentUserId")
    @Mapping(source = "childUser.id", target = "childUserId")
    HierarchyDTO toDto(Hierarchy hierarchy);

    @Mapping(source = "parentUserId", target = "parentUser")
    @Mapping(source = "childUserId", target = "childUser")
    Hierarchy toEntity(HierarchyDTO hierarchyDTO);

    default Hierarchy fromId(Long id) {
        if (id == null) {
            return null;
        }
        Hierarchy hierarchy = new Hierarchy();
        hierarchy.setId(id);
        return hierarchy;
    }
}
