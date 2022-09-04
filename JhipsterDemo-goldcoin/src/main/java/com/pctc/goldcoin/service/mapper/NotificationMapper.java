package com.pctc.goldcoin.service.mapper;

import com.pctc.goldcoin.domain.*;
import com.pctc.goldcoin.service.dto.NotificationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Notification and its DTO NotificationDTO.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class, CompanyProfileMapper.class, LocationMapper.class})
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {

    @Mapping(source = "notiBy.id", target = "notiById")
    @Mapping(source = "notiTo.id", target = "notiToId")
    @Mapping(source = "notiComp.id", target = "notiCompId")
    @Mapping(source = "notiLoc.id", target = "notiLocId")
    NotificationDTO toDto(Notification notification);

    @Mapping(source = "notiById", target = "notiBy")
    @Mapping(source = "notiToId", target = "notiTo")
    @Mapping(source = "notiCompId", target = "notiComp")
    @Mapping(source = "notiLocId", target = "notiLoc")
    Notification toEntity(NotificationDTO notificationDTO);

    default Notification fromId(Long id) {
        if (id == null) {
            return null;
        }
        Notification notification = new Notification();
        notification.setId(id);
        return notification;
    }
}
