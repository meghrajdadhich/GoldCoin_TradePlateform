package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.LocationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Location and its DTO LocationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {

    @Mapping(source = "user.id", target = "userId")
    LocationDTO toDto(Location location);

    @Mapping(source = "userId", target = "user")
    Location toEntity(LocationDTO locationDTO);

    default Location fromId(Long id) {
        if (id == null) {
            return null;
        }
        Location location = new Location();
        location.setId(id);
        return location;
    }
}
