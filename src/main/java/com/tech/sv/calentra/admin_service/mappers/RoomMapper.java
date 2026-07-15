package com.tech.sv.calentra.admin_service.mappers;

import org.mapstruct.Mapper;

import com.tech.sv.calentra.admin_service.dtos.response.RoomResponse;
import com.tech.sv.calentra.admin_service.entities.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {
	RoomResponse toRoomResponse(Room room);
}
