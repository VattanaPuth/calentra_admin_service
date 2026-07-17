package valio.admin_service.services;

import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import valio.admin_service.dtos.response.RoomResponse;
import valio.admin_service.entities.Room;

public interface RoomService {
	List<RoomResponse> getAllRoomsVisible();
	Room getRoomByIdVisible(UUID roomId);
	Room addRoom(Room room, MultipartFile image);
	Room updateRoom(UUID roomId, Room room, MultipartFile image);
	void updateVisibility(UUID id, Boolean visibility);
	void deleteRoom(UUID id);
	
}
