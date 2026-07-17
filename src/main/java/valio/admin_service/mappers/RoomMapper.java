package valio.admin_service.mappers;

import org.mapstruct.Mapper;

import valio.admin_service.dtos.response.RoomResponse;
import valio.admin_service.entities.Room;

@Mapper(componentModel = "spring")
public interface RoomMapper {
	RoomResponse toRoomResponse(Room room);
}
